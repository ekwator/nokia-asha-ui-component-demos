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
 * This class wraps the GestureRegistrationManager to provide backwards
 * compatibility for targets that don't have the Nokia Gesture API.
 */
public class SafeGestureRegistrationManager {
    
    public static void setListener(Object container, SafeGestureListener listener) {
        try {
            Class.forName("com.nokia.mid.ui.gestures.GestureRegistrationManager"); //Try to produce the exception
            SafeGestureListenerImpl listenerImpl = new SafeGestureListenerImpl(listener);
            com.nokia.mid.ui.gestures.GestureRegistrationManager.setListener(container, listenerImpl);
        } catch (Exception e) {
        }
    }
    
    public static boolean register(Object container, SafeGestureInteractiveZone zone) {
        try {
            Class.forName("com.nokia.mid.ui.gestures.GestureRegistrationManager"); //Try to produce the exception
            com.nokia.mid.ui.gestures.GestureInteractiveZone gzone = new
                com.nokia.mid.ui.gestures.GestureInteractiveZone(zone.getGesture());
            gzone.setRectangle(zone.getX(), zone.getY(), zone.getWidth(), zone.getHeight());
            zone.setNativeZone(gzone);
            return com.nokia.mid.ui.gestures.GestureRegistrationManager.register(container, gzone);
        } catch (Exception e) {
            return false;
        }       
    }
    
    public static void unregister(Object container, SafeGestureInteractiveZone zone) {
        try {
            Class.forName("com.nokia.mid.ui.gestures.GestureRegistrationManager"); //Try to produce the exception
            com.nokia.mid.ui.gestures.GestureInteractiveZone gzone = 
                (com.nokia.mid.ui.gestures.GestureInteractiveZone) zone.getNativeZone();
            com.nokia.mid.ui.gestures.GestureRegistrationManager.unregister(container, gzone);
        } catch (Exception e) {
        }
    }
}
