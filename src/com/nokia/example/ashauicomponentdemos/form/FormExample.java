/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.form;

import com.nokia.example.ashauicomponentdemos.utils.BackStack;
import com.nokia.example.ashauicomponentdemos.utils.Commands;
import com.nokia.example.ashauicomponentdemos.utils.ImageLoader;
import com.nokia.example.ashauicomponentdemos.utils.InformationView;
import com.nokia.example.ashauicomponentdemos.utils.Strings;
import com.nokia.uihelpers.orientation.Orientation;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * An example demonstrating different Form components in LCDUI. 
 */
public class FormExample
    extends MIDlet
    implements CommandListener {

    private final int PROGRESS = 0;
    private final int EXCLUSIVE_POPUP = 1;
    private final int TUMBLR = 2;
    private final int TEXT_INPUT = 3;
    private final int EXCLUSIVE = 4;
    private final int MULTIPLE = 5;
    private final int TEXT_STRING = 6;
    private final int IMAGE = 7;
    private final int SPACER = 8;
    private final int CUSTOM = 9;
    private final String[] TITLES = new String[]{
                "Progress + slider",
                "Exclusive popup",
                "Time & date",
                "Text input",
                "Exclusive select",
                "Multiple select",
                "Text string",
                "Image",
                "Spacer",
                "Custom"
            };
    private BackStack backStack;
    private List list;
    private FormExampleView formExampleView;
    // Store all of the demo views as instance variables
    private ProgressSliderView progressSliderView;
    private ExclusivePopupView exclusivePopupView;
    private TumblerView tumblerView;
    private TextInputView textInputView;
    private ExclusiveSelectView exclusiveSelectView;
    private MultiSelectView multiSelectView;
    private StringItemView stringItemView;
    private ImageView imageView;
    private SpacerView spacerView;
    private CustomView customView;

    /**
     * Start the app, create the example List
     * @throws MIDletStateChangeException
     */
    protected void startApp()
        throws MIDletStateChangeException {
        Image sliderExtend = ImageLoader.load(ImageLoader.CATEGORY_SLIDER);
        Image exclusivePopupExtend = ImageLoader.load(
            ImageLoader.CATEGORY_EXCLUSIVE_POPUP);
        Image tumblerExtend = ImageLoader.load(ImageLoader.CATEGORY_TUMBLER);
        Image textInputExtend = ImageLoader.load(ImageLoader.CATEGORY_INPUT);
        Image exclusiveExtend = ImageLoader.load(
            ImageLoader.CATEGORY_EXCLUSIVESELECT);
        Image multipleExtend = ImageLoader.load(ImageLoader.CATEGORY_MULTIPLE);
        Image textStringExtend = ImageLoader.load(ImageLoader.CATEGORY_TEXTITEM);
        Image imageExtend = ImageLoader.load(ImageLoader.CATEGORY_IMAGE);
        Image spacerExtend = ImageLoader.load(ImageLoader.CATEGORY_SPACER);
        Image customExtend = ImageLoader.load(ImageLoader.CATEGORY_CUSTOM);

        Image[] listImages = {
            sliderExtend,
            exclusivePopupExtend,
            tumblerExtend,
            textInputExtend,
            exclusiveExtend,
            multipleExtend,
            textStringExtend,
            imageExtend,
            spacerExtend,
            customExtend
        };
        int d = Math.min(
            Display.getDisplay(this).getBestImageHeight(Display.LIST_ELEMENT),
            Display.getDisplay(this).getBestImageWidth(Display.LIST_ELEMENT));
        if (d < 38) {
            for (int i = 0; i < listImages.length; i++) {
                listImages[i] = scaleImage(listImages[i], d, d);
            }
        }

        list = new List(Strings.getTitle(Strings.FORM), List.IMPLICIT, TITLES,
            listImages);
        list.setSelectCommand(List.SELECT_COMMAND);
        list.addCommand(Commands.BACK);
        list.addCommand(Commands.INFORMATION);
        list.setCommandListener(this);

        backStack = new BackStack(this);
        Orientation.enableOrientations();
        Display.getDisplay(this).setCurrent(list);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean a)
        throws MIDletStateChangeException {
    }

    /**
     * Lazily initialize and display the requested view
     * @param selectedIndex 
     */
    public void switchView(int selectedIndex) {
        formExampleView = null;

        switch (selectedIndex) {
            case PROGRESS:
                if (progressSliderView == null) {
                    progressSliderView = new ProgressSliderView(this,
                        TITLES[selectedIndex]);
                }
                formExampleView = progressSliderView;
                break;
            case EXCLUSIVE_POPUP:
                if (exclusivePopupView == null) {
                    exclusivePopupView = new ExclusivePopupView(this,
                        TITLES[selectedIndex]);
                }
                formExampleView = exclusivePopupView;
                break;
            case TUMBLR:
                if (tumblerView == null) {
                    tumblerView = new TumblerView(this,
                        TITLES[selectedIndex]);
                }
                formExampleView = tumblerView;
                break;
            case TEXT_INPUT:
                if (textInputView == null) {
                    textInputView = new TextInputView(this,
                        TITLES[selectedIndex]);
                }
                formExampleView = textInputView;
                break;
            case EXCLUSIVE:
                if (exclusiveSelectView == null) {
                    exclusiveSelectView = new ExclusiveSelectView(this,
                        TITLES[selectedIndex]);
                }
                formExampleView = exclusiveSelectView;
                break;
            case MULTIPLE:
                if (multiSelectView == null) {
                    multiSelectView = new MultiSelectView(this,
                        TITLES[selectedIndex]);
                }
                formExampleView = multiSelectView;
                break;
            case TEXT_STRING:
                if (stringItemView == null) {
                    stringItemView = new StringItemView(this,
                        TITLES[selectedIndex]);
                }
                formExampleView = stringItemView;
                break;
            case IMAGE:
                if (imageView == null) {
                    imageView = new ImageView(this,
                        TITLES[selectedIndex]);
                }
                formExampleView = imageView;
                break;
            case SPACER:
                if (spacerView == null) {
                    spacerView = new SpacerView(this,
                        TITLES[selectedIndex]);
                }
                formExampleView = spacerView;
                break;
            case CUSTOM:
                if (customView == null) {
                    Display display = Display.getDisplay(this);
                    customView = new CustomView(
                        display.getColor(
                        Display.COLOR_HIGHLIGHTED_FOREGROUND),
                        display.getColor(
                        Display.COLOR_HIGHLIGHTED_BACKGROUND),
                        this,
                        this,
                        TITLES[selectedIndex]);
                }
                formExampleView = customView;
                break;
            default:
                break;
        }
        if (formExampleView != null) {
            formExampleView.storeCurrentValues();
            backStack.forward(formExampleView);
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == List.SELECT_COMMAND) {
            switchView(list.getSelectedIndex());
        }
        else if (c == Commands.OK) {
            backStack.back();
        }
        else if (c == Commands.BACK) {
            if (formExampleView != null) {
                formExampleView.cancelChanges();
            }
            backStack.back();
        }
        else if (c == Commands.INFORMATION) {
            backStack.forward(new InformationView(Strings.FORM, this));
        }
        else if (c == Commands.INFORMATION_BACK) {
            backStack.back();
        }
    }

    /**
     * Scales an image.
     * @param original
     * @param newWidth
     * @param newHeight
     * @return new scaled image
     */
    private Image scaleImage(Image original, int newWidth, int newHeight) {
        int[] rawInput = new int[original.getHeight() * original.getWidth()];
        original.getRGB(rawInput, 0, original.getWidth(), 0, 0, original.
            getWidth(), original.getHeight());

        int[] rawOutput = new int[newWidth * newHeight];

        // YD compensates for the x loop by subtracting the width back out
        int YD = (original.getHeight() / newHeight) * original.getWidth()
            - original.getWidth();
        int YR = original.getHeight() % newHeight;
        int XD = original.getWidth() / newWidth;
        int XR = original.getWidth() % newWidth;
        int outOffset = 0;
        int inOffset = 0;

        for (int y = newHeight, YE = 0; y > 0; y--) {
            for (int x = newWidth, XE = 0; x > 0; x--) {
                rawOutput[outOffset++] = rawInput[inOffset];
                inOffset += XD;
                XE += XR;
                if (XE >= newWidth) {
                    XE -= newWidth;
                    inOffset++;
                }
            }
            inOffset += YD;
            YE += YR;
            if (YE >= newHeight) {
                YE -= newHeight;
                inOffset += original.getWidth();
            }
        }
        return Image.createRGBImage(rawOutput, newWidth, newHeight, true);
    }
}
