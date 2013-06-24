/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.menus;

import com.nokia.example.ashauicomponentdemos.utils.Commands;
import com.nokia.uihelpers.CustomListItem;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.midlet.MIDlet;

/**
 * Demonstrates context menu on Form Items
 */
public class ContextMenuView
        extends Form
        implements ItemCommandListener, CommandListener {

    private Command addItemCommand;    
    private CommandListener parentCommandListener;
    private int itemCounter;
    
    public ContextMenuView(MIDlet parent, CommandListener commandListener) {
        super("Context menu");
        
        // Populate view with items
        for (int i = 0; i < 10; i++) {
            addNewItem();
        }
        
        addItemCommand = new Command("Add item", Command.SCREEN, 0);
        addCommand(addItemCommand);
        
        addCommand(Commands.BACK);
        parentCommandListener = commandListener;
        setCommandListener(this);
    }

    public void commandAction(Command c, Item item) {
        for (int i = 0; i < size(); i++) {
            if (get(i) == item) {
                delete(i);
                break;
            }
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == addItemCommand) {
            addNewItem();
        }
        else {
            // Delegate global commands for parentCommandListener
            parentCommandListener.commandAction(c, d);
        }
    }
    
    
    /**
     * Adds a new item to the Form.
     */      
    private void addNewItem() {
        itemCounter++;
        CustomListItem cli = new CustomListItem("Item " + itemCounter, null);
        Command removeItemCommand = new Command("Remove " + cli.getText(),
                Command.ITEM,
                0);
        cli.addCommand(removeItemCommand);
        cli.setItemCommandListener(this);
        append(cli);
    }    

}
