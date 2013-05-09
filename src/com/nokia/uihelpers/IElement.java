/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/

package com.nokia.uihelpers;

import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

/**
 * Interface for interactive element with text and image.
 */
public interface IElement {
    public Image getImagePart();
    public String getStringPart();
    public boolean isSelected();
    public void setSelected(boolean selected);
    public int getHeight();
    public Font getFont();
    public String getTruncatedText();
    public String getTruncatedText(int textWidth);
    public Vector getWrappedText();
    public void resetCaches();
    public boolean isSeparator();
}
