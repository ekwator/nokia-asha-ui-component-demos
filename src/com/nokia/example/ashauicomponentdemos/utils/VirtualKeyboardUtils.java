/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.utils;

public class VirtualKeyboardUtils {

    /**
     * Wrapper for com.nokia.mid.ui.VirtualKeyboard.setVisibilityListener method.
     */
    public static void setVisibilityListener(
        final KeyboardVisibilityListener listener) {
        com.nokia.mid.ui.VirtualKeyboard.setVisibilityListener(new com.nokia.mid.ui.KeyboardVisibilityListener() {

            public void showNotify(int keyboardCategory) {
                listener.showNotify(keyboardCategory);
            }

            public void hideNotify(int keyboardCategory) {
                listener.hideNotify(keyboardCategory);
            }
        });
    }

    /**
     * KeyboardVisibilityListener interface for devices which don't support com.nokia.mid.ui.KeyboardVisibilityListener.
     */
    public interface KeyboardVisibilityListener {

        void showNotify(int keyboardCategory);

        void hideNotify(int keyboardCategory);
    }
}
