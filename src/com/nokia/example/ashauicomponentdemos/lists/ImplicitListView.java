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
 * This class displays a list for making implicit selections.
 */
public class ImplicitListView
    extends List
    implements CommandListener {

    private CommandListener parentCommandListener;
    private BackStack backStack;
    
    public ImplicitListView(MIDlet parent, CommandListener commandListener) {
        super("Implicit list",
              List.IMPLICIT);
        this.setCommandListener(this);
        this.parentCommandListener = commandListener;
        this.addCommand(Commands.BACK);
        this.setSelectCommand(List.SELECT_COMMAND);
        for (int i = 0; i < 10; i++) {
            this.append("list item " +
                    String.valueOf(i+1), null);
        }
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
                    (this.getSelectedIndex() + 1) +
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
