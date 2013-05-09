/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.utils;

/**
 * DelayedTask is an abstract class for running tasks after a delay.
 */
public abstract class DelayedTask {

    private final int delay;
    private long runTime;
    private final Thread thread;

    /**
     * Constructor.
     * @param delay 
     */
    public DelayedTask(int delay) {
        this.delay = delay;
        thread = new Thread() {

            public void run() {
                boolean running = true;
                while (running) {
                    long dt = runTime - System.currentTimeMillis();
                    if (dt < 1) {
                        dt = 1;
                    }
                    try {
                        sleep(dt);
                        if (runTime <= System.currentTimeMillis()) {
                            running = false;
                            DelayedTask.this.run();
                        }
                    }
                    catch (InterruptedException e) {
                    }
                }
            }
        };
    }

    /**
     * Start a counter. The task is run when the counter reaches the delay.
     */
    public final void start() {
        resetDelay();
        thread.start();
    }

    /**
     * Reset the counter.
     */
    public void resetDelay() {
        runTime = System.currentTimeMillis() + delay;
    }

    /**
     * The run method is called when the counter reaches the delay. 
     * Subclasses need to implement this method.
     */
    public abstract void run();
}
