/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.form;

import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.TextField;

/**
 * Demonstrates the usage of the TextField component
 */
public class TextInputView
    extends FormExampleView {

    private final int MAX_CHARS = 256;
    private final int[] TYPES = {
        TextField.ANY,
        TextField.DECIMAL,
        TextField.EMAILADDR,
        TextField.INITIAL_CAPS_SENTENCE,
        TextField.INITIAL_CAPS_WORD,
        TextField.NUMERIC,
        TextField.PASSWORD,
        TextField.PHONENUMBER,
        TextField.SENSITIVE,
        TextField.URL
    };
    private final String[] TITLES = new String[]{
        "Keypad normal",
        "Keypad decimal",
        "Keypad email",
        "Keypad initial caps sentence",
        "Keypad initial caps word",
        "Keypad numeric",
        "Keypad password",
        "Keypad phonenumber",
        "Keypad sensitive",
        "Keypad URL"
    };
    
    private TextField[] textFields;
    private String[] textFieldTexts;

    public TextInputView(CommandListener commandListener, String title) {
        super(title, commandListener);
        
        textFields = new TextField[TYPES.length];
        textFieldTexts = new String[TYPES.length];
        
        // Create TextFields with the different constraints and
        // insert them to the form
        for (int i = 0; i < TYPES.length; i++) {
            textFields[i] = new TextField(TITLES[i], "", MAX_CHARS, TYPES[i]);
            textFieldTexts[i] = textFields[i].getString();
            this.append(textFields[i]);
        }
    }

    protected void storeCurrentValues() {
        for (int i = 0; i < TYPES.length; i++) {
            textFieldTexts[i] = textFields[i].getString();
        }
    }

    protected void cancelChanges() {
        for (int i = 0; i < TYPES.length; i++) {
            textFields[i].setString(textFieldTexts[i]);
        }
    }
}
