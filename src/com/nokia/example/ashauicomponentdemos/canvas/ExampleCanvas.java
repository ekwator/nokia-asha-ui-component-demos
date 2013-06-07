/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.canvas;

import com.nokia.mid.ui.LCDUIUtil;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Graphics;

/**
 * The ExampleCanvas changes it's appearance and elements based 
 * on which example is shown. The customization is made by calling
 * various methods (e.g. setFullScreen for fullscreen examples) in
 * CanvasExample.java.
 */
public class ExampleCanvas
    extends Canvas {

    private final String BACK_HINT = "(Press Back to continue)";
    private String content;

    /**
     * Constructor
     * @param title
     * @param content 
     */
    public ExampleCanvas(String title, String content) {
        this.content = content;
        this.setTitle(title);
    }

    /**
     * Constructor
     * @param title
     * @param content
     * @param parent Command handler to which button commends are delegated
     */
    public ExampleCanvas(String title, String content, CommandListener parent) {
        this(title, content);
    }

    /**
     * Also hints if this is statusbar only-example or fullscreen -example.
     * @param fullScreen 
     */
    public void setFullScreen(boolean fullScreen) {
        this.setFullScreenMode(fullScreen);
    }

    /**
     * Shows or hides the status bar
     * @param visible 
     */
    public void setStatusBarVisible(boolean visible) {
        Boolean v = (visible ? Boolean.TRUE : Boolean.FALSE);
        LCDUIUtil.setObjectTrait(this, "nokia.ui.canvas.status_zone", v);
    }

    protected void sizeChanged(int width, int height) {
    }

    public void paint(Graphics g) {
        // Clear the screen by drawing a white rectangle
        int w = getWidth();
        int h = getHeight();
        int textY = this.getHeight() / 4;
        int backHintY = this.getHeight() / 2;
        g.setColor(0xf4f4f4); // white
        g.fillRect(0, 0, w, h);

        // Draw the text
        g.setColor(0, 51, 240); // blue
        g.drawString(content, w / 2, textY,
            Graphics.TOP | Graphics.HCENTER);

        g.drawString(BACK_HINT, w / 2, backHintY, Graphics.TOP | Graphics.HCENTER);
    }
}
