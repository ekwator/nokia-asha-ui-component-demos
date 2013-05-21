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
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * Slider item
 */
public class Slider extends CustomItem {
    
    private final int MIN_CONTENT_WIDTH = 100;
    private final int HANDLE_SIZE = 12;
    private final int BACKGROUND_HEIGHT = 4;
    private final int MARGIN = (int)(HANDLE_SIZE * 1.5);
    private static Image SLIDER_HANDLE = ImageLoader.load(
        ImageLoader.SLIDER_HANDLE);
    
    private Vector listeners;
    private int minValue;
    private int maxValue;
    private int value;
    private int minX;
    private int maxX;
    private int valueStrX = 0;
    private int valueStrY = 0;
    private Displayable container;
    
    public interface SliderListener {
        
        public void valueChanged(Slider slider, int value);
    }
    
    public Slider(int min, int max, String title, Displayable parent) {
        super(title);
        if (min >= max) {
            throw new IllegalArgumentException(
                    "Minimum value cannot be larger than maximum value");
        }
        minValue = min;
        maxValue = max;
        value = minValue;
        
        listeners = new Vector();
        container = parent;
    }
    
    public int getMinValue() {
        return minValue;
    }
    
    public int getMaxValue() {
        return maxValue;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setMinValue(int min) {
        minValue = min;
    }
    
    public void setMaxValue(int max) {
        maxValue = max;
    }
    
    public void setValue(int newValue) {
        if (newValue < minValue) {
            newValue = minValue;
        }
        else if (newValue > maxValue) {
            newValue = maxValue;
        }
        value = newValue;
        repaint();
    }
    
    public void addListener(SliderListener listener) {
        listeners.addElement(listener);
    }
    
    protected void pointerPressed(int x, int y) {
        if (x >= MARGIN && x <= container.getWidth() - MARGIN) {
            int newValue = (int) (((float) x / maxX) * (maxValue - minValue));
            setValue(newValue);
        }
        else if (x < MARGIN) {
            setValue(minValue);
        }
        else if (x > container.getWidth() - MARGIN) {
            setValue(maxValue);
        }
    }
    
    protected void pointerDragged(int x, int y) {
        pointerPressed(x, y);
    }
    
    protected void pointerReleased(int x, int y) {
        // Notify listeners of the new value
        int length = listeners.size();
        for (int i = 0; i < length; i++) {
            ((SliderListener) listeners.elementAt(i)).valueChanged(this, value);
        }
    }
    
    protected void paint(Graphics g, int w, int h) {
        minX = 0 + MARGIN;
        maxX = w - MARGIN;
        valueStrX = w - MARGIN;
        valueStrY = 0;
        
        // Draw the strings: current, min and max values
        g.setColor(0x666666);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        g.drawString(Compatibility.toLowerCaseIfFT("Value is ") + (value), valueStrX, valueStrY, Graphics.TOP | Graphics.RIGHT);
        g.drawString(String.valueOf(minValue), 0, h, Graphics.BOTTOM | Graphics.LEFT);
        g.drawString(String.valueOf(maxValue), w, h, Graphics.BOTTOM | Graphics.RIGHT);
        
        g.fillRect(MARGIN, (int) h / 2 - BACKGROUND_HEIGHT / 2, w - MARGIN * 2, BACKGROUND_HEIGHT);
        
        // Calculate handle position based on the value: 0% = left, 100% = right
        int currentX = minX + (int) ((float) (value - 1) / (maxValue - minValue) * (maxX - minX));
        g.setColor(0x00ff00);
        g.drawImage(SLIDER_HANDLE, 
                    currentX,
                    (int) h / 2,
                    Graphics.VCENTER | Graphics.HCENTER);
    }
    
    protected int getMinContentWidth() {
        return MIN_CONTENT_WIDTH;
    }

    protected int getMinContentHeight() {
        Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
            Font.SIZE_LARGE);
        return font.getHeight() * 2;
    }

    protected int getPrefContentWidth(int width) {
        return container.getWidth();
    }

    protected int getPrefContentHeight(int height) {
        return getMinContentHeight();
    }
    
    protected void keyPressed(int keyCode) {
        int key = getGameAction(keyCode);
        switch (key) {
            case Canvas.LEFT:
                if (value > minValue) {
                    value--;
                }
                break;
            case Canvas.RIGHT:
                if (value < maxValue) {
                    value++;
                }
                break;
        }
        repaint();
    }
    
    protected boolean traverse(int dir,
            int viewportWidth,
            int viewportHeight,
            int[] visRect_inout) {
        if (Compatibility.isNonTouch()) {
            return false;
        }
        return true;
    }
}
