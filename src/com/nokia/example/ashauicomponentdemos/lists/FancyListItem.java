package com.nokia.example.ashauicomponentdemos.lists;

import com.nokia.uihelpers.CustomListItem;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class FancyListItem extends CustomListItem {

    public static final int IMPORTANCE_HIGH = 3;
    public static final int IMPORTANCE_MEDIUM = 2;
    public static final int IMPORTANCE_LOW = 1;
    public static final int IMPORTANCE_NONE = 0;
    
    private static final int COLOR_IMPORTANCE_HIGH = 0xd72525;
    private static final int COLOR_IMPORTANCE_MEDIUM = 0xd7b325;
    private static final int COLOR_IMPORTANCE_LOW = 0x254bd7;
    
    protected String content;
    protected String time;
    protected int importance;

    public FancyListItem(String label,
                String content,
                String time,
                Image image,
                int importance) {
        super(label, image);
        this.content = content;
        this.time = time;
        this.importance = importance;
    }
    
    protected int getMinContentHeight() {
        return 50;
    }

    protected int getPrefContentHeight(int width) {
        return 50;
    }    
    
    protected void paint(Graphics g, int w, int h) {
        int x0 = 0;
        int y0 = 0;
        
        if (pressed) {
            g.setColor(HIGHLIGHT_COLOR);
            g.fillRect(0, 0, w, h);
        }
        
        // Draw the image 
        if (image != null) {
            x0 = MARGIN_LEFT;
            g.drawImage(image, x0, y0,
                Graphics.LEFT | Graphics.TOP);
            x0 += image.getWidth() + MARGIN_LEFT;
        }

        // Draw title string
        g.setColor(FOREGROUND_COLOR);
        final int lineHeight = g.getFont().getHeight();
        y0 += lineHeight;
        g.drawString(label, x0, y0, Graphics.LEFT | Graphics.BOTTOM);

        // Draw content string
        Font smallFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        g.setFont(smallFont);
        final int smallLineHeight = smallFont.getHeight();
        y0 += smallLineHeight;
        g.drawString(content, x0, y0, Graphics.LEFT | Graphics.BOTTOM);

        // Draw time
        int timeWidth = smallFont.stringWidth(time);
        int timeX = w - timeWidth;
        int timeY = smallLineHeight;
        g.drawString(time, timeX, timeY, Graphics.LEFT | Graphics.BOTTOM);

        // Draw importance
        boolean drawImportance = true;
        switch (importance) {
            case IMPORTANCE_HIGH:
                g.setColor(COLOR_IMPORTANCE_HIGH);
                break;
            case IMPORTANCE_MEDIUM:
                g.setColor(COLOR_IMPORTANCE_MEDIUM);
                break;
            case IMPORTANCE_LOW:
                g.setColor(COLOR_IMPORTANCE_LOW);
                break;
            default:
                // Invalid / no color, skip drawing importance
                drawImportance = false;
        }

        if (drawImportance) {
            int importanceIndicatorSize = smallFont.getHeight();
            g.fillRoundRect(timeX + timeWidth / 2 - importanceIndicatorSize / 2,
                            timeY + importanceIndicatorSize / 4,
                            importanceIndicatorSize,
                            importanceIndicatorSize,
                            importanceIndicatorSize, 
                            importanceIndicatorSize);
        }
    }
    
}
