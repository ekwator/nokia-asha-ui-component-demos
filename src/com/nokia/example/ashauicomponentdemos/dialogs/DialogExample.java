/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.dialogs;

import com.nokia.example.ashauicomponentdemos.utils.BackStack;
import com.nokia.example.ashauicomponentdemos.utils.Commands;
import com.nokia.example.ashauicomponentdemos.utils.ImageLoader;
import com.nokia.example.ashauicomponentdemos.utils.InformationView;
import com.nokia.example.ashauicomponentdemos.utils.Strings;
import com.nokia.uihelpers.Compatibility;
import com.nokia.uihelpers.orientation.Orientation;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

/**
 * An example demonstrating various types of modal dialogs created using LCDUI
 * Alert component.
 */
public class DialogExample
    extends MIDlet
    implements CommandListener {

    private final String[] DIALOG_ITEMS = 
        new String[]{
            "Timed",
            "Text, 1 button",
            "Text, 1 button, image",
            "Text, btn, img, prog bar",
            "Text, btn, img, wait bar",
            "Text, 2 buttons",
            "Txt, 3 btns, img, wait bar",
            "Alert - error",
            "Alert - warning",
            "Alert - info",
            "Alert - confirmation",
            "Alert - alarm"
        };
    
    private final String[] TITLES = new String[]{
            "Timed dialog",
            "Information 1",
            "Information 2",
            "Download 1",
            "Download 2",
            "Confirmation",
            "Installation",
            "Alert - error",
            "Alert - warning",
            "Alert - info",
            "Alert - confirmation",
            "Alert - alarm"
        };
    
    private final String[] MESSAGES = {
        "Alert times out after 3 seconds",
        Commands.ALERT_OK.getLabel() + " closes the note and continues",
        Commands.ALERT_OK.getLabel() + " closes the note and continues",
        "Determinate progress indicator",
        "In-determinate progress indicator",
        Commands.ALERT_OK.getLabel() + " closes the note and continues, "
        + Commands.ALERT_CANCEL.getLabel() + " aborts the action",
        Commands.ALERT_HELP.getLabel() + " provides more information",
        "By default, time out after 5 seconds",
        "By default, time out after 5 seconds",
        "By default, time out after 5 seconds",
        "By default, time out after 5 seconds",
        "By default, time out after 5 seconds"
    };
    
    private final int TIMED = 0;
    private final int TEXT_1_BUTTON = 1;
    private final int TEXT_IMAGE_1_BUTTON = 2;
    private final int TEXT_IMAGE_DETERMINATE_1_BUTTON = 3;
    private final int TEXT_IMAGE_INDETERMINATE_1_BUTTON = 4;
    private final int TEXT_2_BUTTONS = 5;
    private final int TEXT_IMAGE_INDETERMINATE_3_BUTTONS = 6;
    private final int ALERT_ERROR = 7;
    private final int ALERT_WARNING = 8;
    private final int ALERT_INFO = 9;
    private final int ALERT_CONFIRMATION = 10;
    private final int ALERT_ALARM = 11;
    
    private Alert alert;
    private List dialogList;
    private Image downloadImage;
    private Image installImage;
    private Image infoImage;
    private BackStack backStack;
    private Timer timer;

    // Screen commands to showcase the correct order of commands in UX perspective.
    private final Command screenOkCommand = new Command(
            Compatibility.toUpperCaseIfFT("OK"), Command.SCREEN, 3);
    private final Command screenHelpCommand = new Command(
            Compatibility.toUpperCaseIfFT("Help"), Command.SCREEN, 2);
    private final Command screenCancelCommand = new Command(
            Compatibility.toUpperCaseIfFT("Cancel"), Command.SCREEN, 1);

    /**
     * Start the app, create and show the initial List view,
     * setup listeners and enable orientation support
     */
    public void startApp() {
        dialogList = new List(Strings.getTitle(Strings.DIALOGS),
            List.IMPLICIT, DIALOG_ITEMS, null);
        dialogList.setCommandListener(this);
        dialogList.setSelectCommand(List.SELECT_COMMAND);
        dialogList.addCommand(Commands.BACK);
        dialogList.addCommand(Commands.INFORMATION);
        dialogList.setFitPolicy(List.TEXT_WRAP_ON);

        downloadImage = ImageLoader.load(ImageLoader.DIALOG_DOWNLOAD);
        installImage = ImageLoader.load(ImageLoader.DIALOG_INSTALL);
        infoImage = ImageLoader.load(ImageLoader.DIALOG_INFO);
        backStack = new BackStack(this);

        Orientation.enableOrientations();
        Display.getDisplay(this).setCurrent(dialogList);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (timer != null) {
            timer.cancel();
        }
        if (c == List.SELECT_COMMAND) {
            int index = dialogList.getSelectedIndex();
            displayDialog(index);
        }
        else if (c == Commands.INFORMATION) {
            backStack.forward(new InformationView(Strings.DIALOGS, this));
        }
        else {
            // everything else takes back.
            backStack.back();
        }
    }

    /**
     * Demonstrates various kinds of Alerts
     * @param type 
     */
    private void displayDialog(int type) {
        alert = new Alert(TITLES[type]);
        alert.setString(MESSAGES[type]);

        final Gauge gauge;

        switch (type) {
            default:
            case TIMED:
                // Generates an alert that times out after 3 seconds
                // or when the user taps outside of the alert
                alert.setTimeout(3000);
                break;
            case TEXT_1_BUTTON:
                alert.addCommand(Commands.ALERT_OK);
                break;
            case TEXT_IMAGE_1_BUTTON:
                alert.setImage(infoImage);
                alert.addCommand(Commands.ALERT_OK);
                alert.setType(AlertType.INFO);
                break;
            case TEXT_IMAGE_DETERMINATE_1_BUTTON:
                // Creates an alert with a determinate gauge
                // The alert is dismissed when the gauge reaches finish 
                // (10 seconds) or Cancel is pressed
                gauge = new Gauge(null, false, 10, 0);
                alert.setIndicator(gauge);
                alert.setImage(downloadImage);
                timer = new Timer();
                // A timer handles the gauge
                timer.schedule(new TimerTask() {

                    public void run() {
                        if (gauge.getValue() == gauge.getMaxValue()) {
                            backStack.back();
                            this.cancel();
                        }
                        else {
                            gauge.setValue(gauge.getValue() + 1);
                        }
                    }
                }, 0, 1000);
                alert.addCommand(Commands.ALERT_CANCEL);
                alert.setType(AlertType.INFO);
                break;
            case TEXT_IMAGE_INDETERMINATE_1_BUTTON:
                // Creates an alert with an indeterminate gauge
                gauge = new Gauge(null, false, Gauge.INDEFINITE,
                    Gauge.CONTINUOUS_RUNNING);
                alert.setIndicator(gauge);
                alert.setImage(downloadImage);
                alert.addCommand(Commands.ALERT_CANCEL);
                alert.setType(AlertType.INFO);
                break;
            case TEXT_2_BUTTONS:
                alert.addCommand(Commands.ALERT_OK);
                alert.addCommand(Commands.ALERT_CANCEL);
                alert.setType(AlertType.CONFIRMATION);
                break;
            case TEXT_IMAGE_INDETERMINATE_3_BUTTONS:
                // Creates an alert with an indeterminate gauge and
                // three buttons
                gauge = new Gauge(null, false, Gauge.INDEFINITE,
                    Gauge.CONTINUOUS_RUNNING);
                alert.setIndicator(gauge);
                alert.setImage(installImage);
                alert.addCommand(screenOkCommand);
                alert.addCommand(screenHelpCommand);
                alert.addCommand(screenCancelCommand);
                break;
            case ALERT_ERROR:
                alert.setType(AlertType.ERROR);
                break;
            case ALERT_WARNING:
                alert.setType(AlertType.WARNING);
                break;
            case ALERT_INFO:
                alert.setType(AlertType.INFO);
                break;
            case ALERT_CONFIRMATION:
                alert.setType(AlertType.CONFIRMATION);
                break;
            case ALERT_ALARM:
                alert.setType(AlertType.ALARM);
                break;
        }
        alert.addCommand(Commands.BACK);

        // For all other Alerts than TIMED and ALERT_ERROR, _WARNING, _INFO,
        // _CONFIRMATION, _ALARM, set the automatic timeout to forever
        if (type != TIMED && type < ALERT_ERROR) {
            alert.setTimeout(Alert.FOREVER);
        }

        // Back command isn't called when pressing back from alert, so no
        // backStack forwarding here.
        Display.getDisplay(this).setCurrent(alert);
    }
}
