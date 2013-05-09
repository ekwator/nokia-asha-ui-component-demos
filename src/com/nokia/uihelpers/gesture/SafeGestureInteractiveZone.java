/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/

package com.nokia.uihelpers.gesture;

/**
 * This class mimics the GestureInteractiveZone class of Nokia UI API. It is
 * used for providing backwards compatibility for devices that don't have the 
 * Nokia Gesture API.
 */
public class SafeGestureInteractiveZone {
    public static final int GESTURE_ALL = 49279;
    public static final int GESTURE_DRAG = 4;
    public static final int GESTURE_DROP = 8;
    public static final int GESTURE_FLICK = 16;
    public static final int GESTURE_LONG_PRESS = 2;
    public static final int GESTURE_LONG_PRESS_REPEATED = 32;
    public static final int GESTURE_PINCH = 64;
    public static final int GESTURE_RECOGNITION_END = 32768;
    public static final int GESTURE_RECOGNITION_START = 16384;
    public static final int GESTURE_TAP = 1;

    private int gestures;
    private int x;
    private int y;
    private int width;
    private int height;
    private Object nativeZone;
    
    public SafeGestureInteractiveZone() {
        
    }
    
    public SafeGestureInteractiveZone(int gestures) {
        this.gestures = gestures;
    }
    
    public void setGesture(int gesture) {
        this.gestures = gesture;
    }
    
    public int getGesture() {
        return gestures;
    }
    
    public void setRectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }

    public Object getNativeZone() {
        return nativeZone;
    }

    public void setNativeZone(Object nativeZone) {
        this.nativeZone = nativeZone;
    }
}
