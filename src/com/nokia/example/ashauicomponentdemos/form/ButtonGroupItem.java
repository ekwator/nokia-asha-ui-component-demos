/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.form;

import com.nokia.uihelpers.Button;
import com.nokia.uihelpers.Button.Listener;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 * An Item containing a set of Buttons. Only a single button in the group can be
 * toggled at once.
 */
public class ButtonGroupItem
    extends CustomItem
    implements Listener {

    public interface ButtonGroupListener {

        void clicked(ButtonGroupItem buttonGroup, int buttonIndex);
    }
    
    public static final int MAX_BUTTONS = 3;
    private final int MARGIN = 10;
    private final int MIN_CONTENT_WIDTH = 100;
    private final int NONE_IS_ACTIVE = -1;
    private Button[] buttons;
    private Vector listeners;
    private int maxWidth;
    private Displayable container;
    private int type;
    private boolean[] previousState;

    public ButtonGroupItem(String label,
        String[] buttonLabels,
        int foregroundColor,
        int backgroundColor,
        int type,
        Displayable container)
        throws IllegalArgumentException {
        super(label);
                
        if (buttonLabels.length <= 0) {
            throw new IllegalArgumentException(
                "Needs at least one button label string");
        }
        else if (buttonLabels.length > MAX_BUTTONS) {
            throw new IllegalArgumentException(
                "Too many button label strings (maximum is three)");
        }

        this.container = container;
        this.type = type;

        // Create the buttons
        buttons = new Button[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            Button button = new Button(buttonLabels[i], type);
            button.setY(0);
            button.setToggleable(true);
            button.addListener(this);
            buttons[i] = button;
        }

        // Always set the first one pressed by default for SEGMENTED buttons
        if (type == Button.SEGMENTED) {
            buttons[0].setPressed(true);
        }

        setHorizontalSizes();

        listeners = new Vector();
        previousState = new boolean[buttons.length];        
    }

    public void addListener(ButtonGroupListener listener) {
        if (!listeners.contains(this)) {
            listeners.addElement(listener);
        }
    }

    public void clicked(Button button) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setPressed(false);
            if (buttons[i] == button) {
                button.setPressed(true);
                notifyClicked(i);
            }
        }
        repaint();
    }

    public int getActive() {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].isPressed()) {
                return i;
            }
        }
        return NONE_IS_ACTIVE;
    }

    public void setActive(int selectedIndex) {
        if (selectedIndex >= 0 && selectedIndex < buttons.length) {
            clicked(buttons[selectedIndex]);
        }
    }

    protected int getMinContentWidth() {
        return MIN_CONTENT_WIDTH;
    }

    protected int getMinContentHeight() {
        return getItemHeight();
    }
    
    private int getItemHeight() {
        if (buttons.length > 0) {
            return buttons[0].getHeight();
        }
        else {
            Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
                Font.SIZE_LARGE);
            return font.getHeight() * 2;
        }
    }

    protected int getPrefContentWidth(int height) {
        return maxWidth;
    }

    protected int getPrefContentHeight(int width) {
        return getItemHeight();
    }

    protected void sizeChanged(int width, int height) {
        // Update the horizontal sizes of the elements, called automatically
        // when an orientation change occurs
        setHorizontalSizes();
    }

    protected void paint(Graphics g, int w, int h) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].render(g, i, buttons.length);
        }
    }

    protected void pointerPressed(int pointerX, int pointerY) {
        for (int i = 0; i < buttons.length; i++) {
            previousState[i] = buttons[i].isPressed();
            buttons[i].touchDown(pointerX, pointerY);
        }
        repaint();
    }
    
    protected void pointerReleased(int pointerX, int pointerY) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].touchUp(pointerX, pointerY);
        }
        
        // If pointer was dragged outside of the item, restore earlier
        // selections for SEGMENTED buttons        
        if (type == Button.SEGMENTED && 
                (pointerX < 0 ||
                pointerX > getPrefContentWidth(0) ||
                pointerY < 0 ||
                pointerY > getPrefContentHeight(0))) {
            for (int i = 0; i < previousState.length; i++) {
                buttons[i].setPressed(previousState[i]);
            }
        }

        repaint();
    }
    
    /**
     * Required for nontouch devices, responds to keypresses
     * @param dir
     * @param viewportWidth
     * @param viewportHeight
     * @param visRect_inout
     * @return 
     */
    protected boolean traverse(int dir,
            int viewportWidth,
            int viewportHeight,
            int[] visRect_inout) {
        return true;
    }

    private void setHorizontalSizes() {
        maxWidth = (container.getWidth() - 2 * MARGIN);
        int buttonWidth = (container.getWidth() - 2 * MARGIN) / buttons.length;
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setWidth(buttonWidth);
            buttons[i].setX(i * buttonWidth);
        }
    }
    
    private int focusedIndex() {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].isFocused()) {
                return i;
            }
        }
        return -1;
    }
    
    private void removeFocuses() {
       for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFocused(false);
        }
    }
    
    private void notifyClicked(int index) {
        int length = listeners.size();
        for (int i = 0; i < length; i++) {
            ((ButtonGroupListener) listeners.elementAt(i)).clicked(this, index);
        }
        // If the type is not SEGMENTED, reset all buttons
        if (type != Button.SEGMENTED) {
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setPressed(false);
            }
        }
    }
    
    protected void keyPressed(int keyCode) {
        int key = getGameAction(keyCode);
        
        if (type == Button.SEGMENTED) {
            int active = getActive();
            switch (key) {
                case Canvas.LEFT:
                    active = (active - 1 < 0) ? buttons.length - 1 : active - 1;
                    setActive(active);
                    break;
                case Canvas.RIGHT:
                    setActive((active + 1) % buttons.length);
                    break;
            }
        }
        else if (type == Button.REGULAR) {
            int focused = focusedIndex();
            switch (key) {
                case Canvas.LEFT:
                    if (focused > 0) {
                        removeFocuses();
                        buttons[focused - 1].setFocused(true);
                    }
                    break;
                case Canvas.RIGHT:
                    if (focused < buttons.length - 1) {
                        removeFocuses();
                        buttons[focused + 1].setFocused(true);
                    }
                    break;
                case Canvas.FIRE:
                    // Make the button look pressed
                    buttons[focused].setPressed(true);
                    break;
            }
        }
        repaint();
    }
    
    protected void keyReleased(int keyCode) {
        if (type == Button.REGULAR) {
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].isPressed()) {
                    buttons[i].setPressed(false);
                    notifyClicked(i);
                }
            }
            repaint();
        }
    }
}
