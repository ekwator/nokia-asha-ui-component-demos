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
import com.nokia.uihelpers.orientation.Orientation;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;

/**
 * TextBox with additional Commands.
 */
public class ExampleTextView
    extends TextBox
    implements CommandListener {

    private static final int MAX_SIZE = 512;
    private static final String TEXT = "Lorem ipsum dolor sit amet, "
        + "consectetur adipisicing elit,"
        + "sed do eiusmod tempor incididunt ut labore et";
    private static final String EDITABLE_TITLE = "Editable text";
    private static final String NONEDITABLE_TITLE = "Noneditable text";
    private final Command CLEAR_COMMAND = new Command(
        "Clear all", Command.SCREEN, 1);
    private CommandListener parentCommandListener;

    /**
     * Constructs the view
     * @param editable Whether the TextBox should be editable or not
     */
    public ExampleTextView(boolean editable, IDisplayer displayer,
        CommandListener parentCommandListener) {
        this(editable ? EDITABLE_TITLE : NONEDITABLE_TITLE, editable, displayer,
            parentCommandListener);
    }

    /**
     * Constructs the view
     * @param title title of the view
     * @param editable Whether the TextBox should be editable or not
     */
    public ExampleTextView(String title, boolean editable, IDisplayer displayer,
        CommandListener parentCommandListener) {
        super(title, TEXT, MAX_SIZE, TextField.UNEDITABLE);

        this.parentCommandListener = parentCommandListener;
        // If the TextBox is editable, make it accept all input and 
        // add a clear command to the options menu
        if (editable) {
            this.setConstraints(TextField.ANY);
            this.addCommand(CLEAR_COMMAND);
        }
        this.addCommand(Commands.DONE);
        this.addCommand(Commands.BACK);
        this.setCommandListener(this);
        Orientation.enableOrientations();
    }

    /**
     * Handle commands here and delegate some to the parent command listener
     * @param c
     * @param d 
     */
    public void commandAction(Command c, Displayable d) {
        if (c == CLEAR_COMMAND) {
            // Clear the TextBox contents
            this.setString(null);
        }
        else {
            parentCommandListener.commandAction(c, d);
        }
    }
}
