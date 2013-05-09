/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.categorybar;

import com.nokia.example.ashauicomponentdemos.utils.BackStack;
import com.nokia.example.ashauicomponentdemos.utils.CategoryBarUtils;
import com.nokia.example.ashauicomponentdemos.utils.Commands;
import com.nokia.example.ashauicomponentdemos.utils.InformationView;
import com.nokia.example.ashauicomponentdemos.utils.Strings;
import com.nokia.mid.ui.CategoryBar;
import com.nokia.mid.ui.ElementListener;
import com.nokia.uihelpers.orientation.Orientation;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.StringItem;
import javax.microedition.midlet.MIDlet;

/**
 * An example demonstrating the CategoryBar element of Nokia UI API.
 */
public class CategoryBarExample
    extends MIDlet
    implements CommandListener, CategoryBarUtils.ElementListener {

    private final int FOUR_CATEGORIES = 0;
    private final int THREE_CATEGORIES = 1;
    private final int TWO_CATEGORIES = 2;
    private final int FOUR_ACTIONS = 3;
    private final int THREE_ACTIONS = 4;
    private final int TWO_ACTIONS = 5;
    private final int ONE_ACTION = 6;
    private final String[] LIST_ITEMS = {
        "4 tabs",
        "3 tabs",
        "2 tabs",
        "4 actions",
        "3 actions",
        "2 actions",
        "1 action"
    };
    private List viewList;
    private CategoryBar categoryBar;
    private CategoryBarView categoryBarView;
    private BackStack backStack;

    /**
     * Start the app, create and show the initial List view,
     * setup listeners and enable orientation support
     */
    public void startApp() {
        viewList = new List(Strings.getTitle(Strings.CATEGORYBAR),
            List.IMPLICIT, LIST_ITEMS, null);
        viewList.setCommandListener(this);
        viewList.setSelectCommand(List.SELECT_COMMAND);
        viewList.addCommand(Commands.EXIT);
        viewList.addCommand(Commands.INFORMATION);

        Orientation.enableOrientations();
        backStack = new BackStack(this);
        Display.getDisplay(this).setCurrent(viewList);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == List.SELECT_COMMAND) {
            // Initialize and display the requested demo view
            displayView(viewList.getSelectedIndex());
        }
        else if (c == Commands.BACK || c == Commands.EXIT
                || c == Commands.INFORMATION_BACK) {
            backStack.back();
        }
        else if (c == Commands.INFORMATION) {
            backStack.forward(new InformationView(Strings.CATEGORYBAR,
                this));
        }
    }

    /**
     * Handles CategoryBar events, tells the currently visible CategoryBarView
     * to switch view to whatever item is tapped
     * @param categoryBar
     * @param selectedIndex 
     */
    public void notifyElementSelected(CategoryBar categoryBar, int selectedIndex) {
        switch (selectedIndex) {
            case ElementListener.BACK:
                categoryBar.setVisibility(false);
                backStack.back();
                break;
            default:
                categoryBarView.setActive(selectedIndex);
                if (categoryBar.getMode() == CategoryBar.ELEMENT_MODE_RELEASE_SELECTED) {
                    String triggered = "\n\n" + CategoryBarView.VIEW_NAMES[selectedIndex] + " was triggered";
                    StringItem item = new StringItem("", triggered);
                    categoryBarView.append(item);
                }
                break;
        }
    }

    private void displayView(int index) {
        switch (index) {
            case FOUR_CATEGORIES:
                createCategoryView(4, CategoryBar.ELEMENT_MODE_STAY_SELECTED);
                break;
            case THREE_CATEGORIES:
                createCategoryView(3, CategoryBar.ELEMENT_MODE_STAY_SELECTED);
                break;
            case TWO_CATEGORIES:
                createCategoryView(2, CategoryBar.ELEMENT_MODE_STAY_SELECTED);
                break;
            case FOUR_ACTIONS:
                createCategoryView(4, CategoryBar.ELEMENT_MODE_RELEASE_SELECTED);
                break;
            case THREE_ACTIONS:
                createCategoryView(3, CategoryBar.ELEMENT_MODE_RELEASE_SELECTED);
                break;
            case TWO_ACTIONS:
                createCategoryView(2, CategoryBar.ELEMENT_MODE_RELEASE_SELECTED);
                break;
            case ONE_ACTION:
                createCategoryView(1, CategoryBar.ELEMENT_MODE_RELEASE_SELECTED);
            default:
                break;
        }
    }

    /**
     * Generates and displays the CategoryBarView with the requested
     * amount of items
     * @param amountOfCategories 
     */
    private void createCategoryView(int amountOfCategories, int mode) {
        categoryBarView = new CategoryBarView(amountOfCategories, mode);
        categoryBarView.setCommandListener(this);
        categoryBarView.setActive(0);

        categoryBar = categoryBarView.createCategoryBar(mode);
        CategoryBarUtils.setListener(categoryBar, this);
        categoryBar.setVisibility(true);

        Display d = Display.getDisplay(this);
        categoryBar.setHighlightColour(d.getColor(
            Display.COLOR_HIGHLIGHTED_BACKGROUND));

        // Navigate to the created view
        backStack.forward(categoryBarView);
    }
}
