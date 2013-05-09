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
 * This class displays a list for making multiple selections. The list 
 * selections are persisted using record management store (RMS).
 */
public class MultipleListView
    extends List
    implements CommandListener {
    
    private static final String RMS_NAME = "MultipleListExample";
    private final int MAX_ITEMS = 10;
    private boolean[] selections;
    private CommandListener parentCommandListener;
    private BackStack backStack;
    
    public MultipleListView(MIDlet parent, CommandListener commandListener) {
        super("Multiple", List.MULTIPLE);
        
        for (int i = 0; i < MAX_ITEMS; i++) {
            this.append("List item " + String.valueOf(i + 1), null);
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
        boolean[] s = new boolean[this.size()];
        this.getSelectedFlags(s);
        String text = "List items ";
        boolean anySelected = false;
        for (int i = 0; i < s.length; i++) {
            if (s[i]) {
                text += (i + 1) + ", ";
                anySelected = true;
            }
        }
        if (anySelected) {
            text = text.substring(0, text.length() - 2);
            text += " selected.";
        }
        else {
            text = "No list items selected.";
        }
        
        Alert selectedAlert = new Alert("Selected");
        selectedAlert.setString(text);
        selectedAlert.addCommand(Commands.ALERT_CONTINUE);
        selectedAlert.addCommand(Commands.BACK);
        selectedAlert.setTimeout(Alert.FOREVER);
        selectedAlert.setCommandListener(this);
        backStack.forward(selectedAlert);
    }
    
    private void saveState() {
        boolean[] s = new boolean[this.size()];
        this.getSelectedFlags(s);
        ByteArrayOutputStream bout = null;
        try {
            bout = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream(bout);
            dout.writeInt(s.length);
            for (int i = 0, size = s.length; i < size; i++) {
                dout.writeBoolean(s[i]);
            }
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
                int length = din.readInt();
                selections = new boolean[length];
                for (int i = 0; i < length; i++) {
                    selections[i] = din.readBoolean();
                }
                this.setSelectedFlags(selections);
            }
            catch (IOException e) {
            }
        }
    }
}
