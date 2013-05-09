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
 * This class mimics the GestureEvent class of Nokia UI API. It is used for
 * providing backwards compatibility for devices that don't have the Nokia
 * Gesture API.
 */
public class SafeGestureEvent {
    private int dragDistanceX = 0;
    private int dragDistanceY = 0;
    private float flickDirection = 0;
    private int flickSpeed = 0;
    private int flickSpeedX = 0;
    private int flickSpeedY = 0;
    private int pinchCenterChangeX = 0;
    private int pinchCenterChangeY = 0;
    private int pinchCenterX = 0;
    private int pinchCenterY = 0;
    private int pinchDistanceChange = 0;
    private int pinchDistanceCurrent = 0;
    private int pinchDistanceStarting = 0;
    private int startX = 0;
    private int startY = 0;
    private int type = 0;
    
    public int getDragDistanceX() {
        return dragDistanceX;
    }

    public int getDragDistanceY() {
        return dragDistanceY;
    }

    public float getFlickDirection() {
        return flickDirection;
    }

    public int getFlickSpeed() {
        return flickSpeed;
    }

    public int getFlickSpeedX() {
        return flickSpeedX;
    }

    public int getFlickSpeedY() {
        return flickSpeedY;
    }

    public int getPinchCenterChangeX() {
        return pinchCenterChangeX;
    }

    public int getPinchCenterChangeY() {
        return pinchCenterChangeY;
    }

    public int getPinchCenterX() {
        return pinchCenterX;
    }

    public int getPinchCenterY() {
        return pinchCenterY;
    }

    public int getPinchDistanceChange() {
        return pinchDistanceChange;
    }

    public int getPinchDistanceCurrent() {
        return pinchDistanceCurrent;
    }

    public int getPinchDistanceStarting() {
        return pinchDistanceStarting;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getType() {
        return type;
    }

    public void setDragDistanceX(int dragDistanceX) {
        this.dragDistanceX = dragDistanceX;
    }

    public void setDragDistanceY(int dragDistanceY) {
        this.dragDistanceY = dragDistanceY;
    }

    public void setFlickDirection(float flickDirection) {
        this.flickDirection = flickDirection;
    }

    public void setFlickSpeed(int flickSpeed) {
        this.flickSpeed = flickSpeed;
    }

    public void setFlickSpeedX(int flickSpeedX) {
        this.flickSpeedX = flickSpeedX;
    }

    public void setFlickSpeedY(int flickSpeedY) {
        this.flickSpeedY = flickSpeedY;
    }

    public void setPinchCenterChangeX(int pinchCenterChangeX) {
        this.pinchCenterChangeX = pinchCenterChangeX;
    }

    public void setPinchCenterChangeY(int pinchCenterChangeY) {
        this.pinchCenterChangeY = pinchCenterChangeY;
    }

    public void setPinchCenterX(int pinchCenterX) {
        this.pinchCenterX = pinchCenterX;
    }

    public void setPinchCenterY(int pinchCenterY) {
        this.pinchCenterY = pinchCenterY;
    }

    public void setPinchDistanceChange(int pinchDistanceChange) {
        this.pinchDistanceChange = pinchDistanceChange;
    }

    public void setPinchDistanceCurrent(int pinchDistanceCurrent) {
        this.pinchDistanceCurrent = pinchDistanceCurrent;
    }

    public void setPinchDistanceStarting(int pinchDistanceStarting) {
        this.pinchDistanceStarting = pinchDistanceStarting;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setType(int type) {
        this.type = type;
    }
}
