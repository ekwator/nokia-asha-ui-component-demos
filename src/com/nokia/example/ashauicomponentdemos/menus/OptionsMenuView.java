package com.nokia.example.ashauicomponentdemos.menus;

import com.nokia.example.ashauicomponentdemos.utils.Commands;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;
import javax.microedition.midlet.MIDlet;

public class OptionsMenuView
        extends Form {
    
    public OptionsMenuView(MIDlet parent, CommandListener commandListener) {
        super("Options menu");
        
        StringItem item = new StringItem("", "The highest priority command appears as button, while the lower priority commands are available in the menu.", StringItem.PLAIN);
        append (item);
        
        // The highest priority command is mapped to a button
        Command buttonCommand = new Command("Highest prio. cmd", Command.SCREEN, 0);
        addCommand(buttonCommand);
        
        // The rest appear in menu
        Command menuCommand1 = new Command("Command 2", Command.SCREEN, 1);
        addCommand(menuCommand1);
        Command menuCommand2 = new Command("Command 3", Command.SCREEN, 2);
        addCommand(menuCommand2);
        Command menuCommand3 = new Command("Command 4", Command.SCREEN, 3);
        addCommand(menuCommand3);
        
        addCommand(Commands.BACK);

        setCommandListener(commandListener);
    }
}
