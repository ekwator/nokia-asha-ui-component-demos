/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.ticker;

import com.nokia.example.ashauicomponentdemos.utils.Commands;
import com.nokia.example.ashauicomponentdemos.utils.ImageLoader;

import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.Ticker;

/**
 * Ticker on Form
 */
public class TickerFormView
    extends Form {

    /**
     * Constructs the form and adds a Ticker to the view
     *
     * @param commandListener
     */
    public TickerFormView(String title, CommandListener commandListener) {
        super(title);
        Ticker ticker = new Ticker("ticker text just ticking by... lorem ipsum dolor sit amet");
        this.setTicker(ticker);

        // Load the image
        Image image = ImageLoader.load(ImageLoader.PICTURE);

        // If the image was loaded successfully, create and show the ImageItem
        if (image != null) {
            ImageItem imageItem = new ImageItem("Image", image,
                Item.LAYOUT_DEFAULT,
                "Test image");
            // Center the image
            imageItem.setLayout(Item.LAYOUT_EXPAND | Item.LAYOUT_CENTER);
            this.append(imageItem);
        }

        this.addCommand(Commands.INFORMATION);
        this.setCommandListener(commandListener);
    }
}
