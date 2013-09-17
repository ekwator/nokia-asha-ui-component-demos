Asha UI Component Demos
=======================

This Java ME application is a MIDlet suite consisting of demos that show how to
use LCDUI components on Nokia Asha software platform 1.0. Designers get an 
impression of how the components actually look like on the phone, and 
developers can see how the UI components are used in an application by 
examining the source code. The various examples are implemented as independent 
MIDlets.

The MIDlet suite contains examples of the following components:

* Lists, demonstrating various List types. In some lists custom implementation
  is used to provide improved support for softkeys and Action button 1.
* Text, demonstrating TextField.
* Dialogs, demonstrating different kinds of Alerts.
* Canvas, demonstrating different ways of presenting the Canvas.
* Form, demonstrating different Form items, including CustomItem components.
* Categories, demonstrating the CategoryBar with different amounts of 
  categories. In touch and type and non-touch devices, an additional view has 
  been implemented to show categories instead of the category bar.
* Ticker, demonstrating the Ticker item in Displayables.
* Confirmation, demonstrating the confirmation process.
* Empty content, demonstrating the ways to handle situations where a view does
  not have any real content yet.
* Multiple items, demonstrating multiselection for positive selection or for 
  deletion of items.
* Zoom, demonstrating different ways of zooming images.
* Add new, demonstrating how to generate and display new Form Items on the fly.
* Interdependent, demonstrating how a Form can dynamically adjust itself based
  on user input.
* Keypads, demonstrating different keypad layouts available for different text
  input needs.
* Menus, demonstrating how to use context menus and action #2 menu items.

This example application demonstrates:

* Different UI components and patterns that can be used in Nokia Asha phones.
* How to accommodate different input methods in an application.

The application is hosted in GitHub:
https://github.com/nokia-developer/nokia-asha-ui-component-demos

For more information on the implementation, visit Java Developer's Library:
http://developer.nokia.com/Resources/Library/Java/#!developers-guides/ui-graphics-and-interaction/example-asha-ui-component-demos.html


1. Prerequisites
-------------------------------------------------------------------------------

* Java ME basics


2. Design considerations
-------------------------------------------------------------------------------

This example application was created to demonstrate the UI components and does
not have any actual engine implementation.


3. Known issues
------------------------------------------------------------------------------

In Nokia Asha software platform 1.0, MIDlet icons are distorted in the 
MIDletSuite main view.


4. Build and installation instructions
-------------------------------------------------------------------------------

The example has been created with Nokia Asha SDK 1.0. To open the project in 
the SDK, select File -> New -> MIDlet Project. Insert the project name, e.g. 
"AshaUIComponentDemos". Then select "Create project from existing source” and 
browse to the folder you have the project in. Click Finish. To run in the 
emulator, select “Run As | Emulated Java ME MIDlet”. You can either select any
of the MIDlets, or you can run the MIDletSuite by pressing OK.

You can install the application on a phone by transferring the .jar file in
Mass storage mode, with Nokia Suite or over Bluetooth. Locate the file with
file browser and tap to install. Then find the application icon in the
application menu and tap it to launch.


5. Compatibility
-------------------------------------------------------------------------------

Nokia Asha software platform 1.0. Tested on Nokia Asha 501. Developed with
Nokia Asha SDK 1.0.


6. Version history
-------------------------------------------------------------------------------

* 1.2 Minor modifications to the StringItems example. Published in the Nokia
  Asha SDK 1.0 and on Nokia Developer Web site.
* 1.1 Added Menus example
* 1.0.1 Updated icons
* 1.0 Initial release.
