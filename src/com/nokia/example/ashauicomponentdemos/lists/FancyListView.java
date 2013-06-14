/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/

package com.nokia.example.ashauicomponentdemos.lists;

import com.nokia.example.ashauicomponentdemos.utils.Commands;
import com.nokia.example.ashauicomponentdemos.utils.ImageLoader;
import com.nokia.uihelpers.CustomListItem;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.midlet.MIDlet;

/**
 * This class displays a list with customized appearance. The list selection
 * is persisted using record management store (RMS).
 */
public class FancyListView
    extends Form
    implements CommandListener, ItemStateListener {
    
    private final int MAX_ITEMS = 10;
    private MIDlet parent;
    private CommandListener parentCommandListener;
    
    public FancyListView(MIDlet parent, CommandListener commandListener) {
        super("Fancy");
        
        this.parent = parent;
        int itemIndex = 1;
        for (int i = 0; i < MAX_ITEMS; i++) {
            if (i % 7 == 6) {
                append(new Separator());
            } else {
                append(
                        new FancyListItem(
                        "Title " + (itemIndex),
                        "Content " + (itemIndex),
                        "12:34",
                        ImageLoader.loadThumbnail(i),
                        i % (FancyListItem.IMPORTANCE_HIGH + 1)));
                itemIndex++;
            }
        }
        
        this.addCommand(Commands.BACK);
        this.setCommandListener(this);
        parentCommandListener = commandListener;
        
        this.setItemStateListener(this);
    }

    public void itemStateChanged(Item item) {
        if (item instanceof CustomListItem) {
            String text = ((CustomListItem)item).getText();          
            Alert selectedAlert = new Alert("Selected");
            selectedAlert.setString(
                    text +
                    " selected.");
            selectedAlert.addCommand(Commands.ALERT_CONTINUE);
            selectedAlert.addCommand(Commands.BACK);
            selectedAlert.setTimeout(Alert.FOREVER);
            selectedAlert.setCommandListener(this);
            Display.getDisplay(parent).setCurrent(selectedAlert);
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c.getCommandType() == Command.BACK) {
            parentCommandListener.commandAction(c, d);
        }
        else if (c == Commands.ALERT_CONTINUE) {
            // Handle alert continue command as Back command
            parentCommandListener.commandAction(Commands.BACK, d);
        }
    }
    
    private class Separator extends CustomItem {

        public Separator() {
            super("");
        }
        
        protected int getMinContentWidth() {
            return 0;
        }

        protected int getMinContentHeight() {
            return 1;
        }

        protected int getPrefContentWidth(int height) {
            return FancyListView.this.getWidth();
        }

        protected int getPrefContentHeight(int width) {
            return 1;
        }

        protected void paint(Graphics g, int w, int h) {
            g.setColor(0x000000);
            g.drawLine(w / 6, 0, w - w / 6, 0);
        }
    
    }
    
}
