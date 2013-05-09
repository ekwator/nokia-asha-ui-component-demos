/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.utils;

import java.util.Stack;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

/**
 * A helper class to ease the navigation between views. Stores the previous
 * navigation steps in a Stack, has a reference to the running MIDlet and
 * manages the displaying of the views. Also quits the application when 
 * navigating back but the stack is empty.
 */
public class BackStack {

    private Stack backStack;
    private MIDlet midlet;

    public BackStack(MIDlet c) {
        midlet = c;
        backStack = new Stack();
    }

    /**
     * Navigate forward to newView
     * @param newView 
     */
    public void forward(Displayable newView) {
        Display display = Display.getDisplay(midlet);
        Displayable view = display.getCurrent();
        if (view instanceof Alert && newView instanceof Alert) {
            // both are Alerts replace the current one with the new one.
            Displayable previousView = backStack.isEmpty() ? null
                : (Displayable) backStack.peek();
            display.setCurrent((Alert) newView, previousView);
        }
        else {
            backStack.push(view);
            display.setCurrent(newView);
        }
    }

    /**
     * Navigate back one view. Quit if on the last view.
     */
    public void back() {
        back(1);
    }

    /**
     * Backs up multiple levels. Useful with Alerts.
     * @param levels 
     */
    public void back(int levels) {
        Displayable view = null;
        for (int i = 0; i < levels; i++) {
            if (!backStack.isEmpty()) {
                view = (Displayable) backStack.pop();
            }
            else {
                view = null;
            }
        }
        if (view != null) {
            Display.getDisplay(midlet).setCurrent(view);
        }
        else {
            midlet.notifyDestroyed();
        }
    }
}
