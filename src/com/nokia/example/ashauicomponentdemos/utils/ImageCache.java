package com.nokia.example.ashauicomponentdemos.utils;

/**
 * Copyright (c) 2013 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners. 
 * See LICENSE.TXT for license information.
 */

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Hashtable;
import javax.microedition.lcdui.Image;

/**
 * Helper class for loading images and scaling them to requested size.
 * The class caches the scaled images for performance.
 */
public class ImageCache {
    private static Hashtable cache;

    static {
        cache = new Hashtable();
    }

    /**
     * Returns a scaled image from a given location. The scaled image is cached
     * for later use.
     * @param uri The location of the image file.
     * @param width The width of the scaled image.
     * @param height The height of the scaled image.
     * @return The scaled image.
     */
    public static Image getImage(String uri, int width, int height)
            throws IOException
    {
        // Generate a unique key for the image of requested size
        String key = uri + width + "x" + height;
        
        // Fetch a scaled image from cache
        WeakReference ref = (WeakReference) cache.get(key);
        
        if (ref != null) {
            // Check if the WeakReference is still valid
            Object o = ref.get();
            
            if (o != null) {
                return (Image) o;
            }
        }
        
        // Image wasn't in cache or the WeakReference was invalid. Create a new
        // scaled Image and store it into cache.
        Image image = scaleImage(Image.createImage(uri), width, height);
        ref = new WeakReference(image);
        cache.put(key, ref);
        
        return image;
    }

    /**
     * Scales the given image.
     * @param sourceImage The image to scale.
     * @param newWidth The target width of the scaled image.
     * @param newHeight The target height of the scaled image.
     * @return The scaled image.
     */
    private static Image scaleImage(Image sourceImage,
                                    int newWidth,
                                    int newHeight)
    {
        // Get the width and height of the original image
        int sourceWidth = sourceImage.getWidth();
        int sourceHeight = sourceImage.getHeight();
        
        // An array for RGB, with the size of original image)
        int rgbSource[] = new int[sourceWidth * sourceHeight];
        
        // An array for RGB, with the size of scaled image)
        int rgb2Scaled[] = new int[newWidth * newHeight];
        
        // Get the RGB array of source image
        sourceImage.getRGB(rgbSource, 0, sourceWidth, 0, 0, sourceWidth, sourceHeight);
        
        // Calculations and bit shift operations to optimize the for loop
        int tempScaleRatioWidth = ((sourceWidth << 16) / newWidth);
        int tempScaleRatioHeight = ((sourceHeight << 16) / newHeight);
        
        int i = 0;
        
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                rgb2Scaled[i++] = rgbSource[(sourceWidth
                        * ((y * tempScaleRatioHeight) >> 16))
                        + ((x * tempScaleRatioWidth) >> 16)];
            }
        }
        
        // Create an RGB image from rgbScaled array
        return Image.createRGBImage(rgb2Scaled, newWidth, newHeight, true);
    }
}
