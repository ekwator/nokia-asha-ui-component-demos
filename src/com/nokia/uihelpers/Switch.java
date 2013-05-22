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
 * Switch item
 */
public class Switch extends CustomItem {
    
    private final int MIN_CONTENT_WIDTH = 100;
    private final Image ACTIVE_IMAGE =
            ImageLoader.load(ImageLoader.SWITCH_ON);
    private final Image INACTIVE_IMAGE =
            ImageLoader.load(ImageLoader.SWITCH_OFF);
    
    private Vector listeners;
    private boolean value;
    private String activeLabel;
    private String inactiveLabel;
    private String currentLabel;
    private Displayable container;
    
    public interface SwitchListener {
        
        public void valueChanged(Switch aSwitch, boolean value);
    } 
    
    public Switch(String title, String activeLabel, String inactiveLabel,
            Displayable parent) {
        super(title);
        
        listeners = new Vector();
        container = parent;
        
        this.activeLabel = activeLabel;
        this.inactiveLabel = inactiveLabel;
        
        currentLabel = inactiveLabel;
        value = false;
    }
    
    public boolean getValue() {
        return value;
    }
    
    public void setValue(boolean newValue) {
        value = newValue;
        currentLabel = value ? activeLabel : inactiveLabel;
        repaint();
    }
    
    public void addListener(SwitchListener listener) {
        listeners.addElement(listener);
    }
    
    protected void pointerReleased(int x, int y) {
        setValue(!getValue());
        
        // Notify listeners of the new value
        int length = listeners.size();
        for (int i = 0; i < length; i++) {
            ((SwitchListener) listeners.elementAt(i)).valueChanged(this, value);
        }
    }
    
    protected void paint(Graphics g, int w, int h) {
        // Draw the label
        g.setColor(0x666666);
        Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        g.setFont(font);
        g.drawString(currentLabel, 0,
                     h / 2 - font.getHeight() / 2, Graphics.TOP | Graphics.LEFT);
        
        g.drawImage(value ? ACTIVE_IMAGE : INACTIVE_IMAGE, 
                    w - ACTIVE_IMAGE.getWidth(),
                    (int) h / 2 - ACTIVE_IMAGE.getHeight() / 2,
                    Graphics.TOP | Graphics.LEFT);
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
            case Canvas.FIRE:
                setValue(!getValue());
                break;
        }
        repaint();
    }
}
