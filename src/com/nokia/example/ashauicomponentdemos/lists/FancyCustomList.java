/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/

package com.nokia.example.ashauicomponentdemos.lists;

import com.nokia.mid.ui.DirectGraphics;
import com.nokia.mid.ui.DirectUtils;
import com.nokia.uihelpers.CustomList;
import com.nokia.uihelpers.IElement;
import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;

/**
 * This class demonstrates how to extend CustomList to make a customized
 * version of it.
 */
public class FancyCustomList
    extends CustomList {

    private final int COLOR_IMPORTANCE_HIGH = 0xd72525;
    private final int COLOR_IMPORTANCE_MEDIUM = 0xd7b325;
    private final int COLOR_IMPORTANCE_LOW = 0x254bd7;
    
    public FancyCustomList(String title) {
        super(title, List.IMPLICIT);
    }
    
    public final int append(String titlePart, String contentPart,
            String timePart, Image imagePart, int importanceLevel) {
        elements.addElement(new FancyElement(titlePart, contentPart,
                timePart, imagePart, importanceLevel));
        repaint();
        return elements.size() - 1;
    }
    
    public final int appendSeparator() {
        elements.addElement(new FancySeparator());
        repaint();
        return elements.size() - 1;
    }
    
    protected void drawElement(final Graphics g, final int index, final int y,
            final boolean focused) {
        final FancyElement fancyElement = (FancyElement) elements.elementAt(index);
        final int elementHeight = fancyElement.getHeight();
        
        // Ensure that element does not overlap outside allocated area.
        g.setClip(0, y, width, elementHeight);
        
        if (focused) {
            g.setColor(theme.getBackgroundColorFocused());
            g.fillRect(0, y + theme.getBackgroundMarginTopAndBottom(),
                width, elementHeight - 2 * theme.getBackgroundMarginTopAndBottom());
        }
        
        if (theme.getBorderType() == Theme.BORDER_TOUCH_AND_TYPE) {
            try {
                Class.forName("com.nokia.mid.ui.DirectUtils");
                Class.forName("com.nokia.mid.ui.DirectGraphics");
                DirectGraphics dg = DirectUtils.getDirectGraphics(g);
                int borderWidth = width - theme.getScrollBarMarginRight()
                    - theme.getScrollBarWidth();
                dg.setARGBColor(theme.getBorderColorDark());
                g.fillRect(0, y + elementHeight - 2, borderWidth, 1);
                dg.setARGBColor(theme.getBorderColorLight());
                g.fillRect(0, y + elementHeight - 1, borderWidth, 1);
            }
            catch (ClassNotFoundException e) {
            }
        }
        
        int x0 = theme.getTextOnlyMarginLeft();
        
        if (fancyElement.isSeparator) {
            drawSeparator(g, x0, y, elementHeight, fancyElement.getTextColor());
        }
        else {
            drawFancyElement(fancyElement, g, x0, y, elementHeight, focused);
        }
    }
    
    private void drawSeparator(final Graphics g, 
            int x, int y, int elementHeight, int color) {
        g.setColor(color);
        int separatorHeight = 2;
        g.fillRect(x,
                    y + elementHeight / 2 - separatorHeight / 2,
                    width - theme.getTextOnlyMarginLeft() * 2,
                    separatorHeight);
    }
    
    private void drawFancyElement(FancyElement fancyElement,
            final Graphics g, int x0, int y,
            int elementHeight, boolean focused) {
        int y0 = y;
        // Draw the image
        Image image = fancyElement.getImagePart();
        if (image != null) {
            x0 = theme.getImageMarginLeft();
            g.drawImage(image, x0, y0 + elementHeight / 2,
                Graphics.LEFT | Graphics.VCENTER);
            x0 += image.getWidth() + theme.getTextMarginLeftAndRight();
        }

        // Draw title string
        g.setColor((focused) ? theme.getTextColorFocused() : theme.getTextColor());
        g.setFont(fancyElement.getFont());
        final int lineHeight = fancyElement.getFont().getHeight();
        int padding = 0;
        y0 += lineHeight + padding;
        g.drawString(fancyElement.getTitlePart(),
            x0, y0, Graphics.LEFT | Graphics.BOTTOM);

        // Draw content string
        Font smallFont = fancyElement.getSmallFont();
        g.setFont(smallFont);
        final int smallLineHeight = smallFont.getHeight();
        y0 += elementHeight - smallLineHeight * 1.5 - padding;
        g.drawString(fancyElement.getContentPart(),
            x0, y0, Graphics.LEFT | Graphics.BOTTOM);

        // Draw time
        int timeWidth = smallFont.stringWidth(fancyElement.getTimePart());
        int timeX = width - theme.getScrollBarMarginRight() -
                        theme.getScrollBarWidth() - timeWidth;
        int timeY = y + smallLineHeight + padding;
        g.drawString(fancyElement.getTimePart(),
            timeX, timeY, Graphics.LEFT | Graphics.BOTTOM);

        // Draw importance
        boolean drawImportance = true;
        switch (fancyElement.getImportanceLevel()) {
            case FancyElement.IMPORTANCE_HIGH:
                g.setColor(COLOR_IMPORTANCE_HIGH);
                break;
            case FancyElement.IMPORTANCE_MEDIUM:
                g.setColor(COLOR_IMPORTANCE_MEDIUM);
                break;
            case FancyElement.IMPORTANCE_LOW:
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
    
    protected class FancySeparator extends FancyElement {
        public FancySeparator() {
            super("", "", "", null, IMPORTANCE_NONE);
            isSeparator = true;
        }
    }
    
    protected class FancyElement implements IElement {
        
        public static final int IMPORTANCE_HIGH = 3;
        public static final int IMPORTANCE_MEDIUM = 2;
        public static final int IMPORTANCE_LOW = 1;
        public static final int IMPORTANCE_NONE = 0;
        
        protected String titlePart;
        protected String contentPart;
        protected String timePart;
        protected Image imagePart;
        protected int importanceLevel;
        protected boolean isSeparator = false;
        protected boolean selected = false;
        
        public FancyElement(String titlePart,
                String contentPart,
                String timePart,
                Image mainImagePart,
                int importanceLevel) {
            this.titlePart = titlePart;
            this.contentPart = contentPart;
            this.timePart = timePart;
            this.imagePart = mainImagePart;
            this.importanceLevel = importanceLevel;
        }

        public Image getImagePart() {
            return imagePart;
        }
        
        public int getImportanceLevel() {
            return importanceLevel;
        }
        
        public String getStringPart() {
            return titlePart;
        }
        
        public String getTitlePart() {
            return titlePart;
        }
        
        public String getContentPart() {
            return contentPart;
        }
        
        public String getTimePart() {
            return timePart;
        }
        
        public boolean isSeparator() {
            return isSeparator;
        }
        
        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public int getHeight() {
            if (isSeparator) {
                return theme.getFont().getHeight();
            }
            else {
                return theme.getFont().getHeight() * 2;
            }
        }
        
        public int getTextColor() {
            return theme.getTextColor();
        }
        
        public Font getSmallFont() {
            return Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        }
        
        public Font getFont() {
            return theme.getFont();
        }

        public String getTruncatedText() {
            return titlePart;
        }

        public String getTruncatedText(int textWidth) {
            return titlePart;
        }

        public Vector getWrappedText() {
            return null;
        }

        public void resetCaches() {
            
        }
    }
}
