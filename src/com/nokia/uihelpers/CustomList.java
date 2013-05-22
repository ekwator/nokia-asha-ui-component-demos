/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.uihelpers;

import com.nokia.example.ashauicomponentdemos.utils.DelayedTask;
import com.nokia.example.ashauicomponentdemos.utils.ImageLoader;
import com.nokia.mid.ui.CategoryBar;
import com.nokia.mid.ui.DirectGraphics;
import com.nokia.mid.ui.DirectUtils;
import com.nokia.mid.ui.VirtualKeyboard;
import com.nokia.uihelpers.gesture.SafeGestureEvent;
import com.nokia.uihelpers.gesture.SafeGestureInteractiveZone;
import com.nokia.uihelpers.gesture.SafeGestureListener;
import com.nokia.uihelpers.gesture.SafeGestureRegistrationManager;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;

/**
 * List view custom UI control.
 * Provides a scrollbar for visualization.
 */
public class CustomList
    extends Canvas
    implements SafeGestureListener {

    private final static int GESTURES = SafeGestureInteractiveZone.GESTURE_DRAG
        | SafeGestureInteractiveZone.GESTURE_TAP
        | SafeGestureInteractiveZone.GESTURE_FLICK
        | SafeGestureInteractiveZone.GESTURE_LONG_PRESS
        | SafeGestureInteractiveZone.GESTURE_DROP;
    private final static int MAX_SCROLLING_VELOCITY = 1000;
    private final static int SCROLLING_FRICTION = 300;
    private final static int RUBBERBAND_FORCE = 10000;
    private final static Command MARK = new Command("Mark", Command.OK, 0);
    private final static Command UNMARK = new Command("Unmark", Command.OK, 0);
    private final static Command SELECT = new Command("Select", Command.OK, 0);
    private Animator animator = null;
    private final SafeGestureInteractiveZone zone;
    private final int listType;
    private Image selectedImage;
    private Image unselectedImage;
    private boolean wrapText = false;
    protected int focusedElementIndex = -1;
    protected int width;  // width for the list
    protected int height;  // height for the list
    protected Vector elements = new Vector();
    protected int translateY = 0;  // y-coordinate of the top of visible area
    private boolean scrollBarVisible = false;
    private DelayedTask hideScrollBarTask = null;
    protected volatile boolean refreshContentHeight = false;
    private int contentHeight = 0;
    protected final int bottomPadding;
    protected Theme theme = new Theme();
    private Command selectCommand;
    private Command listCommand;
    private CommandListener commandListener = null;
    private final CommandListener internalListener = new CommandListener() {

        public void commandAction(Command c, Displayable d) {
            if (c == listCommand) {
                handleSelectEvent();
            }
            else if (commandListener != null) {
                commandListener.commandAction(c, d);
            }
        }
    };
    private boolean startScrollingAnimationAfterContentHeightRefreshed = false;

    /**
     * @see List#List(java.lang.String, int) 
     */
    public CustomList(String title, int listType)
        throws IllegalArgumentException {
        super();
        if (listType != List.IMPLICIT && listType != List.EXCLUSIVE
            && listType != List.MULTIPLE) {
            throw new IllegalArgumentException();
        }
        setTitle(title);
        this.listType = listType;

        zone = new SafeGestureInteractiveZone(GESTURES);
        try {
            Class.forName("com.nokia.mid.ui.VirtualKeyboard");
            VirtualKeyboard.hideOpenKeypadCommand(true);
        }
        catch (ClassNotFoundException e) {
        }
        int bottomPadding = 0;
        try {
            Class.forName("com.nokia.mid.ui.CategoryBar");
            bottomPadding = CategoryBar.getBestImageHeight(
                CategoryBar.IMAGE_TYPE_BACKGROUND) - 4;
        }
        catch (ClassNotFoundException e) {
        }
        this.bottomPadding = bottomPadding;

        // Highlight the first row and add the default select command 
        // if a nontouch device
        if (!hasPointerEvents()) {
            listCommand = listType == List.MULTIPLE ? MARK : SELECT;
            addCommand(listCommand);
            setFocusedElementIndex(0);
        }
        super.setCommandListener(internalListener);
    }

    /**
     * @see List#List(java.lang.String, int, java.lang.String[], javax.microedition.lcdui.Image[]) 
     */
    public CustomList(String title, int listType, String[] stringElements,
        Image[] imageElements)
        throws NullPointerException, IllegalArgumentException {
        this(title, listType);
        if (stringElements == null) {
            throw new NullPointerException();
        }
        if (imageElements != null
            && imageElements.length != stringElements.length) {
            throw new IllegalArgumentException();
        }
        if (stringElements != null) {
            for (int i = 0; i < stringElements.length; i++) {
                String stringPart = stringElements[i];
                Image imagePart = imageElements != null
                    ? imageElements[i] : null;
                append(stringPart, imagePart);
            }
        }
    }

    /**
     * Set a theme for the list
     * @param theme 
     */
    public final void setTheme(Theme theme) {
        this.theme = theme;
        for (int i = 0; i < elements.size(); i++) {
            ((IElement) elements.elementAt(i)).resetCaches();
        }
        if (!theme.hideScrollBar) {
            scrollBarVisible = true;
        }
    }

    /**
     * Get the theme used in the list
     * @return theme used in the list
     */
    public final Theme getTheme() {
        return theme;
    }

    /**
     * @see List#append(java.lang.String, javax.microedition.lcdui.Image) 
     */
    public final int append(String stringPart, Image imagePart)
        throws NullPointerException {
        elements.addElement(new Element(stringPart, imagePart));
        refreshContentHeight = true;
        repaint();
        return elements.size() - 1;
    }

    /**
     * @see List#delete(int) 
     */
    public void delete(int elementNum) {
        try {
            elements.removeElementAt(elementNum);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(e.getMessage());
        }
        refreshContentHeight = true;
        startScrollingAnimationAfterContentHeightRefreshed = true;

        if (focusedElementIndex >= elements.size()) {
            setFocusedElementIndex(elements.size() - 1);
        }
        repaint();
    }

    /**
     * @see List#size()  
     */
    public final int size() {
        return elements.size();
    }

    /**
     * @see Canvas#showNotify() 
     */
    protected void showNotify() {
        super.showNotify();

        if (!SafeGestureRegistrationManager.register(this, zone)) {
            throw new RuntimeException(
                    "GestureRegistrationManager.register() failed!");
        }
        SafeGestureRegistrationManager.setListener(this, this);
        startScrollingAnimation(0);
    }

    /**
     * @see Canvas#hideNotify() 
     */
    protected void hideNotify() {
        super.hideNotify();
        SafeGestureRegistrationManager.unregister(this, zone);
        stopScrollAnimation();
    }

    /**
     * @see Canvas#sizeChanged(int, int) 
     */
    protected void sizeChanged(int w, int h) {
        width = w;
        height = h;
        zone.setRectangle(0, 0, w, h);
        refreshContentHeight = true;
        repaint();
    }

    /**
     * @see List#setSelectCommand(javax.microedition.lcdui.Command)   
     */
    public void setSelectCommand(Command c) {
        if (listCommand != null && c != null && listType != List.MULTIPLE) {
            removeCommand(listCommand);
            listCommand = new Command(c.getLabel(), c.getLongLabel(),
                Command.OK, 1);
            addCommand(listCommand);
        }
        selectCommand = c;
    }

    /**
     * @see List#setCommandListener(javax.microedition.lcdui.CommandListener)    
     */
    public void setCommandListener(CommandListener l) {
        commandListener = l;
    }

    /**
     * @see List#isSelected(int)   
     */
    public boolean isSelected(int elementNum)
        throws IndexOutOfBoundsException {
        try {
            return ((IElement) elements.elementAt(elementNum)).isSelected();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(e.getMessage());
        }
    }

    /**
     * @see List#getSelectedIndex()   
     */
    public int getSelectedIndex() {
        for (int i = 0; i < elements.size(); i++) {
            if (((IElement) elements.elementAt(i)).isSelected()) {
                return i;
            }
        }

        return -1;
    }

    /**
     * @see List#getSelectedFlags(boolean[]) 
     */
    public int getSelectedFlags(boolean[] selectedArray_return) {
        if (selectedArray_return == null) {
            throw new NullPointerException();
        }
        if (selectedArray_return.length != elements.size()) {
            throw new IllegalArgumentException();
        }
        int selectedCounter = 0;
        for (int i = 0; i < selectedArray_return.length; i++) {
            selectedArray_return[i] =
                ((IElement) elements.elementAt(i)).isSelected();
            selectedCounter += selectedArray_return[i] ? 1 : 0;
        }
        return selectedCounter;
    }

    /**
     * @see List#setSelectedIndex(int, boolean)    
     */
    public void setSelectedIndex(int elementNum, boolean selected)
        throws IndexOutOfBoundsException {
        try {
            ((IElement) elements.elementAt(elementNum)).setSelected(selected);
            updateListCommand();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(e.getMessage());
        }
    }

    /**
     * @see List#setSelectedFlags(boolean[])   
     */
    public void setSelectedFlags(boolean[] selectedArray)
        throws NullPointerException {
        if (selectedArray == null) {
            throw new NullPointerException();
        }
        if (selectedArray.length != elements.size()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < selectedArray.length; i++) {
            ((IElement) elements.elementAt(i)).setSelected(selectedArray[i]);
        }
        updateListCommand();
    }

    /**
     * @see List#setFitPolicy(int) 
     */
    public void setFitPolicy(int fitPolicy) {
        if (fitPolicy != Choice.TEXT_WRAP_DEFAULT
            && fitPolicy != Choice.TEXT_WRAP_OFF
            && fitPolicy != Choice.TEXT_WRAP_ON) {
            throw new IllegalArgumentException();
        }
        boolean oldWrapText = wrapText;
        wrapText = fitPolicy == Choice.TEXT_WRAP_ON;
        if (oldWrapText != wrapText) {
            refreshContentHeight = true;
        }
    }

    /**
     * @see Canvas#pointerPressed(int, int) 
     */
    protected final void pointerPressed(int x, int y) {
        setFocusedElementIndex(elementIndexAt(x, y - translateY));
        repaint();
    }

    /**
     * @see Canvas#pointerReleased(int, int) 
     */
    protected final void pointerReleased(int x, int y) {
        setFocusedElementIndex(-1);
    }

    /**
     * @see Canvas#keyPressed(int) 
     */
    protected void keyPressed(int keyCode) {
        int gameKeyCode = getGameAction(keyCode);

        if (focusedElementIndex == -1) {
            setFocusedElementIndex(0);
        }
        else {
            switch (gameKeyCode) {
                case Canvas.UP:
                    if (focusedElementIndex > 0) {
                        IElement element = (IElement) elements.elementAt(focusedElementIndex - 1);
                        if (element.isSeparator()) {
                            focusedElementIndex--;
                            keyPressed(keyCode);
                            return;
                        }
                        // Move one line up
                        setFocusedElementIndex(focusedElementIndex - 1);
                    }
                    else {
                        // Went over the top, move to the last element
                        setFocusedElementIndex(elements.size() - 1);
                    }
                    scrollToElement(focusedElementIndex);
                    break;
                case Canvas.DOWN:
                    if (focusedElementIndex + 1 <= elements.size() - 1) {
                        IElement element = (IElement) elements.elementAt(focusedElementIndex + 1);
                        if (element.isSeparator()) {
                            focusedElementIndex++;
                            keyPressed(keyCode);
                            return;
                        }
                        // Move one line down
                        setFocusedElementIndex(focusedElementIndex + 1);
                    }
                    else {
                        // Went past the last one, move to the first element
                        setFocusedElementIndex(0);
                    }
                    scrollToElement(focusedElementIndex);
                    break;
                case Canvas.FIRE:
                    handleSelectEvent();
                    break;
            }
        }

        repaint();
    }

    protected void setFocusedElementIndex(int focusedElementIndex) {
        this.focusedElementIndex = focusedElementIndex;
        updateListCommand();
    }

    private void updateListCommand() {
        if (!hasPointerEvents()) {
            // Update list command
            try {
                if (isSelected(focusedElementIndex)) {
                    if (listCommand == MARK) {
                        listCommand = UNMARK;
                        addCommand(UNMARK);
                        removeCommand(MARK);
                    }
                }
                else if (listCommand == UNMARK) {
                    listCommand = MARK;
                    addCommand(MARK);
                    removeCommand(UNMARK);
                }
            }
            catch (IndexOutOfBoundsException e) {
                // no element in focus
            }
        }
    }

    /**
     * Scroll to show the element and focus the element in non touch devices.
     * 
     * @param elementNum 
     */
    public void scrollToElement(int elementNum) {
        if (elementNum >= 0 && elementNum < elements.size()) {
            stopScrollAnimation();
            int topY = 0;
            int bottomY = 0;
            for (int i = 0; i < elements.size(); i++) {
                if (i < elementNum) {
                    topY += ((IElement) elements.elementAt(i)).getHeight();
                }
                else if (i == elementNum) {
                    bottomY = topY
                        + ((IElement) elements.elementAt(i)).getHeight();
                }
            }
            if (topY < Math.abs(translateY)) {
                translateY = -topY;
                repaint();
            }
            else if (bottomY > Math.abs(translateY) + height - bottomPadding) {
                translateY = height - bottomPadding - bottomY;
                repaint();
            }
            showScrollBar();
            if (!hasPointerEvents()) {
                setFocusedElementIndex(elementNum);
                repaint();
            }
        }
    }

    private void startScrollingAnimation(int startSpeed) {
        startSpeed = Math.max(Math.min(MAX_SCROLLING_VELOCITY, startSpeed),
            -MAX_SCROLLING_VELOCITY);
        animator = new Animator(translateY, startSpeed,
            Math.min(height - getContentHeight(), 0), 0, SCROLLING_FRICTION,
            RUBBERBAND_FORCE);
        animator.start();
    }

    private void animate(int yOffset, boolean lastFrame) {
        translateY = yOffset;
        showScrollBar();
        repaint();
        if (lastFrame) {
            animator = null;
        }
    }

    /**
     * @see com.nokia.mid.ui.gestures.GestureListener#gestureAction(
     * java.lang.Object, com.nokia.mid.ui.gestures.GestureInteractiveZone, 
     * com.nokia.mid.ui.gestures.GestureEvent)
     */
    public final void gestureAction(Object container,
        SafeGestureInteractiveZone zone, SafeGestureEvent event) {

        int contentHeight = getContentHeight();
        switch (event.getType()) {
            case SafeGestureInteractiveZone.GESTURE_DRAG: {
                if (animator == null || (translateY > height - contentHeight
                    && translateY < 0)) {
                    stopScrollAnimation();
                    if (theme.useRubberBandEffect) {
                        translateY += event.getDragDistanceY();
                    }
                    else {
                        translateY = Math.min(Math.max(height - contentHeight,
                            translateY + event.getDragDistanceY()), 0);
                    }
                    showScrollBar();
                }
                break;
            }
            case SafeGestureInteractiveZone.GESTURE_DROP: {
                stopScrollAnimation();
                startScrollingAnimation(0);
                break;
            }
            case SafeGestureInteractiveZone.GESTURE_LONG_PRESS:
            case SafeGestureInteractiveZone.GESTURE_TAP: {
                if (((translateY >= height - contentHeight
                    || height - contentHeight >= 0) && translateY <= 0)
                    && !stopScrollAnimation()) {
                    handleSelectEvent(event);
                }
                break;
            }
            case SafeGestureInteractiveZone.GESTURE_FLICK: {
                if (animator == null || (translateY > height - contentHeight
                    && translateY < 0)) {
                    stopScrollAnimation();
                    // Start vertical flick only if the gesture is 
                    // more vertical than horizontal.
                    float angle = Math.abs(event.getFlickDirection());
                    if ((angle > (Math.PI / 4) && angle < (3 * Math.PI / 4))) {
                        startScrollingAnimation(event.getFlickSpeedY());
                    }
                    else {
                        startScrollingAnimation(0);
                    }
                }
                break;
            }
            default:
                break;
        }
        setFocusedElementIndex(-1);
        repaint();
    }

    /**
     * Show the scroll bar for a time configured in the theme.
     */
    protected synchronized void showScrollBar() {
        scrollBarVisible = true;
        if (!theme.hideScrollBar) {
            return;
        }

        if (hideScrollBarTask == null) {
            hideScrollBarTask = new DelayedTask(theme.hideScrollBarDelay) {

                public void run() {
                    hideScrollBar();
                }
            };
            hideScrollBarTask.start();
        }
        else {
            hideScrollBarTask.resetDelay();
        }
    }

    private synchronized void hideScrollBar() {
        scrollBarVisible = false;
        hideScrollBarTask = null;
        repaint();
    }

    /**
     * Common handler function for selection events.
     * 
     * @param event Gesture event.
     */
    private void handleSelectEvent(SafeGestureEvent event) {
        final int elementIndex = elementIndexAt(event.getStartX(), event.
            getStartY() - translateY);
        if (elementIndex > -1) {
            // set tapped element selected
            if (listType == List.MULTIPLE) {
                IElement element = (IElement) elements.elementAt(elementIndex);
                element.setSelected(!element.isSelected());
            }
            else {
                for (int i = 0; i < elements.size(); i++) {
                    ((IElement) elements.elementAt(i)).setSelected(
                        elementIndex == i);
                }
            }
            updateListCommand();
            repaint();
            if (event.getType() == SafeGestureInteractiveZone.GESTURE_TAP
                && selectCommand != null
                && commandListener != null) {
                this.commandListener.commandAction(selectCommand, this);
            }
        }
    }

    /**
     * Select the focused element in non touch devices.
     */
    protected void handleSelectEvent() {
        if (focusedElementIndex > -1 && focusedElementIndex < elements.size()) {
            if (listType == List.MULTIPLE) {
                IElement element = (IElement) elements.elementAt(
                    focusedElementIndex);
                element.setSelected(!element.isSelected());
            }
            else {
                for (int i = 0; i < elements.size(); i++) {
                    ((IElement) elements.elementAt(i)).setSelected(
                        focusedElementIndex == i);
                }
            }
            updateListCommand();
            repaint();
            if (selectCommand != null && commandListener != null) {
                this.commandListener.commandAction(selectCommand, this);
            }
        }
    }

    /**
     * Finds the index of an element with the given coordinates.
     * 
     * @param x X-coordinate.
     * @param y Y-coordinate.
     * @return Index of an element at the given coordinates or -1 if no element found.
     */
    protected int elementIndexAt(int x, int y) {
        int heightSoFar = 0;
        int heightNext = 0;

        // Go through all items (pretend that list item heights can vary).
        for (int i = 0; i < elements.size(); i++) {
            heightSoFar = heightNext;
            IElement element = (IElement) elements.elementAt(i);
            heightNext += element.getHeight();

            if (y >= heightSoFar && y <= heightNext && !element.isSeparator()) {
                return i;
            }
        }

        return -1;
    }

    /**
     * @return true if animation was stopped.
     */
    protected boolean stopScrollAnimation() {
        if (animator != null) {
            animator.close();
            animator = null;
            return true;
        }
        return false;
    }

    private int getContentHeight() {
        if (refreshContentHeight) {
            refreshContentHeight = false;
            contentHeight = bottomPadding + calculateHeight();
            if (startScrollingAnimationAfterContentHeightRefreshed) {
                startScrollingAnimationAfterContentHeightRefreshed = false;
                startScrollingAnimation(0);
            }
        }
        return contentHeight;
    }

    /**
     * Paints the list.
     * @param g
     */
    protected final void paint(Graphics g) {
        int yOffset = translateY;

        // Background
        if (theme.drawBackground) {
            g.setColor(theme.backgroundColor);
            g.fillRect(0, 0, width, height);
        }

        drawElements(g, yOffset, width, height, focusedElementIndex);

        // Detemine the need for scrollbar, and the height for it.
        int contentHeight = getContentHeight();
        if (contentHeight > height - bottomPadding && scrollBarVisible) {
            int scrollBarMaxHeight = height - bottomPadding
                - 2 * theme.scrollBarMarginTopAndBottom;
            int scrollBarHeight = Math.max(
                scrollBarMaxHeight * height / contentHeight + 1,
                theme.scrollBarWidth);
            int top = theme.scrollBarMarginTopAndBottom
                + scrollBarMaxHeight * (-yOffset) / contentHeight;
            int bottom = top + scrollBarHeight;
            if (top < theme.scrollBarMarginTopAndBottom) {
                top = theme.scrollBarMarginTopAndBottom;
                bottom = Math.max(top + theme.scrollBarWidth, bottom);
            }
            else {
                int maxBottom = height - bottomPadding
                    - theme.scrollBarMarginTopAndBottom;
                if (bottom > maxBottom) {
                    bottom = maxBottom;
                    top = Math.min(bottom - theme.scrollBarWidth, top);
                }
            }
            g.setClip(0, 0, width, height);
            g.setColor(theme.scrollBarColor);
            g.fillRoundRect(width - theme.scrollBarWidth
                - theme.scrollBarMarginRight,
                top,
                theme.scrollBarWidth,
                bottom - top,
                theme.scrollBarArc,
                theme.scrollBarArc);
        }
    }

    /**
     * @return height of the content in the list
     */
    protected int calculateHeight() {
        int h = 0;
        for (int i = 0, size = elements.size(); i < size; i++) {
            h += ((IElement) elements.elementAt(i)).getHeight();
        }
        return h;
    }

    /**
     * Draw all elements in the list.
     * 
     * @param g
     * @param yOffset scrolling offset
     * @param width of the view area
     * @param height of the view area
     * @param focusedElementIndex index of the focused element
     */
    protected void drawElements(Graphics g, int yOffset,
        int width, int height, int focusedElementIndex) {
        int heightSoFar = 0;
        int heightNext = 0;
        for (int i = 0, size = elements.size(); i < size; i++) {
            heightSoFar = heightNext;
            heightNext += ((IElement) elements.elementAt(i)).getHeight();

            if (heightNext + yOffset < 0) {
                // Item is not visible -> skip drawing it.
                continue;

            }
            else if (heightSoFar + yOffset > height) {
                // Item would be drawn "under" the visible area 
                // -> stop drawing.
                break;
            }
            drawElement(g, i, heightSoFar + yOffset, i == focusedElementIndex);
        }
    }

    protected void drawElement(final Graphics g, final int index, final int y,
        final boolean focused) {
        final IElement element = (IElement) elements.elementAt(index);
        final int elementHeight = element.getHeight();

        // Ensure that element does not overlap outside allocated area.
        g.setClip(0, y, width, elementHeight);

        if (focused) {
            g.setColor(theme.backgroundColorFocused);
            g.fillRect(0, y + theme.backgroundMarginTopAndBottom,
                width, elementHeight - 2 * theme.backgroundMarginTopAndBottom);
        }

        if (theme.borderType == Theme.BORDER_TOUCH_AND_TYPE) {
            try {
                Class.forName("com.nokia.mid.ui.DirectUtils");
                Class.forName("com.nokia.mid.ui.DirectGraphics");
                DirectGraphics dg = DirectUtils.getDirectGraphics(g);
                int borderWidth = width - theme.scrollBarMarginRight
                    - theme.scrollBarWidth;
                dg.setARGBColor(theme.borderColorDark);
                g.fillRect(0, y + elementHeight - 2, borderWidth, 1);
                dg.setARGBColor(theme.borderColorLight);
                g.fillRect(0, y + elementHeight - 1, borderWidth, 1);
            }
            catch (ClassNotFoundException e) {
            }
        }

        int x0 = theme.textOnlyMarginLeft;
        int y0 = y;
        Image image = element.getImagePart();
        if (image != null) {
            x0 = theme.imageMarginLeft;
            g.drawImage(image, x0, y0 + elementHeight / 2,
                Graphics.LEFT | Graphics.VCENTER);
            x0 += image.getWidth() + theme.textMarginLeftAndRight;
        }

        if (listType != List.IMPLICIT) {
            String imagePath = listType == List.EXCLUSIVE
                ? theme.toggleExclusiveImageSelected
                : theme.toggleMultipleImageSelected;
            if (imagePath != null && selectedImage == null) {
                try {
                    selectedImage = Image.createImage(imagePath);
                }
                catch (IOException e) {
                    // image not found
                }
            }
            imagePath = listType == List.EXCLUSIVE
                ? theme.toggleExclusiveImage : theme.toggleMultipleImage;
            if (imagePath != null && unselectedImage == null) {
                try {
                    unselectedImage = Image.createImage(imagePath);
                }
                catch (IOException e) {
                    // image not found
                }
            }
            Image toggleImage = element.isSelected() ? selectedImage
                : unselectedImage;
            if (toggleImage != null) {
                g.drawImage(toggleImage, width - theme.selectedImageMarginRight,
                    y + elementHeight / 2, Graphics.RIGHT | Graphics.VCENTER);
            }
        }

        g.setColor((focused) ? theme.textColorFocused : theme.textColor);
        g.setFont(element.getFont());
        final int lineHeight = element.getFont().getHeight();
        if (wrapText) {
            Vector text = element.getWrappedText();
            y0 += (elementHeight - lineHeight * text.size()) / 2
                - theme.textMarginTopAndBottom + lineHeight;
            for (int i = 0; i < text.size(); i++) {
                g.drawString((String) text.elementAt(i), x0, y0, Graphics.LEFT
                    | Graphics.BOTTOM);
                y0 += lineHeight;
            }
        }
        else {
            y0 += (elementHeight + lineHeight) / 2;
            g.drawString(element.getTruncatedText(),
                x0, y0, Graphics.LEFT | Graphics.BOTTOM);
        }
    }

    /**
     * Element in the list.
     */
    protected final class Element implements IElement {

        private String stringPart;
        private Image imagePart;
        private boolean selected = false;
        private String truncatedText = null;
        private int truncatedTextWidth = -1;
        private Vector wrappedText = null;
        private int wrappedTextWidth = -1;

        public Element(String stringPart, Image imagePart) {
            if (stringPart == null) {
                throw new NullPointerException();
            }
            this.stringPart = stringPart;
            this.imagePart = imagePart;
        }

        public Image getImagePart() {
            return imagePart;
        }

        public String getStringPart() {
            return stringPart;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public int getHeight() {
            if (wrapText) {
                wrapText();
            }
            return Math.max(wrapText ? wrappedText.size()
                * getFont().getHeight() : 0, theme.elementMinHeight) + 2
                * theme.textMarginTopAndBottom;
        }

        public Font getFont() {
            return theme.font;
        }

        public String getTruncatedText() {
            if (width != truncatedTextWidth) {
                truncatedText = truncate(stringPart, getFont(), getTextWidth());
                truncatedTextWidth = width;
            }
            return truncatedText;
        }

        public String getTruncatedText(int textWidth) {
            if (textWidth != truncatedTextWidth) {
                truncatedText = truncate(stringPart, getFont(), textWidth);
                truncatedTextWidth = textWidth;
            }
            return truncatedText;
        }

        public Vector getWrappedText() {
            wrapText();
            return wrappedText;
        }
        
        public boolean isSeparator() {
            return false;
        }

        private void wrapText() {
            if (width != wrappedTextWidth) {
                wrappedText = wrapTextToWidth(stringPart, getTextWidth(),
                    getFont());
                wrappedTextWidth = width;
            }
        }

        private int getTextWidth() {
            int textWidth = width - theme.textMarginLeftAndRight;
            if (imagePart != null) {
                textWidth -= theme.imageMarginLeft;
                textWidth -= imagePart.getWidth();
                textWidth -= theme.textMarginLeftAndRight;
            }
            else {
                textWidth -= theme.textOnlyMarginLeft;
            }
            if (listType != List.IMPLICIT) {
                textWidth -= theme.selectedImageMarginRight;
                textWidth -= theme.selectedImageWidth;
            }
            return textWidth;
        }

        public void resetCaches() {
            truncatedText = null;
            truncatedTextWidth = -1;
            wrappedText = null;
            wrappedTextWidth = -1;
        }
    }

    /**
     * Truncates the string to fit the maxWidth. 
     * If truncated, an elipsis "..." is displayed to indicate this.
     * 
     * @param str string to be truncated
     * @param font font to be used
     * @param maxWidth max width of the line
     * @return String - truncated string with ellipsis added to end of the string
     */
    private static String truncate(final String text, final Font font,
        final int maxWidth) {
        if (font.stringWidth(text) <= maxWidth) {
            return text;
        }

        final String ELIPSIS = "...";
        int length = text.length();
        int position = 0;
        for (int i = 0; i < length && font.stringWidth(
            text.substring(0, i) + ELIPSIS)
            <= maxWidth; i++) {
            position = i;
        }
        return text.substring(0, position) + ELIPSIS;
    }

    /**
     * Wrap text to multiple lines.
     * 
     * @param text
     * @param wrapWidth
     * @param font
     * @return a vector of text lines
     */
    private static Vector wrapTextToWidth(final String text, int wrapWidth,
        final Font font) {
        if (wrapWidth < 20) {
            wrapWidth = 200;
        }

        Vector lines = new Vector();

        int start = 0;
        int position = 0;
        int length = text.length();
        while (position < length - 1) {
            start = position;
            int lastBreak = -1;
            int i = position;
            for (; i < length && font.stringWidth(
                text.substring(position, i))
                <= wrapWidth; i++) {
                if (text.charAt(i) == ' ') {
                    lastBreak = i;
                }
                else if (text.charAt(i) == '\n') {
                    lastBreak = i;
                    break;
                }
            }
            if (i == length) {
                position = i;
            }
            else if (lastBreak <= position) {
                position = i;
            }
            else {
                position = lastBreak;
            }

            lines.addElement(text.substring(start, position));

            if (position == lastBreak) {
                position++;
            }
        }

        return lines;
    }

    /**
     * Create a theme according to device colors.
     * 
     * @param display
     * @return 
     */
    public static Theme createTheme(Display display) {
        Theme theme = new CustomList.Theme();
        theme.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM));
        theme.setTextOnlyMarginLeft(36);
        theme.setElementMinHeight(36);
        theme.setBackgroundColor(display.getColor(Display.COLOR_BACKGROUND));
        theme.setBackgroundColorFocused(display.getColor(
            Display.COLOR_HIGHLIGHTED_BACKGROUND));
        theme.setTextColor(display.getColor(Display.COLOR_FOREGROUND));
        theme.setTextColorFocused(display.getColor(
            Display.COLOR_HIGHLIGHTED_FOREGROUND));
        return theme;
    }

    /**
     * Theme for a CustomList.
     */
    public static class Theme {

        public static final int BORDER_NONE = 0;
        public static final int BORDER_TOUCH_AND_TYPE = 1;
        protected boolean drawBackground = true;
        protected boolean hideScrollBar = true;
        protected boolean useRubberBandEffect = true;
        protected int borderType = BORDER_NONE;
        protected Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
            Font.SIZE_LARGE);
        protected int backgroundColor = 0x202020;
        protected int backgroundColorFocused = 0x1090c8;
        protected int textColor = 0xd8dcd8;
        protected int textColorFocused = 0xf8fcf8;
        protected int scrollBarColor = 0x888c88;
        protected int borderColorLight = 0x00000000;
        protected int borderColorDark = 0x00000000;
        protected int scrollBarWidth = 3;
        protected int scrollBarArc = 2;
        protected int scrollBarMarginRight = 4;
        protected int scrollBarMarginTopAndBottom = 5;
        protected int textOnlyMarginLeft = 12;
        protected int imageMarginLeft = 6;
        protected int textMarginLeftAndRight = 8;
        protected int textMarginTopAndBottom = 2;
        protected int backgroundMarginTopAndBottom = 1;
        protected int selectedImageMarginRight = 6;
        protected int elementMinHeight = 38;
        protected int hideScrollBarDelay = 1000;
        protected int selectedImageWidth = 40;
        protected String toggleExclusiveImage;
        protected String toggleExclusiveImageSelected;
        protected String toggleMultipleImage;
        protected String toggleMultipleImageSelected;

        public Theme() {
            toggleExclusiveImage = ImageLoader.CUSTOMLIST_TOGGLE_DISABLED;
            toggleExclusiveImageSelected = ImageLoader.CUSTOMLIST_TOGGLE_EXCLUSIVE_ACTIVE;
            toggleMultipleImage = ImageLoader.CUSTOMLIST_TOGGLE_DISABLED;
            toggleMultipleImageSelected = ImageLoader.CUSTOMLIST_TOGGLE_MULTIPLE_ACTIVE;
            selectedImageMarginRight = 15;
        }

        public int getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public int getBackgroundColorFocused() {
            return backgroundColorFocused;
        }

        public void setBackgroundColorFocused(int backgroundColorFocused) {
            this.backgroundColorFocused = backgroundColorFocused;
        }

        public int getBackgroundMarginTopAndBottom() {
            return backgroundMarginTopAndBottom;
        }

        public void setBackgroundMarginTopAndBottom(
            int backgroundMarginTopAndBottom) {
            this.backgroundMarginTopAndBottom = backgroundMarginTopAndBottom;
        }

        public int getBorderColorDark() {
            return borderColorDark;
        }

        public void setBorderColorDark(int borderColorDark) {
            this.borderColorDark = borderColorDark;
        }

        public int getBorderColorLight() {
            return borderColorLight;
        }

        public void setBorderColorLight(int borderColorLight) {
            this.borderColorLight = borderColorLight;
        }

        public int getBorderType() {
            return borderType;
        }

        public void setBorderType(int borderType) {
            this.borderType = borderType;
        }

        public boolean isDrawBackground() {
            return drawBackground;
        }

        public void setDrawBackground(boolean drawBackground) {
            this.drawBackground = drawBackground;
        }

        public int getElementMinHeight() {
            return elementMinHeight;
        }

        public void setElementMinHeight(int elementMinHeight) {
            this.elementMinHeight = elementMinHeight;
        }

        public Font getFont() {
            return font;
        }

        public void setFont(Font font) {
            this.font = font;
        }

        public boolean isHideScrollBar() {
            return hideScrollBar;
        }

        public void setHideScrollBar(boolean hideScrollBar) {
            this.hideScrollBar = hideScrollBar;
        }

        public int getHideScrollBarDelay() {
            return hideScrollBarDelay;
        }

        public void setHideScrollBarDelay(int hideScrollBarDelay) {
            this.hideScrollBarDelay = hideScrollBarDelay;
        }

        public int getImageMarginLeft() {
            return imageMarginLeft;
        }

        public void setImageMarginLeft(int imageMarginLeft) {
            this.imageMarginLeft = imageMarginLeft;
        }

        public int getScrollBarArc() {
            return scrollBarArc;
        }

        public void setScrollBarArc(int scrollBarArc) {
            this.scrollBarArc = scrollBarArc;
        }

        public int getScrollBarColor() {
            return scrollBarColor;
        }

        public void setScrollBarColor(int scrollBarColor) {
            this.scrollBarColor = scrollBarColor;
        }

        public int getScrollBarMarginRight() {
            return scrollBarMarginRight;
        }

        public void setScrollBarMarginRight(int scrollBarMarginRight) {
            this.scrollBarMarginRight = scrollBarMarginRight;
        }

        public int getScrollBarMarginTopAndBottom() {
            return scrollBarMarginTopAndBottom;
        }

        public void setScrollBarMarginTopAndBottom(
            int scrollBarMarginTopAndBottom) {
            this.scrollBarMarginTopAndBottom = scrollBarMarginTopAndBottom;
        }

        public int getScrollBarWidth() {
            return scrollBarWidth;
        }

        public void setScrollBarWidth(int scrollBarWidth) {
            this.scrollBarWidth = scrollBarWidth;
        }

        public int getSelectedImageMarginRight() {
            return selectedImageMarginRight;
        }

        public void setSelectedImageMarginRight(int selectedImageMarginRight) {
            this.selectedImageMarginRight = selectedImageMarginRight;
        }

        public int getSelectedImageWidth() {
            return selectedImageWidth;
        }

        public void setSelectedImageWidth(int selectedImageWidth) {
            this.selectedImageWidth = selectedImageWidth;
        }

        public int getTextColor() {
            return textColor;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }

        public int getTextColorFocused() {
            return textColorFocused;
        }

        public void setTextColorFocused(int textColorFocused) {
            this.textColorFocused = textColorFocused;
        }

        public int getTextMarginLeftAndRight() {
            return textMarginLeftAndRight;
        }

        public void setTextMarginLeftAndRight(int textMarginLeftAndRight) {
            this.textMarginLeftAndRight = textMarginLeftAndRight;
        }

        public int getTextMarginTopAndBottom() {
            return textMarginTopAndBottom;
        }

        public void setTextMarginTopAndBottom(int textMarginTopAndBottom) {
            this.textMarginTopAndBottom = textMarginTopAndBottom;
        }

        public int getTextOnlyMarginLeft() {
            return textOnlyMarginLeft;
        }

        public void setTextOnlyMarginLeft(int textOnlyMarginLeft) {
            this.textOnlyMarginLeft = textOnlyMarginLeft;
        }

        public String getToggleExclusiveImage() {
            return toggleExclusiveImage;
        }

        public void setToggleExclusiveImage(String toggleExclusiveImage) {
            this.toggleExclusiveImage = toggleExclusiveImage;
        }

        public String getToggleExclusiveImageSelected() {
            return toggleExclusiveImageSelected;
        }

        public void setToggleExclusiveImageSelected(
            String toggleExclusiveImageSelected) {
            this.toggleExclusiveImageSelected = toggleExclusiveImageSelected;
        }

        public String getToggleMultipleImage() {
            return toggleMultipleImage;
        }

        public void setToggleMultipleImage(String toggleMultipleImage) {
            this.toggleMultipleImage = toggleMultipleImage;
        }

        public String getToggleMultipleImageSelected() {
            return toggleMultipleImageSelected;
        }

        public void setToggleMultipleImageSelected(
            String toggleMultipleImageSelected) {
            this.toggleMultipleImageSelected = toggleMultipleImageSelected;
        }

        public boolean isUseRubberBandEffect() {
            return useRubberBandEffect;
        }

        public void setUseRubberBandEffect(boolean useRubberBandEffect) {
            this.useRubberBandEffect = useRubberBandEffect;
        }
    }

    /**
     * Animation thread. 
     */
    private class Animator
        extends Thread {

        private static final int MAX_FPS = 30;
        private volatile boolean run = true;
        private final int s0;
        private final int v0;
        private final int sMin;
        private final int sMax;
        private final int fF;
        private final int fR;

        public Animator(int startValue, int startSpeed, int minValue,
            int maxValue, int friction, int rubberbandForce) {
            if (minValue > maxValue || friction < 0 || rubberbandForce < 0) {
                throw new IllegalArgumentException();
            }
            this.s0 = startValue * 1000;
            this.v0 = startSpeed;
            this.sMin = minValue * 1000;
            this.sMax = maxValue * 1000;
            this.fF = startSpeed > 0 ? friction : -friction;
            this.fR = rubberbandForce;
        }

        /**
         * @see Thread#run()
         */
        public final void run() {
            if (s0 > sMin && s0 < sMax && v0 == 0) {
                animate(s0 / 1000, true);
                return;
            }
            else if (!theme.useRubberBandEffect && v0 == 0) {
                if (s0 < sMin) {
                    animate(sMin / 1000, true);
                    return;
                }
                else if (s0 > sMax) {
                    animate(sMax / 1000, true);
                    return;
                }
            }
            final long animationStartTime = System.currentTimeMillis();
            final int dtMin = 1000 / MAX_FPS;
            final int tMax = Math.abs(v0 * 1000 / fF);
            final int dsKineticMax = v0 / 2 * tMax;

            int t = 0;
            int tR0 = 0;
            int fRActive = 0;

            boolean lastFrame = false;
            long frameEndTime = animationStartTime;
            try {
                while (run) {
                    Thread.sleep(Math.max(dtMin - (int) (System.
                        currentTimeMillis() - frameEndTime), 0));

                    t = (int) (System.currentTimeMillis() - animationStartTime);

                    int s = s0 + (t > tMax ? dsKineticMax
                        : v0 * t - fF * t / 2000 * t);
                    if (theme.useRubberBandEffect) {
                        if (fRActive == 0) {
                            if (s < sMin) {
                                tR0 = t;
                                fRActive = fR;
                            }
                            else if (s > sMax) {
                                tR0 = t;
                                fRActive = -fR;
                            }
                        }
                        int tR = t - tR0;
                        s += fRActive / 2000 * tR * tR;
                    }
                    else {
                        if (s < sMin) {
                            s = sMin;
                            lastFrame = true;
                        }
                        else if (s > sMax) {
                            s = sMax;
                            lastFrame = true;
                        }
                    }

                    if (t > tMax || fRActive != 0) {
                        if (fRActive > 0) {
                            if (s > sMin) {
                                s = sMin;
                                lastFrame = true;
                            }
                        }
                        else if (fRActive < 0) {
                            if (s < sMax) {
                                s = sMax;
                                lastFrame = true;
                            }
                        }
                        else {
                            lastFrame = true;
                        }
                    }

                    if (run) {
                        animate(s / 1000, lastFrame);
                    }
                    if (lastFrame) {
                        run = false;
                    }

                    frameEndTime = System.currentTimeMillis();
                }
            }
            catch (Exception e) {
                animate(0, true);
            }
        }

        /**
         * Closes this thread.
         */
        public final void close() {
            run = false;
        }
    }
}
