/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.ticker;

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
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

/**
 * Demonstrates the usage of the Ticker component
 */
public class TickerExample
    extends MIDlet
    implements CategoryBarUtils.ElementListener, CommandListener {

    private final int TICKER_LIST = 0;
    private final int TICKER_CANVAS = 1;
    private final int TICKER_TEXT = 2;
    private final int TICKER_FORM = 3;
    private final String[] DIALOG_ITEMS = 
        new String[]{
            "Ticker - list",
            "Ticker - canvas",
            "Ticker - text",
            "Ticker - form"
        };
    private List tickerList;
    private CategoryBar categoryBar;
    private TickerListView tickerListView;
    private TickerCanvasView tickerCanvasView;
    private TickerTextView tickerTextView;
    private TickerFormView tickerFormView;
    private BackStack backStack;

    /**
     * Create a CategoryBar for the examples, initialize and display the first
     * one
     */
    public void startApp() {
        Orientation.enableOrientations();
        backStack = new BackStack(this);
        Image listImage = ImageLoader.load(ImageLoader.CATEGORY_LIST);
        Image canvasImage = ImageLoader.load(ImageLoader.CATEGORY_CANVAS);
        Image textImage = ImageLoader.load(ImageLoader.CATEGORY_TEXT);
        Image formImage = ImageLoader.load(ImageLoader.FORM);

        IconCommand[] iconCommands = {
            new IconCommand(DIALOG_ITEMS[TICKER_LIST], listImage, null,
            Command.SCREEN, 1),
            new IconCommand(DIALOG_ITEMS[TICKER_CANVAS], canvasImage, null,
            Command.SCREEN, 1),
            new IconCommand(DIALOG_ITEMS[TICKER_TEXT], textImage, null,
            Command.SCREEN, 1),
            new IconCommand(DIALOG_ITEMS[TICKER_FORM], formImage, null,
            Command.SCREEN, 1)
        };

        categoryBar = new CategoryBar(iconCommands, true);
        CategoryBarUtils.setListener(categoryBar, this);
        categoryBar.setVisibility(true);
        Display.getDisplay(this).setCurrent(getTickerListView());
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    /**
     * Handle the CategoryBar events, change between the example views
     *
     * @param cb
     * @param i
     */
    public void notifyElementSelected(CategoryBar cb, int i) {
        Display d = Display.getDisplay(this);
        switch (i) {
            default:
            case TICKER_LIST:
                d.setCurrent(getTickerListView());
                break;
            case TICKER_CANVAS:
                d.setCurrent(getTickerCanvasView());
                break;
            case TICKER_TEXT:
                d.setCurrent(getTickerTextView());
                break;
            case TICKER_FORM:
                d.setCurrent(getTickerFormView());
                break;
            case ElementListener.BACK:
                backStack.back();
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == Commands.INFORMATION) {
            if (categoryBar != null) {
                categoryBar.setVisibility(false);
            }
            backStack.forward(new InformationView(Strings.TICKER, this));
        }
        else if (c == Commands.INFORMATION_BACK) {
            if (categoryBar != null) {
                categoryBar.setVisibility(true);
            }
            backStack.back();
        }
        else if (c == Commands.BACK || c == Commands.EXIT) {
            backStack.back();
        }
        else if (c == List.SELECT_COMMAND) {
            if (d instanceof TickerListView) {
                showSelected();
            }
            else {
                int i = tickerList.getSelectedIndex();
                Displayable view;
                switch (i) {
                    default:
                    case TICKER_LIST:
                        view = getTickerListView();
                        break;
                    case TICKER_CANVAS:
                        view = getTickerCanvasView();
                        break;
                    case TICKER_TEXT:
                        view = getTickerTextView();
                        break;
                    case TICKER_FORM:
                        view = getTickerFormView();
                        break;
                }
                view.addCommand(Commands.BACK);
                backStack.forward(view);
            }
        }
        else if (c == Commands.ALERT_CONTINUE) {
            backStack.back(tickerList == null ? 1 : 2);
        }
    }

    private void showSelected() {
        String text = "List item "
            + tickerListView.getSelectedIndex() + " selected.";
        Alert selectedAlert = new Alert("Selected");
        selectedAlert.setString(text);
        selectedAlert.addCommand(Commands.ALERT_CONTINUE);
        selectedAlert.addCommand(Commands.BACK);
        selectedAlert.setTimeout(Alert.FOREVER);
        selectedAlert.setCommandListener(this);

        backStack.forward(selectedAlert);
    }

    private TickerListView getTickerListView() {
        if (tickerListView == null) {
            tickerListView = new TickerListView(DIALOG_ITEMS[TICKER_LIST], this);
        }
        return tickerListView;
    }

    private TickerCanvasView getTickerCanvasView() {
        if (tickerCanvasView == null) {
            tickerCanvasView = new TickerCanvasView(DIALOG_ITEMS[TICKER_CANVAS],
                this);
        }
        return tickerCanvasView;
    }

    private TickerTextView getTickerTextView() {
        if (tickerTextView == null) {
            tickerTextView = new TickerTextView(DIALOG_ITEMS[TICKER_TEXT], this);
        }
        return tickerTextView;
    }

    private TickerFormView getTickerFormView() {
        if (tickerFormView == null) {
            tickerFormView = new TickerFormView(DIALOG_ITEMS[TICKER_FORM], this);
        }
        return tickerFormView;
    }
}
