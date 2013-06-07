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
    
    public void addNewItem() {
        itemCounter++;
        CustomListItem cli = new CustomListItem("Item " + itemCounter, null);
        Command removeItemCommand = new Command("Remove " + cli.getText(),
                Command.ITEM,
                0);
        cli.addCommand(removeItemCommand);
        cli.setItemCommandListener(this);
        append(cli);
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
        } else {
            // Delegate global commands for masterCommandListener
            parentCommandListener.commandAction(c, d);
        }
    }

}
