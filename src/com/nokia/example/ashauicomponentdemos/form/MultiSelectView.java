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
 * Demonstrates the use of the ChoiceGroup class
 */
public class MultiSelectView
    extends FormExampleView {

    private final String LABEL = "Select multiple";
    private final String ITEM = "Choice item ";
    private final int ITEM_COUNT = 4;
    private Image image = null;
    private ChoiceGroup choiceGroup1;
    private ChoiceGroup choiceGroup2;
    private ChoiceGroup choiceGroup3;
    private boolean[] choiceGroup1Values = new boolean[ITEM_COUNT];
    private boolean[] choiceGroup2Values = new boolean[ITEM_COUNT];
    private boolean[] choiceGroup3Values = new boolean[ITEM_COUNT];

    public MultiSelectView(CommandListener commandListener, String title) {
        super(title, commandListener);

        // Load the placeholder icon
        image = ImageLoader.load(ImageLoader.ICON);

        // Create a ChoiceGroup with a few dummy items
        choiceGroup1 = new ChoiceGroup(null, Choice.MULTIPLE);
        for (int i = 1; i <= ITEM_COUNT; i++) {
            choiceGroup1.append(ITEM + String.valueOf(i), null);
        }
        this.append(choiceGroup1);

        // Create a ChoiceGroup with a few dummy items
        choiceGroup2 = new ChoiceGroup(LABEL, Choice.MULTIPLE);
        for (int i = 1; i <= ITEM_COUNT; i++) {
            choiceGroup2.append(ITEM + String.valueOf(i), null);
        }
        this.append(choiceGroup2);

        // Create a ChoiceGroup with dummy items with images
        choiceGroup3 = new ChoiceGroup(LABEL, Choice.MULTIPLE);
        for (int i = 1; i <= ITEM_COUNT; i++) {
            choiceGroup3.append(ITEM + String.valueOf(i), image);
        }
        this.append(choiceGroup3);
    }

    protected void storeCurrentValues() {
        choiceGroup1.getSelectedFlags(choiceGroup1Values);
        choiceGroup2.getSelectedFlags(choiceGroup2Values);
        choiceGroup3.getSelectedFlags(choiceGroup3Values);
    }

    protected void cancelChanges() {
        choiceGroup1.setSelectedFlags(choiceGroup1Values);
        choiceGroup2.setSelectedFlags(choiceGroup2Values);
        choiceGroup3.setSelectedFlags(choiceGroup3Values);
    }
}
