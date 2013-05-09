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
import com.nokia.example.ashauicomponentdemos.utils.ImageLoader;
import com.nokia.uihelpers.CustomList;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

/**
 * This class displays a list with customized appearance. The list selection
 * is persisted using record management store (RMS).
 */
public class FancyListView
    extends FancyCustomList
    implements CommandListener {
    
    private final int MAX_ITEMS = 10;
    private CommandListener parentCommandListener;
    private BackStack backStack;
    
    public FancyListView(MIDlet parent, CommandListener commandListener) {
        super("Fancy");
        this.setTheme(CustomList.createTheme(Display.getDisplay(parent)));
        
        int itemIndex = 1;
        for (int i = 0; i < MAX_ITEMS; i++) {
            if (i % 7 == 6) {
                this.appendSeparator();
            } else {
                this.append(
                    "Title " + (itemIndex),
                    "Content " + (itemIndex),
                    "12:34",
                    ImageLoader.loadThumbnail(i),
                    i % (FancyElement.IMPORTANCE_HIGH + 1)
                );
                itemIndex++;
            }
        }
        
        this.setFitPolicy(List.TEXT_WRAP_DEFAULT);
        this.setSelectCommand(Commands.LIST_SELECT);
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
        else if (c == Commands.LIST_SELECT) {
            int selected = getSelectedIndex();
            if (selected < 6) {
                selected++;
            }
            Alert selectedAlert = new Alert("Selected");
            selectedAlert.setString(
                    "List item " +
                    (selected) +
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
