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

    public static final String ADD = "/add_icon.png";
    public static final String ICON = "/thumbnail_1.png";
    public static final String THUMBNAIL = "/thumbnail_";
    public static final String THUMBNAIL_END = ".png";
    public static final String PICTURE = "/image.jpg";
    public static final String DOWNLOAD = "/dialog_download.png";
    public static final String INSTALL = "/dialog_install.png";
    public static final String INFO = "/dialog_info.png";
    public static final String FORM = "/list_form.png";
    public static final String CATEGORY_1 = "/categorybar_1.png";
    public static final String CATEGORY_2 = "/categorybar_2.png";
    public static final String CATEGORY_SLIDER = "/categorybar_slider.png";
    public static final String CATEGORY_EXCLUSIVE_POPUP =
        "/categorybar_exclusive.png";
    public static final String CATEGORY_TUMBLER = "/categorybar_tumbler.png";
    public static final String CATEGORY_EXCLUSIVESELECT =
        "/categorybar_exclusiveselect.png";
    public static final String CATEGORY_INPUT = "/categorybar_input.png";
    public static final String CATEGORY_MULTIPLE = "/categorybar_multiple.png";
    public static final String CATEGORY_TEXTITEM = "/categorybar_text.png";
    public static final String CATEGORY_IMAGE = "/categorybar_image.png";
    public static final String CATEGORY_SPACER = "/categorybar_spacer.png";
    public static final String CATEGORY_CUSTOM = "/categorybar_custom.png";
    public static final String CATEGORY_SLIDER_EXTEND =
        "/categoryextend_slider.png";
    public static final String CATEGORY_EXCLUSIVE_POPUP_EXTEND =
        "/categoryextend_popup.png";
    public static final String CATEGORY_EXCLUSIVE_EXTEND =
        "/categoryextend_exclusive.png";
    public static final String CATEGORY_TUMBLER_EXTEND =
        "/categoryextend_tumbler.png";
    public static final String CATEGORY_INPUT_EXTEND =
        "/categoryextend_input.png";
    public static final String CATEGORY_MULTIPLE_EXTEND =
        "/categoryextend_multiple.png";
    public static final String CATEGORY_TEXT_EXTEND =
        "/categoryextend_text.png";
    public static final String CATEGORY_IMAGE_EXTEND =
        "/categoryextend_image.png";
    public static final String CATEGORY_SPACER_EXTEND =
        "/categoryextend_spacer.png";
    public static final String CATEGORY_CUSTOM_EXTEND =
        "/categoryextend_custom.png";
    public static final String CATEGORY_SEARCH = "/categorybar_search.png";
    public static final String CATEGORY_COMMENTS = "/categorybar_comments.png";
    public static final String CATEGORY_APPOINTMENTS =
        "/categorybar_appointments.png";
    public static final String CATEGORY_FAVOURITES =
        "/categorybar_favourites.png";
    public static final String CATEGORY_SEARCH_EXTEND =
        "/categoryextend_search.png";
    public static final String CATEGORY_COMMENTS_EXTEND =
        "/categoryextend_comments.png";
    public static final String CATEGORY_APPOINTMENTS_EXTEND =
        "/categoryextend_appointment.png";
    public static final String CATEGORY_FAVOURITES_EXTEND =
        "/categoryextend_favourites.png";
    public static final String CATEGORY_ALL_EXTEND =
        "/categoryextend_all.png";
    public static final String CATEGORY_DOWNLOADS_EXTEND =
        "/categoryextend_downloads.png";
    public static final String CATEGORY_CONTACTS_EXTEND =
        "/categoryextend_contacts.png";
    public static final String CATEGORY_PROFILE_EXTEND =
        "/categoryextend_profile.png";
    public static final String CATEGORY_EXPORTS_EXTEND =
        "/categoryextend_exports.png";
    public static final String CATEGORY_TAGS_EXTEND =
        "/categoryextend_tags.png";
    public static final String CATEGORY_ALERTS_EXTEND =
        "/categoryextend_alerts.png";
    public static final String CATEGORY_ARCHIVE_EXTEND =
        "/categoryextend_archive.png";
    public static final String CATEGORY_IDEAS_EXTEND =
        "/categoryextend_ideas.png";
    public static final String CATEGORY_SETTINGS_EXTEND =
        "/categoryextend_settings.png";
    public static final String CATEGORY_INFORMATION_EXTEND =
        "/categoryextend_information.png";
    public static final String CATEGORY_ALL =
        "/categorybar_all.png";
    public static final String CATEGORY_DOWNLOADS =
        "/categorybar_downloads.png";
    public static final String CATEGORY_CONTACTS =
        "/categorybar_contacts.png";
    public static final String CATEGORY_PROFILE =
        "/categorybar_profile.png";
    public static final String CATEGORY_EXPORTS =
        "/categorybar_exports.png";
    public static final String CATEGORY_TAGS =
        "/categorybar_tags.png";
    public static final String CATEGORY_ALERTS =
        "/categorybar_alerts.png";
    public static final String CATEGORY_ARCHIVE =
        "/categorybar_archive.png";
    public static final String CATEGORY_IDEAS =
        "/categorybar_ideas.png";
    public static final String CATEGORY_SETTINGS =
        "/categorybar_settings.png";
    public static final String CATEGORY_INFORMATION =
        "/categorybar_information.png";
    public static final String CATEGORY_LIST = "/categorybar_list.png";
    public static final String CATEGORY_CANVAS = "/categorybar_canvas.png";
    public static final String CATEGORY_TEXT = "/categorybar_text.png";
    public static final String CATEGORY_PINCH = "/categorybar_pinch.png";
    public static final String CATEGORY_TAP = "/categorybar_tap.png";
    public static final String BALL = "/ball.png";
    public static final String SLIDER_HANDLE = "/slider_handle.png";
    
    public static final String BUTTON_UP_LEFT_LEFT = "/button_left-left_up.png";
    public static final String BUTTON_UP_LEFT_RIGHT = "/button_left-right_up.png";
    public static final String BUTTON_UP_RIGHT_LEFT = "/button_right-left_up.png";
    public static final String BUTTON_UP_RIGHT_RIGHT = "/button_right-right_up.png";
    public static final String BUTTON_UP_CENTER = "/button_center_up.png";
    public static final String BUTTON_DOWN_LEFT_LEFT = "/button_left-left_down.png";
    public static final String BUTTON_DOWN_LEFT_RIGHT = "/button_left-right_down.png";
    public static final String BUTTON_DOWN_RIGHT_LEFT = "/button_right-left_down.png";
    public static final String BUTTON_DOWN_RIGHT_RIGHT = "/button_right-right_down.png";
    public static final String BUTTON_DOWN_CENTER = "/button_center_down.png";
    
    public static final String SEGMENTED_ACTIVE_LEFT_LEFT = "/segmented_left-left_active.png";
    public static final String SEGMENTED_ACTIVE_LEFT_RIGHT = "/segmented_left-right_active.png";
    public static final String SEGMENTED_ACTIVE_RIGHT_LEFT = "/segmented_right-left_active.png";
    public static final String SEGMENTED_ACTIVE_RIGHT_RIGHT = "/segmented_right-right_active.png";
    public static final String SEGMENTED_ACTIVE_CENTER = "/segmented_middle_active.png";
    
    public static final String SEGMENTED_INACTIVE_LEFT_LEFT = "/segmented_left-left_inactive.png";
    public static final String SEGMENTED_INACTIVE_LEFT_RIGHT = "/segmented_left-right_inactive.png";
    public static final String SEGMENTED_INACTIVE_RIGHT_LEFT = "/segmented_right-left_inactive.png";
    public static final String SEGMENTED_INACTIVE_RIGHT_RIGHT = "/segmented_right-right_inactive.png";
    public static final String SEGMENTED_INACTIVE_CENTER = "/segmented_middle_inactive.png";
    
    public static Image loadThumbnail(int index) {
        return load(THUMBNAIL + String.valueOf(index + 1) + THUMBNAIL_END);
    }

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
