/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.uihelpers.orientation;

class OrientationImpl
    extends Orientation
    implements com.nokia.mid.ui.orientation.OrientationListener {

    OrientationImpl() {
        // Listen for orientation events
        com.nokia.mid.ui.orientation.Orientation.addOrientationListener(this);
    }
    
    /**
     * Changes display orientation between landscape and portrait.
     * Note: ORIENTATION_LANDSCAPE_180 and ORIENTATION_PORTRAIT_180 are not
     * supported.
     * 
     * @param newDisplayOrientation Tells the new device orientation.
     * @see com.nokia.mid.ui.orientation.OrientationListener#displayOrientationChanged(int)
     */
    public void displayOrientationChanged(int newDisplayOrientation) {
        switch (newDisplayOrientation) {
            case com.nokia.mid.ui.orientation.Orientation.ORIENTATION_LANDSCAPE:
                com.nokia.mid.ui.orientation.Orientation.setAppOrientation(newDisplayOrientation);
                break;
            case com.nokia.mid.ui.orientation.Orientation.ORIENTATION_PORTRAIT:
                com.nokia.mid.ui.orientation.Orientation.setAppOrientation(newDisplayOrientation);
                break;
            default:
                break;     
        }
    }
}
