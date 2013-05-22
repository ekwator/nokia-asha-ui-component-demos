/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.utils;



/**
 * Titles of the example MIDlets
 */
public class Strings {

    public static final int LISTS = 0;
    public static final int TEXT = 1;
    public static final int DIALOGS = 2;
    public static final int CANVAS = 3;
    public static final int FORM = 4;
    public static final int CATEGORYBAR = 5;
    public static final int TICKER = 6;
    public static final int CONFIRMATION = 7;
    public static final int EMPTYCONTENT = 8;
    public static final int MULTIPLEITEMS = 9;
    public static final int ZOOM = 10;
    public static final int ADDNEW = 11;
    public static final int INTERDEPENDENT = 12;
    public static final int KEYPADS = 13;
    public static final int MENUS = 14;
    private static final String[] TITLES = {
        "Lists",
        "Text",
        "Dialogs",
        "Canvas",
        "Form",
        "Categories",
        "Ticker",
        "Confirmation",
        "Empty content",
        "Multiple items",
        "Zoom",
        "Add new",
        "Interdependent",
        "Keypads",
        "Menus"
    };

    public static String getTitle(int index) {
        return TITLES[index];
    }
}
