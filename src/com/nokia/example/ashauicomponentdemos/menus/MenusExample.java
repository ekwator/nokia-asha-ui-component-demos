/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.menus;

import com.nokia.example.ashauicomponentdemos.utils.BackStack;
import com.nokia.example.ashauicomponentdemos.utils.Commands;
import com.nokia.example.ashauicomponentdemos.utils.InformationView;
import com.nokia.example.ashauicomponentdemos.utils.Strings;

import com.nokia.uihelpers.orientation.Orientation;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MenusExample
    extends MIDlet
    implements CommandListener {

    private static final int CONTEXT_MENU = 0;
    private static final int OPTIONS_MENU = 1;
    private static final int POPUP_LIST = 2;

    private static final String[] LIST_ITEMS = {
        "Context menu",
        "Options menu",
        "Popup list",
    };

    private List list;
    private BackStack backStack;
    private int listType; 
    
    /**
     * Start the app, create and display the initial list view
     */
    public void startApp() {
        list = new List(
                Strings.getTitle(Strings.MENUS),
                List.IMPLICIT,
                LIST_ITEMS,
                null);
        list.setSelectCommand(List.SELECT_COMMAND);
        list.addCommand(Commands.INFORMATION);
        list.addCommand(Commands.BACK);
        list.setCommandListener(this);
        Display.getDisplay(this).setCurrent(list);

        Orientation.enableOrientations();
        backStack = new BackStack(this);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean a)
        throws MIDletStateChangeException {
    }

    /**
     * Handle the commands made in this or the child views
     *
     * @param c
     * @param d
     */
    public void commandAction(Command c, Displayable d) {
        if (c == List.SELECT_COMMAND) {
            showListView(list.getSelectedIndex());
        }
        else if (c == Commands.BACK) {
            backStack.back();
        }
        else if (c == Commands.INFORMATION) {
            backStack.forward(new InformationView(Strings.MENUS, this));
        }
        else if (c == Commands.INFORMATION_BACK) {
            backStack.back();
        }
    }
    
    /**
     * Displays the requested view
     *
     * @param index
     */
    private void showListView(int index) {
        listType = index;
        switch (listType) {
            case CONTEXT_MENU:
                backStack.forward(new ContextMenuView(this, this));
                break;
            case OPTIONS_MENU:
                backStack.forward(new OptionsMenuView(this, this));
                break;
            case POPUP_LIST:
                backStack.forward(new PopupListView(this, this));
                break;
            default:
                break;
        }
    }    
    
}
