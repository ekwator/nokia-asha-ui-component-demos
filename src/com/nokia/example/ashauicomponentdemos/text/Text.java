/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.text;

import com.nokia.example.ashauicomponentdemos.utils.Commands;
import com.nokia.example.ashauicomponentdemos.utils.IDisplayer;
import com.nokia.example.ashauicomponentdemos.utils.InformationView;
import com.nokia.example.ashauicomponentdemos.utils.Strings;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

/**
 * Demonstrates the usage of the TextBox class
 */
public class Text
    extends MIDlet
    implements CommandListener, IDisplayer {

    private final int EDITABLE_TEXT = 0;
    private final int NON_EDITABLE_TEXT = 1;
    private final int EDIT_FLOW = 2;
    private List list;
    private ExampleTextView editableView;
    private ExampleTextView nonEditableView;
    private ExampleTextView editFlowView;
    private ExampleTextView editFlowEditView;
    private String originalString = "";
    private final String[] LIST_ITEMS = 
        new String[]{
            "Editable text",
            "Non-editable text",
            "Edit flow"
        };

    /**
     * Start the app, create the initial list and display it
     */
    public void startApp() {
        list = new List(Strings.getTitle(Strings.TEXT),
            List.IMPLICIT, LIST_ITEMS, null);
        list.addCommand(Commands.EXIT);
        list.addCommand(Commands.INFORMATION);
        list.setSelectCommand(List.SELECT_COMMAND);
        list.setCommandListener(this);

        editableView = new ExampleTextView(true, this, this);
        nonEditableView = new ExampleTextView(false, this, this);
        editFlowView = new ExampleTextView("Edit flow", false, this, this);
        editFlowView.addCommand(Commands.EDIT);
        editFlowEditView = new ExampleTextView("Edit flow", true, this, this);

        Display.getDisplay(this).setCurrent(list);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == Commands.EDIT) {
            editFlowEditView.setString(editFlowView.getString());
            setCurrent(editFlowEditView);
        }
        else if (c == List.SELECT_COMMAND) {
            // Instantiate and display the example view as either
            // editable or non-editable
            switch (list.getSelectedIndex()) {
                default:
                case EDITABLE_TEXT:
                    originalString = editableView.getString();
                    setCurrent(editableView);
                    break;
                case NON_EDITABLE_TEXT:
                    setCurrent(nonEditableView);
                    break;
                case EDIT_FLOW:
                    originalString = editFlowView.getString();
                    setCurrent(editFlowView);
                    break;
            }
        }
        else if (c == Commands.DONE) {
            if (d == editFlowEditView) {
                editFlowView.setString(editFlowEditView.getString());
            }
            setCurrent(list);
        }
        else if (c == Commands.BACK) {
            // Discard possible changes.
            if (d == editableView) {
                editableView.setString(originalString);
            } else if (d == editFlowEditView) {
                editFlowView.setString(originalString);
            }
            setCurrent(list);
        }
        else if (c == Commands.INFORMATION) {
            setCurrent(new InformationView(Strings.TEXT, this));
        }
        else if (c == Commands.INFORMATION_BACK) {
            setCurrent(list);
        }
        else if (c == Commands.EXIT) {
            notifyDestroyed();
        }
    }

    public void setCurrent(Displayable displayable) {
        Display.getDisplay(this).setCurrent(displayable);
    }
}
