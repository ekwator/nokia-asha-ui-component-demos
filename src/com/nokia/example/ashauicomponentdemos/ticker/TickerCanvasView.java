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
import com.nokia.mid.ui.VirtualKeyboard;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Ticker;
import javax.microedition.lcdui.game.GameCanvas;

/**
 * Ticker on GameCanvas
 */
public class TickerCanvasView
    extends GameCanvas
    implements Runnable {

    private final int WIDTH = 40;
    private final int HEIGHT = 40;
    private final int REFRESH_INTERVAL = 10;
    private int x = 0;
    private int y = 0;
    private int xDir = 1;
    private int yDir = 2;
    private boolean animate = false;
    private Graphics graphics;
    private Image ball;

    /**
     * Constructs the canvas and adds a Ticker to the view
     * @param commandListener 
     */
    public TickerCanvasView(String title, CommandListener commandListener) {
        super(true);
        Ticker ticker = new Ticker("ticker text just ticking by... lorem ipsum dolor sit amet");
        this.setTicker(ticker);
        this.setTitle(title);

        ball = ImageLoader.load(ImageLoader.BALL);

        this.addCommand(Commands.INFORMATION);
        this.setCommandListener(commandListener);
        try {
            Class.forName("com.nokia.mid.ui.VirtualKeyboard");
            VirtualKeyboard.hideOpenKeypadCommand(true);
        }
        catch (ClassNotFoundException e) {
        }
    }

    /**
     * Start the animation when the view is on screen
     */
    protected void showNotify() {
        animate = true;
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * Pause the animation when the view goes offscreen
     */
    protected void hideNotify() {
        animate = false;
    }

    /**
     * Called when switching orientation
     * @param width
     * @param height 
     */
    protected void sizeChanged(int width, int height) {
        // Reset ball coordinates and direction
        x = y = 0;
        xDir = 1;
        yDir = 2;
        // The Graphics -object needs to be refreshed after an
        // orientation change
        graphics = getGraphics();
    }

    public void run() {
        graphics = getGraphics();

        // Bounce a ball across the screen
        while (animate) {
            boolean goingOverLeftEdge = x + xDir + WIDTH >= this.getWidth();
            boolean goingOverRightEdge = x + xDir <= 0;
            boolean goingOverBottomEdge = y + yDir + HEIGHT >= this.getHeight();
            boolean goingOverTopEdge = y + yDir <= 0;

            if (goingOverLeftEdge || goingOverRightEdge) {
                xDir = -xDir;
            }
            if (goingOverBottomEdge || goingOverTopEdge) {
                yDir = -yDir;
            }
            x += xDir;
            y += yDir;
            render(graphics);
            flushGraphics();

            try {
                Thread.sleep(REFRESH_INTERVAL);
            }
            catch (InterruptedException ie) {
            }
        }
    }

    public void render(Graphics g) {
        // Clear the screen
        g.setColor(0, 255, 255);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Draw the ball
        g.setColor(0, 0, 255);
        g.drawImage(ball, x, y, Graphics.TOP | Graphics.LEFT);
    }
}
