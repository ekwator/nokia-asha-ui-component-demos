/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.canvas;

import com.nokia.example.ashauicomponentdemos.utils.BackStack;
import com.nokia.example.ashauicomponentdemos.utils.CategoryBarUtils;
import com.nokia.example.ashauicomponentdemos.utils.Commands;
import com.nokia.example.ashauicomponentdemos.utils.ImageLoader;
import com.nokia.example.ashauicomponentdemos.utils.InformationView;
import com.nokia.example.ashauicomponentdemos.utils.Strings;
import com.nokia.mid.ui.CategoryBar;
import com.nokia.mid.ui.ElementListener;
import com.nokia.mid.ui.IconCommand;
import com.nokia.uihelpers.orientation.Orientation;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

/**
 * An example demonstrating LCDUI Canvas.
 */
public class CanvasExample
    extends MIDlet
    implements CommandListener, CategoryBarUtils.ElementListener {

    private final int CANVAS_1 = 0;
    private final int CANVAS_2 = 1;
    
    private final int CHROME_AND_CATEGORY = 0;
    private final int CHROME = 1;
    private final int CANVAS_TEXT_EDITOR = 2;
    private final int STATUS_BAR_ONLY = 3;
    private final int FULL_SCREEN = 4;
    
    private final String[] DIALOG_ITEMS = new String[] {
        "Chrome + category bar",
        "Chrome",
        "Canvas text editor",
        "Status bar only",
        "Full screen"
    };
    
    private final String[] CONTENTS = {
        "Chrome and category bar ",
        "Chrome, no Category bar",
        "Canvas text editor",
        "Status bar only",
        "Full screen"
    };

    private final String[] TITLES = new String[] {
        "Canvas 1-",
        "Canvas 2",
        "Canvas text editor",
        "",
        ""
    };

    private Object categoryBar = null;
    private BackStack backStack;
    private List canvasList;
    private boolean categoryBarWasVisible = false;

    public void startApp() {
        canvasList = new List(Strings.getTitle(Strings.CANVAS),
            List.IMPLICIT, DIALOG_ITEMS, null);
        Image category1Icon = ImageLoader.load(ImageLoader.CATEGORYBAR_1);
        Image category2Icon = ImageLoader.load(ImageLoader.CATEGORYBAR_2);

        IconCommand canvas1Command = new IconCommand("Canvas 1",
            category1Icon,
            null,
            Command.SCREEN,
            1);
        IconCommand canvas2Command = new IconCommand("Canvas 2",
            category2Icon,
            null,
            Command.SCREEN,
            1);
        IconCommand[] iconCommands = {
            canvas1Command,
            canvas2Command
        };
        CategoryBar categoryBar = new CategoryBar(iconCommands, true);
        this.categoryBar = categoryBar;
        CategoryBarUtils.setListener(categoryBar, this);

        canvasList.setCommandListener(this);
        canvasList.setSelectCommand(List.SELECT_COMMAND);
        canvasList.addCommand(Commands.BACK);
        canvasList.addCommand(Commands.INFORMATION);

        backStack = new BackStack(this);

        Orientation.enableOrientations();
        Display.getDisplay(this).setCurrent(canvasList);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    /**
     * The command handler listening to the List. Based on the selection,
     * initializes and launches the requested Canvas example.
     */
    public void commandAction(Command c, Displayable d) {
        Displayable displayable = null;
        ExampleCanvas canvas = null;
        int i = canvasList.getSelectedIndex();

        if (c == List.SELECT_COMMAND) {
            switch (i) {
                default:
                case CHROME_AND_CATEGORY:
                    canvas = new ExampleCanvas(TITLES[i] + "1",
                        CONTENTS[i] + "1");
                    setCategoryBarVisibility(true);
                    break;
                case CHROME:
                    canvas = new ExampleCanvas(TITLES[i], CONTENTS[i]);
                    canvas.addCommand(Commands.BACK);
                    break;
                case STATUS_BAR_ONLY:
                    canvas = new ExampleCanvas(TITLES[i], CONTENTS[i], this);
                    canvas.setFullScreen(true);
                    canvas.setStatusBarVisible(true);
                    canvas.addCommand(Commands.BACK);
                    break;
                case FULL_SCREEN:
                    canvas = new ExampleCanvas(TITLES[i], CONTENTS[i], this);
                    canvas.setFullScreen(true);
                    canvas.addCommand(Commands.BACK);
                    break;
                case CANVAS_TEXT_EDITOR:
                    canvas = new TextEditorCanvas(TITLES[i], CONTENTS[i],
                        this);
                    canvas.addCommand(Commands.BACK);
                    break;
            }

            if (canvas != null) {
                displayable = (Displayable)canvas;
            }
            displayable.setCommandListener(this);
            backStack.forward(displayable);
        }
        // Back from either a Canvas example view or the Information view
        else if (c == Commands.BACK
            || c == Commands.INFORMATION_BACK) {
            backStack.back();

            // Toggle the CategoryBar visibility if necessary;
            // show it if coming back to CHROME_AND_CATEGORY view 
            // from the information view, otherwise hide it
            if (categoryBarWasVisible) {
                setCategoryBarVisibility(true);
                categoryBarWasVisible = false;
            }
            else {
                setCategoryBarVisibility(false);
            }
        }
        else if (c == Commands.INFORMATION) {
            // Store the previous state of the CategoryBar in order to be able
            // to restore the state when coming back from the information view
            if (getCategoryBarVisibility()) {
                setCategoryBarVisibility(false);
                categoryBarWasVisible = true;
            }
            backStack.forward(new InformationView(Strings.CANVAS, this));
        }
    }

    private boolean getCategoryBarVisibility() {
        if (categoryBar != null) {
            CategoryBar categoryBar = (CategoryBar) this.categoryBar;
            return categoryBar.getVisibility();
        }
        return false;
    }

    private void setCategoryBarVisibility(boolean visible) {
        if (categoryBar != null) {
            CategoryBar categoryBar = (CategoryBar) this.categoryBar;
            categoryBar.setVisibility(visible);
        }
    }

    /**
     * This method is called whenever the user acts with the CategoryBar
     * (in Canvas + CategoryBar example)
     * @param categoryBar
     * @param selectedIndex 
     */
    public void notifyElementSelected(CategoryBar categoryBar, int selectedIndex) {
        ExampleCanvas canvas;

        // Switch the view based on which CategoryBar item is tapped
        switch (selectedIndex) {
            case CANVAS_1:
            case CANVAS_2:
                String id = (selectedIndex == CANVAS_1 ? "1" : "2");
                canvas = new ExampleCanvas(TITLES[CHROME_AND_CATEGORY] + id,
                    CONTENTS[CHROME_AND_CATEGORY] + id);
                canvas.setCommandListener(this);
                Display.getDisplay(this).setCurrent(canvas);
                break;
            case ElementListener.BACK:
                setCategoryBarVisibility(false);
                backStack.back();
                break;
            default:
                break;
        }
    }
}
