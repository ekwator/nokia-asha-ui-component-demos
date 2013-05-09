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
import com.nokia.example.ashauicomponentdemos.utils.RMSUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

/**
 * This class displays a list for making exclusive selections that have to be
 * explicitly confirmed via a Command. The list selection is persisted using
 * record management store (RMS).
 */
public class ExclusiveConfirmListView
    extends List
    implements CommandListener {
    
    private static final String RMS_NAME = "ExclusiveConfirmListExample";
    private final int MAX_ITEMS = 10;
    private int exclusiveSelection;
    private CommandListener parentCommandListener;
    private BackStack backStack;
    
    public ExclusiveConfirmListView(MIDlet parent, CommandListener commandListener) {
        super("Exclusive", List.EXCLUSIVE);
        
        for (int i = 0; i < MAX_ITEMS; i++) {
            this.append("List item "
                + String.valueOf(i + 1), null);
        }
        
        this.setFitPolicy(List.TEXT_WRAP_DEFAULT);
        this.addCommand(Commands.DONE);
        this.addCommand(Commands.BACK);
        this.setCommandListener(this);
        loadState();
        parentCommandListener = commandListener;
        backStack = new BackStack(parent);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == Commands.BACK) {
            if (d instanceof Alert) {
                backStack.back();
            }
            else {
                parentCommandListener.commandAction(c, d);
            }
        }
        else if (c == Commands.DONE) {
            saveState();
            showSelected();
        }
        else if (c == Commands.ALERT_CONTINUE) {
            parentCommandListener.commandAction(Commands.BACK, d);
        }
        else {
            parentCommandListener.commandAction(c, d);
        }
    }
    
    private void showSelected() {
        Alert selectedAlert = new Alert("Selected");
        selectedAlert.setString("List item " +
                (this.getSelectedIndex() + 1) +
                " selected.");
        selectedAlert.addCommand(Commands.ALERT_CONTINUE);
        selectedAlert.addCommand(Commands.BACK);
        selectedAlert.setTimeout(Alert.FOREVER);
        selectedAlert.setCommandListener(this);
        backStack.forward(selectedAlert);
    }
    
    private void saveState() {
        ByteArrayOutputStream bout = null;
        try {
            bout = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream(bout);
            dout.writeInt(this.getSelectedIndex());
            RMSUtils.save(RMS_NAME, bout.toByteArray());
        }
        catch (IOException e) {
        }
        finally {
            try {
                if (bout != null) {
                    bout.close();
                }
            }
            catch (IOException e) {
            }
        }
    }
    
    private void loadState() {
        byte[] data = RMSUtils.load(RMS_NAME);
        if (data != null) {
            try {
                DataInputStream din =
                    new DataInputStream(new ByteArrayInputStream(data));
                exclusiveSelection = din.readInt();
                this.setSelectedIndex(exclusiveSelection, true);
            }
            catch (IOException e) {
            }
        }
        else {
            this.setSelectedIndex(0, true);
        }
    }
}
