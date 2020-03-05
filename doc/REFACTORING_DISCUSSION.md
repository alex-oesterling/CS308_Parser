Dana Mulligan (dmm107)
Hannah Taubenfeld (hbt3)
Tyler Meier (tkm22)
Alex Oesterling (axo)

## REFACTORING_DISCUSSION.md
### **VIEW**
* Visualizer.java
    * line 195 - changed the entry set to be iterated when both the key and value are needed
    * line 344, 328, 360 - changed the way errors are handled by refactoring and creating a new method that includes the use of the error
    * line 382 - got rid of magic numbers

* ViewExternal.java
    * removed unused methods

* TurtleView.java
    * line 93, 95, 107, 109 - added a entire method for catching errors

* ToolBar.java
    * line 82, 192 - errors are work in progress
    * all private instance variables only used in a local scope (all menu items) became local variables
* PenProperties.java
    * Removed extraneous private instance variables and cleaned up boolean logic (return (boolean statement))

* ShapePallete.java
    * Removed extraneous private instance variables
    * Corrected constants to be static and final
    * Made scene and return statements more clean
        * Scene = (createNode())
        * Return new ...

* ColorPallete.java
    * Removed extraneous private instance variables
    * Corrected constants to be static and final
    * Made scene and return statements more clean
        * Scene = (createNode())
        * return new ...

* CommandLine.java
    * Cleaned up use of diamond operator <>
    
* HelpWindow.java
    * Removed extranous private instance variables
    * Corrected constants to be static and final

* MoveTurtle.java
    * Removed extranous private instance variables
    * Corrected constants to be static and final

Major Refactoring
* In Visualizer, we are extracting all the UI elements to helper classes that return the code. This way, the role of Visualizer becomes a main central "communicator" between classes and does not do any of the rendering itself

### **Model**
* Commands
    * Changed all commands to take in three parameters:
        * `List<Turtle>`
        * `List<Double>`
        * `List<List<Command>>`
* Controller
    * Removed if else statements on creating different constructors depending on the type of command created
    * added making lists of correct parameters

* Properties Files 
    * Changed properties files to make *more sense*, so that it reflects the number of things needed


Major Refactoring
    *These refactors allowed us to get rid of all of the if and else statements we were using in creating commands due to how many parameters each specific command has. Now, every command takes in the same amount of parameters which makes the code more clean and the properties files created more concrete in what they mean.