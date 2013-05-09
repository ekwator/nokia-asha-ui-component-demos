/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.utils;

import com.nokia.uihelpers.Compatibility;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.CommandListener;

/**
 * Confirmation query for save operations
 */
public class SaveChangesAlert
    extends Alert {

    public SaveChangesAlert(CommandListener listener) {
        super(Compatibility.toLowerCaseIfFT("Save changes"));
        setString("Save changes made?");
        addCommand(Commands.ALERT_SAVE_YES);
        addCommand(Commands.ALERT_SAVE_NO);
        if (!Compatibility.hasOnekeyBack()) {
            addCommand(Commands.ALERT_SAVE_BACK);
        }
        else {
            addCommand(Commands.BACK);
        }
        setTimeout(Alert.FOREVER);
        setCommandListener(listener);
    }
}
