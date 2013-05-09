/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.canvas;

import com.nokia.example.ashauicomponentdemos.utils.VirtualKeyboardUtils;
import com.nokia.example.ashauicomponentdemos.utils.VirtualKeyboardUtils.KeyboardVisibilityListener;
import com.nokia.uihelpers.safetexteditor.SafeTextEditor;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.TextField;

/**
 * Builds on ExampleCanvas, adds the TextEditor support.
 */
public class TextEditorCanvas
    extends ExampleCanvas
    implements SafeTextEditor.TextEditorListener, KeyboardVisibilityListener {

    private final String[] HELP_TEXT = {
        "Available for full and",
        "normal canvas displays",
        "canvas text editor"
    };
    private final String DEFAULT_TEXT = "press here to enter text";
    private final String BACK_HINT = "(Press Back to continue)";
    private final int MARGIN = 10;
    private final int MAX_CHARS = 256;
    private final int SHIFT_AMOUNT = 50;
    private SafeTextEditor textEditor;
    private Rectangle textEditorRect;
    private boolean isUp = false;

    public TextEditorCanvas(String title, String content, CommandListener parent) {
        super(title, content, parent);
        createAndShowTextEditor();
        // Listen to the virtual keyboard visibility; it can hide
        // because user pressed enter or hardware back button. 
        VirtualKeyboardUtils.setVisibilityListener(this);
    }

    /**
     * Initializes the TextEditor and sets it visible
     */
    private void createAndShowTextEditor() {
        Font font = Font.getDefaultFont();
        textEditorRect = new Rectangle(MARGIN + 2, // x
            4 * font.getHeight() + 2 * MARGIN + 2, // y
            this.getWidth() - 2 * MARGIN, // width
            font.getHeight()); // height
        textEditor = SafeTextEditor.createTextEditor(); 
        textEditor.setMaxSize(MAX_CHARS);
        textEditor.setConstraints(TextField.ANY);
        textEditor.setSize(textEditorRect.width, textEditorRect.height);
        textEditor.setParent(this);
        textEditor.setPosition(textEditorRect.x, textEditorRect.y);
        textEditor.setBackgroundColor(0xFFFFFFFF);
        textEditor.setForegroundColor(0xFF000000);
        textEditor.setConstraints(TextField.NON_PREDICTIVE);
        textEditor.setTextEditorListener(this);
        textEditor.setVisible(true);
        textEditor.setContent(DEFAULT_TEXT);
    }

    public void paint(Graphics g) {
        if (isUp) {
            g.translate(0, -SHIFT_AMOUNT);
        }
        int w = getWidth();
        int h = getHeight();
        g.setColor(255, 255, 255); // white
        g.fillRect(0, 0, w, h);

        Font font = g.getFont();
        int fontHeight = font.getHeight();

        g.setColor(0, 51, 240); // blue
        g.drawString(HELP_TEXT[0], MARGIN, MARGIN, Graphics.TOP | Graphics.LEFT);
        g.drawString(HELP_TEXT[1], MARGIN, MARGIN + fontHeight,
            Graphics.TOP | Graphics.LEFT);
        g.drawString(HELP_TEXT[2], MARGIN, MARGIN + 3 * fontHeight,
            Graphics.TOP | Graphics.LEFT);

        // Draws a rectangle around the TextEditor, since by itself
        // the TextEditor is without any styles
        if (textEditor != null) {
            g.setColor(50, 50, 50);
            g.drawRect(textEditorRect.x - 2,
                textEditorRect.y - 5,
                textEditorRect.width + 2,
                textEditorRect.height + 10);
        }

        if (!isUp) {
            // Don't show if virtual keyboard is up. Otherwise it would be ok to show, but for
            // some reason text added after the text editor was drawn without removing the
            // previous graphic, so when the keyboard is hidden, the 'duplicate' is shown
            // for awhile when the keyboard is sliding down.
            g.setColor(0, 51, 240); // blue
            int backHintY = h - 20;
            g.drawString(BACK_HINT, MARGIN, backHintY, Graphics.BOTTOM | Graphics.LEFT);
        }
    }

    protected void pointerPressed(int x, int y) {
        if (textEditorRect != null) {
            // If the TextEditor exists, see if it doesn't have focus
            // and if the tap hit it
            if (!textEditor.hasFocus() && textEditorRect.hits(x, y)) {
                isUp = true;
                // Tap hit the editor, set focus and open keyboard
                // and move the TextEditor up to prevent the keyboard
                // from covering it
                textEditor.setFocus(true);
                textEditor.setPosition(textEditor.getPositionX(),
                                       textEditor.getPositionY() - SHIFT_AMOUNT);
                // If the text is the placeholder text, delete it
                if (textEditor.getContent().equals(DEFAULT_TEXT)) {
                    textEditor.setContent("");
                }
                
                repaint();
            }
            else if (!textEditorRect.hits(x, y) && isUp) {
                textEditor.setFocus(false);
            }
        }
    }

    public void inputAction(SafeTextEditor textEditor, int actions) {
    }

    public void hideNotify(int keyboardCategory) {
        updateEditorPosition();
        repaint();
    }

    public void showNotify(int keyboardCategory) {
        repaint();
    }

    private void updateEditorPosition() {
        isUp = false;
        // The tap didn't hit the editor, remove focus
        // and move the editor back down...
        textEditor.setFocus(false);
        textEditor.setPosition(textEditor.getPositionX(),
                               textEditor.getPositionY() + SHIFT_AMOUNT);
        // ...and if there is no text, insert the placeholder text
        if (textEditor.getContent().equals("")) {
            textEditor.setContent(DEFAULT_TEXT);
        }
    }

    /**
     * A private class to make tap handling easier
     */
    private static class Rectangle {

        public int x;
        public int y;
        public int height;
        public int width;

        public Rectangle(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public boolean hits(int hitX, int hitY) {
            return (x < hitX
                && x + width > hitX
                && y < hitY
                && y + height > hitY);
        }
    }
}
