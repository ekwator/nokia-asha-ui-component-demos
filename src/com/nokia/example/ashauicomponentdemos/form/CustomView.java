/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.form;

import com.nokia.mid.ui.LCDUIUtil;
import com.nokia.uihelpers.Slider;
import com.nokia.uihelpers.Slider.SliderListener;
import com.nokia.uihelpers.Switch;
import com.nokia.uihelpers.Switch.SwitchListener;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.midlet.MIDlet;

/**
 * Demonstrates the usage of custom form elements
 */
public class CustomView
    extends FormExampleView
    implements SliderListener, SwitchListener {

    private int sliderValue = 0;
    private boolean switchValue = false;
    private Slider slider;
    private Switch aSwitch;

    public CustomView(int foregroundColor, int backgroundColor,
            CommandListener commandListener, MIDlet parent, String title) {
        super(title, commandListener);
        
        // The slider
        slider = new Slider(1, 30, "Slider", this);
        LCDUIUtil.setObjectTrait(slider,
            "nokia.ui.s40.item.direct_touch", Boolean.TRUE);
        slider.addListener(this);
        this.append(slider);
        
        // The switch
        aSwitch = new Switch("Switch",
                "Value is on",
                "Value is off",
                this);
        LCDUIUtil.setObjectTrait(aSwitch,
            "nokia.ui.s40.item.direct_touch", Boolean.TRUE);
        this.append(aSwitch);        
    }

    protected void storeCurrentValues() {
        sliderValue = slider.getValue();
        switchValue = aSwitch.getValue();
    }

    protected void cancelChanges() {
        slider.setValue(sliderValue);
        aSwitch.setValue(switchValue);
    }

    public void valueChanged(Slider slider, int value) {
    }

    public void valueChanged(Switch aSwitch, boolean value) {
    }
}
