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

/**
 * This example demonstrates the usage of the List class
 */
public class ListsExample
    extends MIDlet
    implements CommandListener {

    private static final int IMPLICIT_LIST = 0;
    private static final int EXCLUSIVE_CONFIRM_LIST = 1;
    private static final int MULTIPLE_LIST = 2;
    private static final int TRUNCATED_LIST = 3;
    private static final int THUMBNAILS_LIST = 4;
    private static final int FANCY_LIST = 5;
    private static final int GRID_LIST = 6;

    private static final String[] LIST_ITEMS = {
        "Implicit",
        "Exclusive + confirm",
        "Multiple",
        "Truncated",
        "Thumbnails",
        "Fancy list",
        "Grid list"
    };

    private List list;
    private BackStack backStack;
    private int listType;
    
    /**
     * Start the app, create and display the initial list view
     */
    public void startApp() {
        list =
            new List(Strings.getTitle(Strings.LISTS), List.IMPLICIT,
            LIST_ITEMS, null);
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
     * Displays the requested view
     *
     * @param index
     */
    private void showListView(int index) {
        listType = index;
        switch (listType) {
            case IMPLICIT_LIST:
                backStack.forward(new ImplicitListView(this, this));
                break;
            case EXCLUSIVE_CONFIRM_LIST:
                backStack.forward(new ExclusiveConfirmListView(this, this));
                break;
            case MULTIPLE_LIST:
                backStack.forward(new MultipleListView(this, this));
                break;
            case TRUNCATED_LIST:
                backStack.forward(new TruncatedListView(this, this));
                break;
            case THUMBNAILS_LIST:
                backStack.forward(new ThumbnailListView(this, this));
                break;
            case FANCY_LIST:
                backStack.forward(new FancyListView(this, this));
                break;
            case GRID_LIST:
                backStack.forward(new TemplateGridView(this, this));
                break;
        }
    }

    /**
     * Handle the commands made in this or the child views
     *
     * @param c
     * @param disp
     */
    public void commandAction(Command c, Displayable d) {
        if (c == List.SELECT_COMMAND) {
            showListView(list.getSelectedIndex());
        }
        else if (c == Commands.BACK) {
            backStack.back();
        }
        else if (c == Commands.INFORMATION) {
            backStack.forward(new InformationView(Strings.LISTS, this));
        }
        else if (c == Commands.INFORMATION_BACK) {
            backStack.back();
        }
    }
}
