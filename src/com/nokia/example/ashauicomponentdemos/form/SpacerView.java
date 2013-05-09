/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.form;

import com.nokia.example.ashauicomponentdemos.utils.Commands;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Spacer;
import javax.microedition.lcdui.StringItem;

/**
 * Demonstrates the usage of the Spacer class
 */
public class SpacerView
    extends FormExampleView {

    private final int SPACER_HEIGHT = 90;

    public SpacerView(CommandListener commandListener, String title) {
        super(title, commandListener);
        removeCommand(Commands.OK);

        // When a command is attached to BUTTON / HYPERLINK 
        // StringItem, the appearance of the StringItem becomes
        // a button or a hyperlink
        Command selectCommand = new Command("Select", Command.ITEM, 1);

        // Create a Button StringItem
        StringItem stringItem = new StringItem(
            "Spacer (" + SPACER_HEIGHT +  "px) between buttons",
            "ACTION", StringItem.BUTTON);
        stringItem.addCommand(selectCommand);
        this.append(stringItem);

        // Create a spacer with the width of the screen, height of 90 pixels
        Spacer spacer = new Spacer(this.getWidth(), SPACER_HEIGHT);
        this.append(spacer);

        // Create another Button StringItem
        stringItem = new StringItem("", "ACTION", StringItem.BUTTON);
        stringItem.addCommand(selectCommand);
        this.append(stringItem);
    }

    protected void storeCurrentValues() {
        // No need to implement, no changeable values
    }

    protected void cancelChanges() {
        // No need to implement, no changeable values
    }
}
