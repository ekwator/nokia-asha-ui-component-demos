package com.nokia.uihelpers;

import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class CustomListItem extends CustomItem {
    
    protected static final int MARGIN_LEFT = 5;
    protected static final int ITEM_HEIGHT = 30;
    private static final int SCROLL_THRESHOLD = 3;
    protected static final int FOREGROUND_COLOR = 0x484848;
    protected static final int HIGHLIGHT_COLOR = 0xe1e1e1;
    
    protected String label;    
    protected Image image;
    
    protected boolean pressed;
    private int dragCounter;
    
    public CustomListItem(String label, Image image) {
        super(null);
        this.label = label;
        this.image = image;
    }

    public String getText() {
        return label;
    }

    public void setText(String text) {
        this.label = text;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    protected int getMinContentWidth() {
        return 0;
    }

    protected int getMinContentHeight() {
        return ITEM_HEIGHT;
    }

    protected int getPrefContentWidth(int height) {
        return 240;
    }

    protected int getPrefContentHeight(int width) {
        return ITEM_HEIGHT;
    }
    
    public void pointerPressed(int x, int y) {
        pressed = true;
        dragCounter = 0;
        repaint();
    }
    
    public void pointerReleased(int x, int y) {
        if (pressed) {
            notifyStateChanged();
        }
        pressed = false; 
        repaint();        
    }
    
    public void pointerDragged(int x, int y) {
        dragCounter++;
        if (pressed && dragCounter > SCROLL_THRESHOLD) {
            pressed = false;
            repaint();
        }        
    }
    
    protected void paint(Graphics g, int w, int h) {
        if (pressed) {
            g.setColor(HIGHLIGHT_COLOR);
            g.fillRect(0, 0, w, h);
        }
        if (label != null) {
            g.setColor(FOREGROUND_COLOR);
            g.drawString(label,
                    MARGIN_LEFT * 2,
                    h / 2 - g.getFont().getHeight() / 2,
                    Graphics.TOP | Graphics.LEFT);
        }
    }
    
    public void traverseOut() {
        pressed = false;
        repaint();
        super.traverseOut();
    }    
    
}
