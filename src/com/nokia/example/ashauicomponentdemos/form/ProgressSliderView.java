/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.form;

import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Gauge;

/**
 * Demonstrates the different use cases of the Gauge class
 */
public class ProgressSliderView
    extends FormExampleView {

    private Gauge slider;
    private int sliderPosition;

    public ProgressSliderView(CommandListener commandListener, String title) {
        super(title, commandListener);

        // Create a determinate progress indicator. Needs to be final
        // in order to be able to use from a Timer
        final Gauge determinateGauge = new Gauge(
            "Determinate progress indicator",
            false,
            10,
            0);
        this.append(determinateGauge);

        // Up the determinate indicator's value once a second, 
        // looping from 0 to max
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                if (determinateGauge.getValue()
                    == determinateGauge.getMaxValue()) {
                    determinateGauge.setValue(0);
                }
                else {
                    determinateGauge.setValue(determinateGauge.getValue() + 1);
                }
            }
        }, 0, 1000);

        // Create an indeterminate progress indicator
        Gauge gauge = new Gauge(
            "Indeterminate progress indicator",
            false,
            Gauge.INDEFINITE,
            Gauge.CONTINUOUS_RUNNING);
        this.append(gauge);

        // Create a slider that the user can slide
        slider =
            new Gauge("Slider", true, 100, 0);
        this.append(slider);
    }

    protected void storeCurrentValues() {
        sliderPosition = slider.getValue();
    }

    protected void cancelChanges() {
        slider.setValue(sliderPosition);
    }
}
