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

import com.nokia.mid.ui.LCDUIUtil;
import com.nokia.mid.ui.PopupList;
import com.nokia.mid.ui.PopupListItem;
import com.nokia.mid.ui.PopupListListener;

import com.nokia.uihelpers.grid.GridItem;
import com.nokia.uihelpers.grid.GridLayout;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;

/**
 * Demonstrates PopupList component
 */
public class PopupListView
        extends Form
        implements CommandListener {
    
    private GridLayout gridLayout;
    private Command addItemCommand;
    private CommandListener masterCommandListener;
    private int itemCounter;

    // Constants    
    private static final int GRID_LAYOUT_COLUMN_COUNT_IN_PORTRAIT =
            GridLayout.DEFAULT_COLUMN_COUNT;
    private static final int GRID_LAYOUT_COLUMN_COUNT_IN_LANDSCAPE =
            GRID_LAYOUT_COLUMN_COUNT_IN_PORTRAIT + 1;
    private static final int SCREEN_WIDTH_IN_PORTRAIT = 240;
    private static final int GRID_ITEM_COUNT = 15;
    
    public PopupListView(MIDlet parent, CommandListener commandListener) {
        super("Popup list");
        
        // Hide the open keyboard command in full touch devices
        final String keyboardType = System.getProperty(
                "com.nokia.keyboard.type");

        if (keyboardType.equals("OnekeyBack") || keyboardType.equals("None")) {
            com.nokia.mid.ui.VirtualKeyboard.hideOpenKeypadCommand(true);
        }

        gridLayout = new GridLayoutWithContextMenu(getWidth());
        LCDUIUtil.setObjectTrait(
                gridLayout,
                "nokia.ui.s40.item.direct_touch",
                Boolean.TRUE);
        append(gridLayout);
        
        masterCommandListener = commandListener;
        
        addItemCommand = new Command("Add item", Command.SCREEN, 0);
        addCommand(addItemCommand);

        addCommand(Commands.BACK);
        setCommandListener(this);
        
        // Populate grid with items
        for (int i = 0; i < GRID_ITEM_COUNT; i++) {
            addNewItem();
        }
    }  
    
    /**
     * Adjusts the grid size according to the display size.
     *
     * @see javax.microedition.lcdui.Displayable#sizeChanged(int, int)
     */
    public void sizeChanged(int w, int h) {
        int columnCount = GRID_LAYOUT_COLUMN_COUNT_IN_PORTRAIT;

        if (w > SCREEN_WIDTH_IN_PORTRAIT) {
            columnCount = GRID_LAYOUT_COLUMN_COUNT_IN_LANDSCAPE;
        }

        gridLayout.setColumnCountAndWidth(columnCount, w);
    } 

    public void commandAction(Command c, Displayable d) {
        if (c == addItemCommand) {
            addNewItem();
        } else {
            // Delegate global commands for masterCommandListener
            masterCommandListener.commandAction(c, d);
        }
    }
    
    /**
     * Adds a new item to the grid.
     */    
    private void addNewItem() {
        itemCounter++;
        String imageUri = "/image" + (itemCounter % 2 + 1) + ".png";
        GridItem gridItem = new GridItem(
                imageUri,
                "Item " + itemCounter,
                50,
                50);
        gridLayout.addItem(gridItem);
    }

    /**
     * GridLayout component that displays a context menu on long-press gesture.
     */    
    private class GridLayoutWithContextMenu extends GridLayout {

        private Timer timer;
        private int dragCounter;

        public GridLayoutWithContextMenu(int width) {
            super(width);            
        }
        
        /**
         * Displays a context menu for the selected grid item.
         */ 
        public void showContextMenu() {
            PopupList popupList = new PopupList();
            PopupListItem deleteItem = new PopupListItem(
                    "Remove " + getSelectedItem().getString());
            popupList.setListener(new PopupListListener() {
                
                public void itemSelected(PopupList list, PopupListItem item) {
                    removeItem(getSelectedItem());
                }

                public void listDismissed(PopupList list) {
                    getSelectedItem().setHighlight(false);
                    repaint();
                }
            
            });                    
            popupList.appendItem(deleteItem);
            popupList.setVisible(true);
        }

        public void pointerPressed(final int x, final int y) {
            super.pointerPressed(x, y);
            dragCounter = 0;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    showContextMenu();
                }
            }, 300);
            repaint();
        }

        public void pointerReleased(int x, int y) {
            super.pointerReleased(x, y);
            if (timer != null) {
                timer.cancel();
            }
            repaint();
        }

        public void pointerDragged(int x, int y) {
            super.pointerDragged(x, y);
            dragCounter++;
            // Don't show context menu if user has dragged
            if (dragCounter > 3) {
                if (timer != null) {
                    timer.cancel();
                }
                repaint();
            }
        }
    }

}

