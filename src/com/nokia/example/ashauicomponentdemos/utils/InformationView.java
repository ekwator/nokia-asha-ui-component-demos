/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.utils;

import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Form;

/**
 * The information view displayed in various parts of the application
 * presenting context-sensitive help text
 */
public class InformationView
    extends Form {

    private static final String[] INFORMATION_TEXTS = {
        // LISTS
        "This mini application shows different versions of a list."
        + "\n\n- The implicit list should be used for drill downs; "
        + "the list closes after an item is selected"
        + "\n\n- The exclusive list should be used for selecting one item "
        + "(and one item must always be selected); Back discards the selection"
        + ", and \"done\" confirms the selection"
        + "\n\n- The multiple list should be used for selecting no items, one "
        + "item, or multiple items; Back discards the selection, and \"done\" "
        + "from Options menu confirms the selection"
        + "\n\n- Truncated shows an implicit list where the list items are "
        + "truncated so that they can each fit into one row. Try rotating the "
        + "phone to see how the content of the row is extended"
        + "\n\n- Thumbnails shows an implicit list with additional thumbnails "
        + "for each item. Thumbnails are aligned to the left of the list"
        + "\n\n- Fancy list shows a custom list with thumbnails, time stamps "
        + "and indicator icons. The list items have title and subtext, and "
        + "the list uses a divider."
        + "\n\n- Grid list shows a grid layout that is done with the Grid "
        + "template, included in the Asha SDK."
        + "\n\nFor more information on lists, please see the Nokia Asha design guidelines.",
        
        // TEXT
        "This mini application shows different variations of a TextBox. It can be "
        + "\n\n-editable; this is indicated by blue text on white background. The user "
        + "can alter the text by tapping the text which will slide up the virtual "
        + "keyboard (if the virtual keyboard is not opened automatically by the MIDlet). "
        + "In edit mode, the font turns black. After finishing the changes, the user can " 
        + "close the keyboard by pressing Back, tapping outside the keyboard or dragging it down. "
        + "The font turns blue again when in view mode."
        + "\n\n-non-editable; this is indicated with light grey font on a white background. The user "
        + "cannot change the text. "
        + "\n\nFor more information on TextBox, see the Nokia Asha design guidelines.",
        
        // DIALOGS
        "This mini app demonstrates various types of dialog and should cover "
        + "most possibilities for the use of dialogs."
        + "\n\n- timed dialog; disappears after a timeout (in our example 3 "
        + "seconds) and the user returns to the parent view"
        + "\n\n- text + 1 button"
        + "\n\n- text + 1 button+ image"
        + "\n\n- text + 1 button + image + determinate progress indicator"
        + "\n\n- text + 1 button + image + indeterminate progress indicator"
        + "\n\n- text + 2 buttons"
        + "\n\n- text + 3 buttons + image + indeterminate progress indicator"
        + "\n\nDeterminate progress indicators should be used if the process "
        + "duration is predictable or if it takes longer than 10 seconds. "
        + "Indeterminate progress indicators should only be used if the "
        + "process length is not predictable, but definitely less than 10 "
        + "seconds."
        + "\n\nIf there is only one button, it confirms the note, e.g. an "
        + "information dialog."
        + "\n\n If there are two buttons, the top button should be to move the "
        + "user forward (Yes, Ok); the bottom button is for moving backwards (Back, Cancel). "
        + "\n\nIf there are three buttons, the top and bottom buttons remain as they are, "
        + "but you can add e.g. Help between them. "
        + "\n\nFor more information on dialogs, please see the Nokia Asha design guidelines.",
        
        // CANVAS
        "There are different types of Canvas supported: "
        + "\n\n-With chrome; Header bar and BACK Command are included. "
        + "\n\n-With chrome and the CategoryBar; Header bar, CategoryBar and BACK Command are included."
        + "\n\n-With the status bar only; BACK Command is included to support the Hardware Back."
        + "\n\n-In full screen, without any chrome; BACK Command is included to support the Hardware Back. "
        + "In addition there is a special object for editing text "
        + "\n-Canvas TextEditor"
        + "\n\nFor more information on Canvas, please see the Nokia Asha design guidelines.",
        
        // FORMS
        "Forms are the most versatile screen environment. "
        + "They are usually used for data input tasks. Therefore "
        + "they offer a wide variety of types of items and "
        + "ready-made choice groups. There should always be a Command available "
        + "for saving the changes. Cancelling should be done via the hardware back key."

        + "\n\n- Progress bar and slider "
        + "\n\nThese elements originate from the Gauge element. They can be  "
        + "\n- determinate progress indicators, used as filling horizontal bars; "
        + "these should be used if the process duration is predictable or if it "
        + "takes longer than 10 seconds"
        + "\n- indeterminate progress indicators, used as spinners; these should "
        + "only be used if the process length is not predictable but definitely less than 10 seconds."
        + "\n- sliders, used to set a value within a number range. The numbers are integers."

        + "\n\n- Exclusive pop-up group"
        + "\n\nTapping on the item opens a pop-up list. If there are more than 4 items in "
        + "the pop-up group, the group\'s content becomes scrollable. Once an item is selected, "
        + "the pop-up closes."

        + "\n\n- Tumbler"
        + "\n\nA tumbler is used to select the time and/or date."
        + "\n- time only"
        + "\n- date only"
        + "\n- combination of time and date"
        + "\n- please note that no other tumbler content is possible"

        + "\n\n- Text input"
        + "\n\nIt is recommended that you do not \"preload\" the text field. The text field "
        + "header should contain all the relevant information for the text input field. "
        + "Please take care to use the right keyboard layout when opening the text input "
        + "field and when continuing with the text input."

        + "\n\n- Exclusive choice group (select)"
        + "\n\nThis is a set of items with an optional header and optional thumbnails. "
        + "Exactly one item must always be selected; the selected item is indicated with a highlight. "
        + "If there is the chance that no elements could be selected, add a \"no selection\" "
        + "option to the group."

        + "\n\n- Multiple choice group (select)"
        + "\n\nThis is a set of items with an optional header and optional thumbnails. "
        + "It is possible to select no items, one item or multiple items. Selected items "
        + "are indicated with a highlight."

        + "\n\n- Text string "
        + "\n\nThis offers the possibility to either "
        + "\n- display text, or "
        + "\n- create buttons and hyperlinks."
        + "\n\nIt is possible to give a label to each text string."

        + "\n\n- Image"
        + "\n\nThe image item allows images to be added into a form, e.g. to make the content "
        + "look more appealing, or to associate images with data input tasks."

        + "\n\n- Spacer"
        + "\n\nThis element allows components to be stretched."

        + "\n\n- Custom"
        + "\n\nIt is possible to create your own elements in a form screen and therefore "
        + "add custom elements in a feasible way to an existing palette of form items and groups. "
        + "The Custom item demonstrates 2 different examples:"
        + "\n\n1. Slider which allows, e.g. a start value different than 0 (1 in case of the example) "
        + "or which allows different numerical systems than the decimal numeral system – "
        + "for example HH:MM:SS to build a seek bar for a media player."
        + "\n\n2. Switch which allows users to toggle between on/off states "
        + "(or other clearly opposite states). "

        + "\n\nFor more information on forms, please see the Nokia Asha design guidelines.",

        
        // CATEGORY BAR
        "Category bar supports two modes:"
        + "\n\n- Tab mode; used for parallel view navigation. Currently "
        + "active tab is highlighted."
        + "\n\n- Toolbar mode; used for presenting frequently used, important "
        + "actions. The maximum number of items in the Category bar is four. "
        + "\n\nFor more information on the category bar, please see the Nokia "
        + "Asha design guidelines.",
        
        // TICKER
        "This application shows how the ticker appears in different display types,"
        + "\n\n- lists"
        + "\n- canvases"
        + "\n- text boxes"
        + "\n- form"
        + "\n\nThe ticker appears as part of the chrome, and does not scroll away with the content."
        + "\n\nFor more information on tickers, please see the Nokia Asha design guidelines.",
       
        
        // MENUS
        "This application demonstrates options menu and context menus. \n\n"
        + "Options menu is created by adding Commands to a Form. The highest "
        + "priority Command will be displayed as a button while the "
        + "lower priority Commands will be available in the menu.\n\n"
        + "You can create a context menu in two different ways, "
        + "\n\n- By adding Commands to a Form Item. The Commands are displayed in "
        + "a popup menu when long-pressing the Item."
        + "\n\n- By creating and showing a PopupList component. This is useful "
        + "when you need to explicitly control when to show/hide the menu. "
        + "You can use PopupList e.g. for showing context menu for a selection on a "
        + "Canvas."
    };

    public InformationView(int item, CommandListener commandListener) {
        super(Strings.getTitle(item));
        append(INFORMATION_TEXTS[item]);
        this.addCommand(Commands.INFORMATION_BACK);
        this.setCommandListener(commandListener);
    }
}
