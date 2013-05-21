/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.categorybar;

import com.nokia.example.ashauicomponentdemos.utils.ImageLoader;
import com.nokia.mid.ui.CategoryBar;
import com.nokia.mid.ui.IconCommand;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.StringItem;

/**
 * CategoryBarView is a Form that can display four different views. It is used
 * in CategoryBarExample where CategoryBar is used to switch between the views.
 */
public class CategoryBarView
    extends Form {

    protected static final String[] VIEW_NAMES = {
        "Downloads",
        "Pinch",
        "Slider",
        "Tap",
    };
    private int amountOfCategories;
    private int mode;

    public CategoryBarView(int amountOfCategories, int mode) {
        super("Search");

        this.amountOfCategories = amountOfCategories;
        this.mode = mode;
    }

    /**
     * A method to fake different views
     * @param index of the view to change to
     */
    public void setActive(int index) {
        if (index >= 0 && index < VIEW_NAMES.length) {
            this.deleteAll();
            String name = String.valueOf(amountOfCategories);
            if (mode == CategoryBar.ELEMENT_MODE_STAY_SELECTED) {
                this.setTitle(VIEW_NAMES[index]);
                name += " tabs";
                StringItem stringItem = new StringItem("", name);
                this.append(stringItem);
            }
            else {           
                setTitle(name + " actions");
            }
        }
    }

    /**
     * A factory method to create a CategoryBar to display in the parent view. 
     * Amount of categories is specified during the construction of this object.
     * @return CategoryBar with the requested amount of categories
     */
    public CategoryBar createCategoryBar(int mode) {
        Vector commands = new Vector();

        if (amountOfCategories >= 1) {
            Image search = ImageLoader.load(ImageLoader.CATEGORYBAR_DOWNLOADS);
            // Passing null as the second image makes the phone draw the selected
            // image with the current highlight color
            commands.addElement(new IconCommand("downloads", search, null,
                Command.SCREEN, 1));
        }
        if (amountOfCategories >= 2) {
            Image comments = ImageLoader.load(ImageLoader.CATEGORYBAR_PINCH);
 
            commands.addElement(new IconCommand("pinch", comments, null,
                Command.SCREEN, 1));
        }
        if (amountOfCategories >= 3) {
            Image allExtend = ImageLoader.load(ImageLoader.CATEGORYBAR_SLIDER);
 
            commands.addElement(new IconCommand("slider", allExtend, null,
                Command.SCREEN, 1));
        }
        if (amountOfCategories == 4) {
            Image contactsExtend = ImageLoader.load(ImageLoader.CATEGORYBAR_TAP);

            commands.addElement(new IconCommand("tap", contactsExtend, null,
                Command.SCREEN, 1));
       }

        IconCommand[] iconCommands = new IconCommand[amountOfCategories];

        for (int i = 0; i < amountOfCategories; i++) {
            iconCommands[i] = (IconCommand) commands.elementAt(i);
        }

        return new CategoryBar(iconCommands, true, mode);
    }
}
