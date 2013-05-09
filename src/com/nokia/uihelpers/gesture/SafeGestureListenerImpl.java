/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/

package com.nokia.uihelpers.gesture;

public class SafeGestureListenerImpl 
    implements com.nokia.mid.ui.gestures.GestureListener {
    private SafeGestureListener listener;
    
    public SafeGestureListenerImpl(SafeGestureListener listener) {
        this.listener = listener;
    }
    
    public void gestureAction(Object container,
            com.nokia.mid.ui.gestures.GestureInteractiveZone gzone,
            com.nokia.mid.ui.gestures.GestureEvent gevent) {
        
        SafeGestureInteractiveZone zone = new SafeGestureInteractiveZone();
        zone.setGesture(gzone.getGestures());
        zone.setRectangle(gzone.getX(), gzone.getY(), gzone.getWidth(), gzone.getHeight());
        
        SafeGestureEvent event = new SafeGestureEvent();
        event.setType(gevent.getType());
        event.setStartX(gevent.getStartX());
        event.setStartY(gevent.getStartY());
        
        switch (gevent.getType()) {
            case com.nokia.mid.ui.gestures.GestureInteractiveZone.GESTURE_PINCH:
                event.setPinchCenterChangeX(gevent.getPinchCenterChangeX());
                event.setPinchCenterChangeY(gevent.getPinchCenterChangeY());
                event.setPinchCenterX(gevent.getPinchCenterX());
                event.setPinchCenterY(gevent.getPinchCenterY());
                event.setPinchDistanceChange(gevent.getPinchDistanceChange());
                event.setPinchDistanceCurrent(gevent.getPinchDistanceCurrent());
                event.setPinchDistanceStarting(gevent.getPinchDistanceStarting());
                break;
            case com.nokia.mid.ui.gestures.GestureInteractiveZone.GESTURE_DRAG:
                event.setDragDistanceX(gevent.getDragDistanceX());
                event.setDragDistanceY(gevent.getDragDistanceY());
                break;
            case com.nokia.mid.ui.gestures.GestureInteractiveZone.GESTURE_FLICK:
                event.setFlickDirection(gevent.getFlickDirection());
                event.setFlickSpeed(gevent.getFlickSpeed());
                event.setFlickSpeedX(gevent.getFlickSpeedX());
                event.setFlickSpeedY(gevent.getFlickSpeedY());
                break;
            case com.nokia.mid.ui.gestures.GestureInteractiveZone.GESTURE_DROP:
            case com.nokia.mid.ui.gestures.GestureInteractiveZone.GESTURE_LONG_PRESS:
            case com.nokia.mid.ui.gestures.GestureInteractiveZone.GESTURE_TAP:
            default:
                break;
        }
        
        listener.gestureAction(container, zone, event);
    }
}
