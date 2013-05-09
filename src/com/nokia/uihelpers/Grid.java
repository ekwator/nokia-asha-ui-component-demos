/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.uihelpers;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;

/**
 * Grid view custom UI control.
 * Provides a scrollbar for visualization.
 */
public class Grid
    extends CustomList {

    private int elementWidth = 80;
    private int elementHeight = 80;

    public Grid(String title)
        throws IllegalArgumentException {
        super(title, List.IMPLICIT);
    }

    public Grid(String title, String[] stringElements, Image[] imageElements)
        throws NullPointerException, IllegalArgumentException {
        super(title, List.IMPLICIT, stringElements, imageElements);
    }

    public void setElementSize(int elementWidth, int elementHeight) {
        this.elementWidth = elementWidth;
        this.elementHeight = elementHeight;
        refreshContentHeight = true;
        repaint();
    }

    protected int calculateHeight() {
        int elementsPerRow = getWidth() / elementWidth;
        return elementHeight * (elements.size() / elementsPerRow
            + (elements.size() % elementsPerRow > 0 ? 1 : 0));
    }

    protected int elementIndexAt(int x, int y) {
        int row = y / elementHeight;
        int column = x / elementWidth;
        int elementsPerRow = getWidth() / elementWidth;
        int index = row * elementsPerRow + column;
        if (index < 0 || index >= elements.size()) {
            index = -1;
        }
        return index;
    }

    protected void keyPressed(int keyCode) {
        int elementsPerRow = getWidth() / elementWidth;
        int gameKeyCode = getGameAction(keyCode);

        if (focusedElementIndex == -1) {
            focusedElementIndex = 0;
        }
        else {
            switch (gameKeyCode) {
                case Canvas.LEFT:
                    if (focusedElementIndex > 0) {
                        // Go one left
                        focusedElementIndex--;
                        // Check for row change
                        if (focusedElementIndex % elementsPerRow == elementsPerRow
                            - 1
                            && focusedElementIndex != 0) {
                        }
                    }
                    else {
                        // Went over the top, move to the last element
                        focusedElementIndex = elements.size() - 1;
                    }
                    scrollToElement(focusedElementIndex);
                    break;
                case Canvas.RIGHT:
                    if (focusedElementIndex < elements.size() - 1) {
                        // Go one right
                        focusedElementIndex++;
                        // Check for row change
                        if (focusedElementIndex % elementsPerRow == 0
                            && focusedElementIndex != elements.size() - 1) {
                        }
                    }
                    else {
                        // Went past the last one, move to the first element
                        focusedElementIndex = 0;
                    }
                    scrollToElement(focusedElementIndex);
                    break;
                case Canvas.UP:
                    if (focusedElementIndex >= elementsPerRow) {
                        // Move one line up
                        focusedElementIndex -= elementsPerRow;
                    }
                    else {
                        // Went over the top, move to the last row
                        int lastRow = (elements.size() - 1) / elementsPerRow;
                        focusedElementIndex += lastRow * elementsPerRow;
                        if (focusedElementIndex >= elements.size()) {
                            focusedElementIndex = elements.size() - 1;
                        }
                    }
                    scrollToElement(focusedElementIndex);
                    break;
                case Canvas.DOWN:
                    if (focusedElementIndex + elementsPerRow <= elements.size()
                        - 1) {
                        // Move one line down
                        focusedElementIndex += elementsPerRow;
                    }
                    else {
                        int lastRow = (elements.size() - 1) / elementsPerRow;
                        if (focusedElementIndex / elementsPerRow < lastRow) {
                            // Went past the last one but not the last row
                            focusedElementIndex = elements.size() - 1;
                        }
                        else {
                            // Went past the last one, move to the first row
                            focusedElementIndex -= lastRow * elementsPerRow;
                        }
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

    public void scrollToElement(int elementNum) {
        if (elementNum >= 0 && elementNum < elements.size()) {
            stopScrollAnimation();
            int elementsPerRow = getWidth() / elementWidth;
            int row = elementNum / elementsPerRow;
            int topY = row * elementHeight;
            int bottomY = topY + elementHeight;
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
                focusedElementIndex = elementNum;
                repaint();
            }
        }
    }

    protected void drawElements(Graphics g, int yOffset, int width, int height,
        int focusedElementIndex) {
        int elementsPerRow = width / elementWidth;
        int heightSoFar = 0;
        int heightNext = 0;
        for (int i = 0, size = elements.size(); i < size; i++) {
            if (i % elementsPerRow == 0) {
                heightSoFar = heightNext;
                heightNext += elementHeight;
            }

            if (heightNext + yOffset < 0) {
                // Item is not visible -> skip drawing it.
                continue;
            }
            else if (heightSoFar + yOffset > height) {
                // Item would be drawn "under" the visible area 
                // -> stop drawing.
                break;
            }
            drawElement(g, i, (i % elementsPerRow) * elementWidth,
                heightSoFar + yOffset,
                i == focusedElementIndex);
        }
    }

    private void drawElement(final Graphics g, final int index, final int x,
        final int y, final boolean focused) {
        final Element element = (Element) elements.elementAt(index);

        // Ensure that element does not overlap outside allocated area.
        g.setClip(x, y, elementWidth, elementHeight);

        int availableHeight = elementHeight - 2
            * theme.backgroundMarginTopAndBottom;

        if (focused) {
            g.setColor(theme.backgroundColorFocused);
            g.fillRect(x, y + theme.backgroundMarginTopAndBottom, elementWidth,
                availableHeight);
        }

        int x0 = x + elementWidth / 2;
        int yText = y + theme.backgroundMarginTopAndBottom;

        int usedHeight = element.getFont().getHeight();
        Image image = element.getImagePart();
        if (image != null) {
            usedHeight += image.getHeight();
            int spacerHeight = (availableHeight - usedHeight) / 3;
            int yImage = y + theme.backgroundMarginTopAndBottom + spacerHeight;
            g.drawImage(image, x0, yImage,
                Graphics.HCENTER | Graphics.TOP);
            yText += image.getHeight() + spacerHeight;
        }
        else {
            yText += (availableHeight - usedHeight) / 2;
        }

        g.setColor(focused ? theme.textColorFocused : theme.textColor);
        g.setFont(element.getFont());
        g.drawString(element.getTruncatedText(elementWidth),
            x0, yText, Graphics.HCENTER | Graphics.TOP);
    }
}
