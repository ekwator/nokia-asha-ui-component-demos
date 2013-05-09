/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.uihelpers;

import com.nokia.example.ashauicomponentdemos.utils.ImageLoader;

import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * A simple button class for custom button elements
 */
public class Button {

    public interface Listener {

        void clicked(Button button);
    }
    
    // Segmented buttons
    public static final int SEGMENTED = 0;
    // Regular buttons
    public static final int REGULAR = 1;
    
    private Vector listeners = new Vector();
    private final int RADIUS = 10;
    private String label;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean toggleable = false;
    private boolean pressed = false;
    private boolean focused = false;
    private Image upLeftLeft;
    private Image upLeftRight;
    private Image upRightLeft;
    private Image upRightRight;
    private Image upCenter;
    private Image downLeftLeft;
    private Image downLeftRight;
    private Image downRightLeft;
    private Image downRightRight;
    private Image downCenter;

    public static int getDefaultHeight() {
        Image image = ImageLoader.load(ImageLoader.BUTTON_UP_CENTER);
        return image.getHeight();
    }
    
    public Button(String label, int type) {
        this.label = label;
        loadImages(type);
        this.height = upCenter.getHeight();
    }

    public Button(String label, int x, int y, int width, int height, int type) {
        this.label = label;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        loadImages(type);
    }
    
    private void loadImages(int type) {
        if (type == REGULAR) {
            upLeftLeft = ImageLoader.load(ImageLoader.BUTTON_UP_LEFT_LEFT);
            upLeftRight = ImageLoader.load(ImageLoader.BUTTON_UP_LEFT_RIGHT);
            upRightLeft = ImageLoader.load(ImageLoader.BUTTON_UP_RIGHT_LEFT);
            upRightRight = ImageLoader.load(ImageLoader.BUTTON_UP_RIGHT_RIGHT);
            upCenter = ImageLoader.load(ImageLoader.BUTTON_UP_CENTER);
            downLeftLeft = ImageLoader.load(ImageLoader.BUTTON_DOWN_LEFT_LEFT);
            downLeftRight = ImageLoader.load(ImageLoader.BUTTON_DOWN_LEFT_RIGHT);
            downRightLeft = ImageLoader.load(ImageLoader.BUTTON_DOWN_RIGHT_LEFT);
            downRightRight = ImageLoader.load(ImageLoader.BUTTON_DOWN_RIGHT_RIGHT);
            downCenter = ImageLoader.load(ImageLoader.BUTTON_DOWN_CENTER);
        }
        else {
            downLeftLeft = ImageLoader.load(ImageLoader.SEGMENTED_ACTIVE_LEFT_LEFT);
            downLeftRight = ImageLoader.load(ImageLoader.SEGMENTED_ACTIVE_LEFT_RIGHT);
            downRightLeft = ImageLoader.load(ImageLoader.SEGMENTED_ACTIVE_RIGHT_LEFT);
            downRightRight = ImageLoader.load(ImageLoader.SEGMENTED_ACTIVE_RIGHT_RIGHT);
            downCenter = ImageLoader.load(ImageLoader.SEGMENTED_ACTIVE_CENTER);
            upLeftLeft = ImageLoader.load(ImageLoader.SEGMENTED_INACTIVE_LEFT_LEFT);
            upLeftRight = ImageLoader.load(ImageLoader.SEGMENTED_INACTIVE_LEFT_RIGHT);
            upRightLeft = ImageLoader.load(ImageLoader.SEGMENTED_INACTIVE_RIGHT_LEFT);
            upRightRight = ImageLoader.load(ImageLoader.SEGMENTED_INACTIVE_RIGHT_RIGHT);
            upCenter = ImageLoader.load(ImageLoader.SEGMENTED_INACTIVE_CENTER);
        }
    }

    public void setToggleable(boolean toggleable) {
        this.toggleable = toggleable;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public void touchUp(int x, int y) {
        if (contains(x, y)) {
            if (!toggleable) {
                pressed = false;
            }
            notifyClicked();
        } else {
            pressed = false;
        }
    }
    
    public void touchDown(int x, int y) {
        if (contains(x, y)) {
            pressed = true;
        }
    }

    public void addListener(Listener listener) {
        if (!listeners.contains(this)) {
            listeners.addElement(listener);
        }
    }

    public void notifyClicked() {
        int length = listeners.size();
        for (int i = 0; i < length; i++) {
            ((Listener) listeners.elementAt(i)).clicked(this);
        }
    }

    /**
     * Helper method, used when rendering a single button 
     * without a ButtonGroupItem
     * @param g The graphics context to draw onto
     */
    public void render(Graphics g) {
        render(g, 0, 1);
    }
    
    public void render(Graphics g, int index, int count) {
        int oldColor = g.getColor();
        
        g.translate(x, y);
        
        Image leftImage = null;
        Image rightImage = null;
        Image centerImage = null;
        
        boolean multipleButtons = (count > 1);

        // Choose the correct images for this button
        if (!multipleButtons) {
            // A single button
            if (pressed) {
                leftImage = downLeftLeft;
                rightImage = downRightRight;
                centerImage = downCenter;
            }
            else {
                leftImage = upLeftLeft;
                rightImage = upRightRight;
                centerImage = upCenter;
            }
        }
        else if (multipleButtons && index == 0) {
            // The first button
            if (pressed) {
                leftImage = downLeftLeft;
                rightImage = downLeftRight;
                centerImage = downCenter;
            }
            else {
                leftImage = upLeftLeft;
                rightImage = upLeftRight;
                centerImage = upCenter;
            }
        }
        else if (multipleButtons && index == count - 1) {
            // The last button
            if (pressed) {
                leftImage = downRightLeft;
                rightImage = downRightRight;
                centerImage = downCenter;
            }
            else {
                leftImage = upRightLeft;
                rightImage = upRightRight;
                centerImage = upCenter;
            }
        }
        else {
            // Middle button
            if (pressed) {
                leftImage = downRightLeft;
                rightImage = downLeftRight;
                centerImage = downCenter;
            }
            else {
                leftImage = upRightLeft;
                rightImage = upLeftRight;
                centerImage = upCenter;
            }
        }
        
        g.drawImage(leftImage, 0, 0, Graphics.TOP | Graphics.LEFT);
        for (int i = leftImage.getWidth(); i < width - rightImage.getWidth(); i++) {
            g.drawImage(centerImage, i, 0, Graphics.TOP | Graphics.LEFT);
        }
        g.drawImage(rightImage, width - rightImage.getWidth(), 0, Graphics.TOP | Graphics.LEFT);
        
        // Draw text
        Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        g.setFont(font);
        int fontHeight = font.getHeight();
        g.setColor(0xffffff);
        g.drawString(label, width / 2, height / 2 - fontHeight / 2, Graphics.TOP
            | Graphics.HCENTER);
        
        // Draw focused state
        if (focused) {
            g.setColor(0x000000);
            g.setStrokeStyle(Graphics.SOLID);
            g.drawRoundRect(0, 0, width, height, RADIUS, RADIUS);
            g.drawRoundRect(1, 1, width - 2, height - 2, RADIUS, RADIUS);
        }
        
        g.translate(-x, -y);
        g.setColor(oldColor);
    }

    public boolean contains(int hitX, int hitY) {
        return (hitX >= x && hitX <= x + width && hitY >= y && hitY <= y
            + height);
    }
}
