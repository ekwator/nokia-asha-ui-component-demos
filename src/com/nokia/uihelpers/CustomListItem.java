package com.nokia.uihelpers;

import com.nokia.mid.ui.PopupList;
import com.nokia.mid.ui.PopupListItem;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class CustomListItem extends CustomItem {
    
    private static final int MARGIN_LEFT = 10;
    private static final int ITEM_HEIGHT = 30;
    private static final int SCROLL_THRESHOLD = 3;
    private static final int FOREGROUND_COLOR = 0x484848;
    private static final int HIGHLIGHT_COLOR = 0xe1e1e1;
    
    private String text;    
    private Image image;
    
    private boolean pressed;
    private int dragCounter;
    
    public CustomListItem(String text, Image image) {
        super(null);
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        if (text != null) {
            g.setColor(FOREGROUND_COLOR);
            g.drawString(text,
                    MARGIN_LEFT,
                    h / 2 - g.getFont().getHeight() / 2,
                    Graphics.TOP | Graphics.LEFT);
        }
    }
    
}
