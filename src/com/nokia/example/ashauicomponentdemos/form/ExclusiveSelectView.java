/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.form;

import com.nokia.example.ashauicomponentdemos.utils.ImageLoader;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Image;

/**
 * Demonstrates the usage of the exclusive ChoiceGroup
 */
public class ExclusiveSelectView
    extends FormExampleView {

    private final int ITEM_COUNT = 4;
    private Image image = null;
    private ChoiceGroup choiceGroup1;
    private ChoiceGroup choiceGroup2;
    private ChoiceGroup choiceGroup3;
    private int choiceGroup1Value = 0;
    private int choiceGroup2Value = 0;
    private int choiceGroup3Value = 0;

    public ExclusiveSelectView(CommandListener commandListener, String title) {
        super(title, commandListener);

        // Load the image
        image = ImageLoader.load(ImageLoader.ICON);

        // A ChoiceGroup without images
        choiceGroup1 = new ChoiceGroup(null, Choice.EXCLUSIVE);
        for (int i = 1; i <= ITEM_COUNT; i++) {
            choiceGroup1.append("Choice item "
                + String.valueOf(i), null);
        }
        this.append(choiceGroup1);

        // A ChoiceGroup with label
        choiceGroup2 = new ChoiceGroup(
            "Exclusive choice group",
            Choice.EXCLUSIVE);
        for (int i = 1; i <= ITEM_COUNT; i++) {
            choiceGroup2.append("Choice item "
                + String.valueOf(i), null);
        }
        this.append(choiceGroup2);

        // A ChoiceGroup with images
        choiceGroup3 = new ChoiceGroup(
            "Exclusive choice group",
            Choice.EXCLUSIVE);
        for (int i = 1; i <= ITEM_COUNT; i++) {
            choiceGroup3.append("Choice item "
                + String.valueOf(i), image);
        }
        this.append(choiceGroup3);
    }

    protected void storeCurrentValues() {
        choiceGroup1Value = choiceGroup1.getSelectedIndex();
        choiceGroup2Value = choiceGroup2.getSelectedIndex();
        choiceGroup3Value = choiceGroup3.getSelectedIndex();
    }

    protected void cancelChanges() {
        choiceGroup1.setSelectedIndex(choiceGroup1Value, true);
        choiceGroup2.setSelectedIndex(choiceGroup2Value, true);
        choiceGroup3.setSelectedIndex(choiceGroup3Value, true);
    }
}
