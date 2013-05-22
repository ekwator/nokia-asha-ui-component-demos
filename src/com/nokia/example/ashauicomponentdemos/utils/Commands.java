/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.utils;


import javax.microedition.lcdui.Command;

/**
 * A public store for commonly used Commands, to allow different parts
 * of the MIDlets talk the same language
 */
public class Commands {

    public static final Command SELECT =
        new Command("Select", Command.ITEM, 1);
    public static final Command LIST_SELECT =
        new Command("Select", Command.OK, 1);
    public static final Command BACK =
        new Command("Back", Command.BACK, 1);
    public static final Command EXIT =
        new Command("Back", Command.EXIT, 1);
    public static final Command INFORMATION =
        new Command("Information", Command.SCREEN, 3);
    public static final Command INFORMATION_BACK =
        new Command("Back", Command.BACK, 1);
    public static final Command OK = new Command("OK", Command.OK, 1);
    public static final Command DONE = new Command("Done", Command.OK, 1);
    public static final Command ALERT_HELP =
        new Command("Help", Command.HELP, 1);
    public static final Command CANCEL =
        new Command("Cancel", Command.CANCEL, 1);
    public static final Command ALERT_OK = new Command("OK", Command.OK, 1);
    public static final Command ALERT_CANCEL =
        new Command("Cancel", Command.CANCEL, 1);
    public static final Command ALERT_CONTINUE =
        new Command("Continue", Command.OK, 1);
    public static final Command ALERT_SAVE_YES =
        new Command("Yes", Command.OK, 1);
    public static final Command ALERT_SAVE_NO =
        new Command("No", Command.HELP, 1);
    public static final Command ALERT_SAVE_BACK =
        new Command("Back", Command.CANCEL, 1);
    public static final Command EDIT =
        new Command("Edit", Command.SCREEN, 1);
    public static final Command OK_SCREEN =
        new Command("OK", Command.SCREEN, 2);
    public static final Command DELETE_SCREEN =
        new Command("Delete", Command.SCREEN, 2);

}
