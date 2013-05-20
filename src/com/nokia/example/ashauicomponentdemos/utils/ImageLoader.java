/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.utils;

import java.io.IOException;
import javax.microedition.lcdui.Image;

/**
 * A helper class for image loading
 */
public class ImageLoader {

    // misc graphics
    public static final String DIALOG_DOWNLOAD = "/legacy_dialog_download.png";
    public static final String DIALOG_INSTALL = "/legacy_dialog_install.png";
    public static final String DIALOG_INFO = "/legacy_dialog_info.png";
    public static final String ICON = "/thumbnail_1.png";
    public static final String PICTURE = "/image.jpg";
    public static final String THUMBNAIL = "/thumbnail_";
    public static final String THUMBNAIL_END = ".png";
    public static final String TICKER_BALL = "/ticker_ball.png";
       
    // graphics for a button
    public static final String BUTTON_UP_LEFT_LEFT =
            "/legacy_button_left-left_up.png";
    public static final String BUTTON_UP_LEFT_RIGHT =
            "/legacy_button_left-right_up.png";
    public static final String BUTTON_UP_RIGHT_LEFT =
            "/legacy_button_right-left_up.png";
    public static final String BUTTON_UP_RIGHT_RIGHT =
            "/legacy_button_right-right_up.png";
    public static final String BUTTON_UP_CENTER =
            "/legacy_button_center_up.png";
    public static final String BUTTON_DOWN_LEFT_LEFT =
            "/legacy_button_left-left_down.png";
    public static final String BUTTON_DOWN_LEFT_RIGHT =
            "/legacy_button_left-right_down.png";
    public static final String BUTTON_DOWN_RIGHT_LEFT =
            "/legacy_button_right-left_down.png";
    public static final String BUTTON_DOWN_RIGHT_RIGHT =
            "/legacy_button_right-right_down.png";
    public static final String BUTTON_DOWN_CENTER =
            "/legacy_button_center_down.png";
    
    // is this legacy that should be deleted?
    public static final String SEGMENTED_ACTIVE_LEFT_LEFT =
            "/legacy_segmented_left-left_active.png";
    public static final String SEGMENTED_ACTIVE_LEFT_RIGHT =
            "/legacy_segmented_left-right_active.png";
    public static final String SEGMENTED_ACTIVE_RIGHT_LEFT =
            "/legacy_segmented_right-left_active.png";
    public static final String SEGMENTED_ACTIVE_RIGHT_RIGHT =
            "/legacy_segmented_right-right_active.png";
    public static final String SEGMENTED_ACTIVE_CENTER =
            "/legacy_segmented_middle_active.png";
    public static final String SEGMENTED_INACTIVE_LEFT_LEFT =
            "/legacy_segmented_left-left_inactive.png";
    public static final String SEGMENTED_INACTIVE_LEFT_RIGHT =
            "/legacy_segmented_left-right_inactive.png";
    public static final String SEGMENTED_INACTIVE_RIGHT_LEFT =
            "/legacy_segmented_right-left_inactive.png";
    public static final String SEGMENTED_INACTIVE_RIGHT_RIGHT =
            "/legacy_segmented_right-right_inactive.png";
    public static final String SEGMENTED_INACTIVE_CENTER =
            "/legacy_segmented_middle_inactive.png";
    
    // graphics for a slider
    public static final String SLIDER_HANDLE = "/slider_handle_pressed.png";
    
    // graphics for a switch
    public static final String SWITCH_ON = "/legacy_checkbox_tick.png";
    public static final String SWITCH_OFF = "/legacy_checkbox_untick.png";
    
    // graphics for a toggle button of a custom list
    public static final String CUSTOMLIST_TOGGLE_DISABLED =
            "/customlist_toggle_disabled.png";
    public static final String CUSTOMLIST_TOGGLE_EXCLUSIVE_ACTIVE =
            "/customlist_toggle_exclusive_active.png";
    public static final String CUSTOMLIST_TOGGLE_EXCLUSIVE_INACTIVE =
            "/customlist_toggle_exclusive_inactive.png";
    public static final String CUSTOMLIST_TOGGLE_MULTIPLE_ACTIVE =
            "/customlist_toggle_multiple_active.png";
    public static final String CUSTOMLIST_TOGGLE_MULTIPLE_INACTIVE =
            "/customlist_toggle_multiple_inactive.png";
    
    // all the icons for a category bar
    public static final String CATEGORYBAR_1 = "/categorybar_1.png";
    public static final String CATEGORYBAR_2 = "/categorybar_2.png"; 
    public static final String CATEGORYBAR_CANVAS = "/categorybar_canvas.png";
    public static final String CATEGORYBAR_DOWNLOADS = "/categorybar_downloads.png";   
    public static final String CATEGORYBAR_FORM = "/categorybar_form.png";
    public static final String CATEGORYBAR_LIST = "/categorybar_list.png";
    public static final String CATEGORYBAR_PINCH = "/categorybar_pinch.png";  
    public static final String CATEGORYBAR_SLIDER = "/categorybar_slider.png";
    public static final String CATEGORYBAR_TAP = "/categorybar_tap.png";
    public static final String CATEGORYBAR_TEXT = "/categorybar_text.png";
    
    // the icons used in the list of form examples
    public static final String LIST_POPUP = "/list_popup.png";
    public static final String LIST_SLIDER = "/list_slider.png";
    public static final String LIST_TUMBLER = "/list_tumbler.png";
    public static final String LIST_TEXT_INPUT = "/list_input.png";
    public static final String LIST_EXCLUSIVE_SELECT = "/legacy_form_exclusive.png";
    public static final String LIST_MULTIPLE_SELECT = "/legacy_form_multiple.png";
    public static final String LIST_TEXT_ITEM = "/list_text.png";
    public static final String LIST_IMAGE = "/list_image.png";
    public static final String LIST_SPACER = "/list_spacer.png";
    public static final String LIST_CUSTOM = "/list_custom.png";
    
    /**
     * Loads indexed thumbnail graphics into an Image
     * @param index
     * @return Image
     */
    public static Image loadThumbnail(int index) {
        return load(THUMBNAIL + String.valueOf(index + 1) + THUMBNAIL_END);
    }

    /**
     * Loads a file into an Image
     * @param file
     * @return Image
     */
    public static Image load(String file) {
        Image image = null;
        try {
            image = Image.createImage(file);
        }
        catch (NullPointerException npe) {
            System.out.println("No image file name specified");
        }
        catch (IOException ioe) {
            System.out.println("Image not found or invalid: " + file);
        }
        return image;
    }
}
