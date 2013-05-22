/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/

package com.nokia.example.ashauicomponentdemos.lists;

import com.nokia.example.ashauicomponentdemos.utils.BackStack;
import com.nokia.example.ashauicomponentdemos.utils.Commands;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

/**
 * This class displays a list with truncated text on list items.
 */
public class TruncatedListView
    extends List
    implements CommandListener {
    
    private static final String[] LONG_ITEMS = new String[10];
    
    static {
        for (int i = 0; i < LONG_ITEMS.length; i++) {
            LONG_ITEMS[i] = new String("List item " + i + ", Lorem ipsum "
                    + "dolor sit amet, consectetur adipiscing elit. Phasellus vel neque pulvinar libero vestibulum malesua.");
        }
    }
    
    private CommandListener parentCommandListener;
    private BackStack backStack;
    
    public TruncatedListView(MIDlet parent, CommandListener commandListener) {
        super("Truncated",
              List.IMPLICIT,
              LONG_ITEMS,
              null);
        this.setFitPolicy(List.TEXT_WRAP_OFF);
        this.setSelectCommand(List.SELECT_COMMAND);
        this.addCommand(Commands.BACK);
        this.setCommandListener(this);
        parentCommandListener = commandListener;
        backStack = new BackStack(parent);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == Commands.BACK) {
            if (d instanceof Alert) {
                backStack.back();
            }
            else {
                parentCommandListener.commandAction(c, d);
            }
        }
        else if (c == List.SELECT_COMMAND) {
            Alert selectedAlert = new Alert("Selected");
            selectedAlert.setString(
                    "List item " +
                    (this.getSelectedIndex()) +
                    " selected.");
            selectedAlert.addCommand(Commands.ALERT_CONTINUE);
            selectedAlert.addCommand(Commands.BACK);
            selectedAlert.setTimeout(Alert.FOREVER);
            selectedAlert.setCommandListener(this);
            backStack.forward(selectedAlert);
        }
        else if (c == Commands.ALERT_CONTINUE) {
            // First get out from the Alert
            backStack.back();
            // Then get out of this example back to the main List view
            parentCommandListener.commandAction(Commands.BACK, d);
        }
        else {
            parentCommandListener.commandAction(c, d);
        }
    }
}
