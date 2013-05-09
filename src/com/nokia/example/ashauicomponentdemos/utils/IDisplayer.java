/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.ashauicomponentdemos.utils;

import javax.microedition.lcdui.Displayable;

/**
 * A simple interface to allow parent views set themselves as the Displayers
 * for their child views.
 */
public interface IDisplayer {

    public void setCurrent(Displayable displayable);
}
