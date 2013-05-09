/**
 * Copyright (c) 2012-2013 Nokia Corporation. All rights reserved. Nokia and
 * Nokia Connecting People are registered trademarks of Nokia Corporation.
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be
 * trademarks or trade names of their respective owners. See LICENSE.TXT for
 * license information.
 */
package com.nokia.example.ashauicomponentdemos.lists;

import com.nokia.example.ashauicomponentdemos.utils.BackStack;
import com.nokia.example.ashauicomponentdemos.utils.Commands;
import com.nokia.mid.ui.LCDUIUtil;
import com.nokia.uihelpers.grid.GridItem;
import com.nokia.uihelpers.grid.GridLayout;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.midlet.MIDlet;

/**
 * This class displays a grid of selectable items.
 */
public class TemplateGridView
        extends Form
        implements CommandListener, ItemStateListener {

    // Constants
    private static final int GRID_LAYOUT_COLUMN_COUNT_IN_PORTRAIT =
            GridLayout.DEFAULT_COLUMN_COUNT;
    private static final int GRID_LAYOUT_COLUMN_COUNT_IN_LANDSCAPE =
            GRID_LAYOUT_COLUMN_COUNT_IN_PORTRAIT + 1;
    private static final int SCREEN_WIDTH_IN_PORTRAIT = 240;
    private static final int GRID_ITEM_COUNT = 15;
    private CommandListener parentCommandListener;
    private GridLayout gridLayout;
    private BackStack backStack;

    public TemplateGridView(MIDlet parent, CommandListener commandListener) {
        super("Grid");

        // Hide the open keyboard command in full touch devices
        final String keyboardType = System.getProperty("com.nokia.keyboard.type");

        if (keyboardType.equals("OnekeyBack") || keyboardType.equals("None")) {
            com.nokia.mid.ui.VirtualKeyboard.hideOpenKeypadCommand(true);
        }

        gridLayout = new GridLayout(getWidth());
        populateGrid();
        LCDUIUtil.setObjectTrait(gridLayout, "nokia.ui.s40.item.direct_touch", Boolean.TRUE);
        append(gridLayout);

        this.setItemStateListener(this);
        this.addCommand(Commands.BACK);
        this.setCommandListener(this);

        parentCommandListener = commandListener;
        backStack = new BackStack(parent);
    }

    /**
     * @see javax.microedition.CommandListener#commandAction(Command, Displayable)
     */
    public void commandAction(Command c, Displayable d) {
        if (c == Commands.BACK) {
            if (d instanceof Alert) {
                backStack.back();
            } else {
                parentCommandListener.commandAction(c, d);
            }
        } else if (c == Commands.ALERT_CONTINUE) {
            // First get out from the Alert
            backStack.back();
            // Then get out of this example back to the main List view
            parentCommandListener.commandAction(Commands.BACK, d);
        } else {
            parentCommandListener.commandAction(c, d);
        }
    }

    /**
     * @see javax.microedition.ItemStateListener#itemStateChanged(Item)
     */
    public void itemStateChanged(Item item) {
        showSelectedAlert(((GridLayout) item).getSelectedItem());
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

    /**
     * An utility function which adds some dummy items to the grid.
     */
    private void populateGrid() {
        // Populate grid with items
        for (int i = 0; i < GRID_ITEM_COUNT; i++) {
            String imageUri = "/image" + (i % 2 + 1) + ".png";
            GridItem gridItem = new GridItem(imageUri, "Image " + (i + 1), 50, 50);
            gridLayout.addItem(gridItem);
        }
    }

    /**
     * Shows the index of the selected item in an alert.
     *
     * @param gridItem the GridItem that was selected
     */
    private void showSelectedAlert(GridItem gridItem) {
        Alert selectedAlert = new Alert("Selected");
        selectedAlert.setString(
                "List item "
                + gridItem.getString()
                + " selected.");
        selectedAlert.addCommand(Commands.ALERT_CONTINUE);
        selectedAlert.addCommand(Commands.BACK);
        selectedAlert.setTimeout(Alert.FOREVER);
        selectedAlert.setCommandListener(this);
        backStack.forward(selectedAlert);
    }
}
