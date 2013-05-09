package com.nokia.uihelpers.grid;

/**
 * Copyright (c) 2013 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners. 
 * See LICENSE.TXT for license information.
 */

import com.nokia.example.ashauicomponentdemos.utils.ImageCache;

import java.io.IOException;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * A grid item displaying an image.
 */
public class GridItem extends CustomItem {
    // Constants
    private static final String HIGHLIGHT_OVERLAY_IMAGE_URI = "/highlight.png";

    // Members
    protected GridItem GridItem = null;
    protected String imageUri = null;
    protected String text = null;
    protected int width = 0;
    protected int height = 0;
    protected boolean highlight = false;

    /**
     * Constructor.
     * @param imageUri A URI of the grid item image.
     * @param text A descriptive text of the item.
     */
    public GridItem(String imageUri, String text,
                    final int width, final int height)
    {
        this();
        this.imageUri = imageUri;
        this.text = text;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructor.
     * @param GridItem The data item associated with this grid item.
     */
    public GridItem(GridItem GridItem, final int width, final int height) {
        this();
        this.GridItem = GridItem;
        
        if (this.GridItem != null) {
            imageUri = getImageUri();
            text = getLabel();
        }
        
        this.width = width;
        this.height = height;
    }

    /**
     * Private default constructor.
     */
    private GridItem() {
        super(null);
    }

    // Setters and getters

    public GridItem getGridItem() {
        return GridItem;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getString() {
        return text;
    }

    public void setString(String text) {
        this.text = text;
    }

    public boolean getHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public void setSize(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @see javax.microedition.lcdui.CustomItem#paint(Graphics, int, int)
     */
    public void paint(Graphics graphics, int width, int height) {
        paint(graphics, 0, 0, width, height);
    }

    /**
     * For convenience. Paints this item in the given position.
     * @param graphics The Graphics instance.
     * @param x The X coordinate of the item.
     * @param y The Y coordinate of the item.
     */
    public void paintXY(Graphics graphics, int x, int y) {
        paint(graphics, x, y, width, height);
    }

    /**
     * For convenience.
     */
    protected void paint(Graphics graphics, int x, int y, int width, int height) {
        try {
            Image image = ImageCache.getImage(imageUri, width, height);
            graphics.drawImage(image, x, y, Graphics.TOP | Graphics.LEFT);
            
            if (highlight) {
                image = ImageCache.getImage(HIGHLIGHT_OVERLAY_IMAGE_URI, width, height);
                graphics.drawImage(image, x, y, Graphics.TOP | Graphics.LEFT);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected int getMinContentHeight() {
        return height;
    }

    protected int getMinContentWidth() {
        return width;
    }

    protected int getPrefContentHeight(int arg0) {
        return height;
    }

    protected int getPrefContentWidth(int arg0) {
        return width;
    }
}
