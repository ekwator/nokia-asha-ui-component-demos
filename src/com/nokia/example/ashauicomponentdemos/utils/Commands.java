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
import javax.microedition.lcdui.Command;

/**
 * A public store for commonly used Commands, to allow different parts
 * of the MIDlets talk the same language
 */
public class Commands {

    public static final Command SELECT =
        new Command(toFT("Select"), Command.ITEM, 1);
    public static final Command LIST_SELECT =
        new Command(toFT("Select"), Command.OK, 1);
    public static final Command BACK =
        new Command(toFT("Back"), Command.BACK, 1);
    public static final Command EXIT =
        new Command(toFT("Back"), Command.EXIT, 1);
    public static final Command INFORMATION =
        new Command(toFT("Information"), Command.SCREEN, 3);
    public static final Command INFORMATION_BACK =
        new Command(toFT("Back"), Command.BACK, 1);
    public static final Command OK = new Command("OK", Command.OK, 1);
    public static final Command DONE = new Command(toFT("Done"), Command.OK, 1);
    public static final Command ALERT_HELP =
        new Command(toFTAlert("Help"), Command.HELP, 1);
    public static final Command CANCEL =
        new Command(toFT("Cancel"), Command.CANCEL, 1);
    public static final Command ALERT_OK = new Command("OK", Command.OK, 1);
    public static final Command ALERT_CANCEL =
        new Command(toFTAlert("Cancel"), Command.CANCEL, 1);
    public static final Command ALERT_CONTINUE =
        new Command(toFTAlert("Continue"), Command.OK, 1);
    public static final Command ALERT_SAVE_YES =
        new Command(toFTAlert("Yes"), Command.OK, 1);
    public static final Command ALERT_SAVE_NO =
        new Command(toFTAlert("No"), Command.HELP, 1);
    public static final Command ALERT_SAVE_BACK =
        new Command(toFTAlert("Back"), Command.CANCEL, 1);
    public static final Command EDIT =
        new Command(toFT("Edit"), Command.SCREEN, 1);

    public static final Command OK_SCREEN =
        new Command("OK", Command.SCREEN, 2);
    public static final Command DELETE_SCREEN =
        new Command(toFT("Delete"), Command.SCREEN, 2);

    private static String toFT(String text) {
        return Compatibility.toLowerCaseIfFT(text);
    }

    private static String toFTAlert(String text) {
        return Compatibility.toUpperCaseIfFT(text);
    }
}
