# DESIGN_PLAN.md

# Names: 
Alex Oesterling (axo), Hannah Taubenfeld (hbt3), Tyler Meier (tkm22), and Dana Mulligan (dmm107)

# Introduction

* Goals:
    * We want the view and the model to be separate, where one side doesn't know how the code is structured in the other
    * The code will be the most flexible in the backend /command part where new commands are able to be easily added to the program. 
* Architecture: 
    * Closed: Parser, specific preset designs, 
    * Open: Specific turtle commands, basic view line creation
* Problem: Taking input commands from a user in a console and reflecting the commands given in a visual feedback
    * If a user types "fd 50" or "forward 50" the turtle on the screen moves forward 50 pixels

# Overview

[Resource we consulted](https://www2.cs.duke.edu/courses/spring20/compsci308/readings/mvc-hfdp.pdf)

* Model (Internal)
    * Purpose: All of the code dealing with what the commands will actually be doing, parsing the commands
    * Two implementations: 
        * Having a map for the commands which makes having multiple command entries correspond to one command easier to deal with
        * Using a bunch of conditional statements for each command (worse way)
    * Interaction with other APIs: It would not be working with the view API's directly, only with the model external API to let it know what to send.
* Model (External)
    * Purpose: Sending the turtle position, angle, whether the turtle is down or up (drawing/not drawing), color as well as having a public method to send a command to the parser
    * Two implementations:
        * Having a map that contains all of the information to be sent to the visual (such as position, angle, if its drawing or not, etc)
        * Having a bunch of getter and setter methods that are able to be accessed by view
    * Interaction with other APIs: Sends the commands to the external view to be drawn and added to the scene. 
* View  (Internal)
    * Purpose: setting scene, stage,  history of commands, typing a command, drawing the line
    * Two implementations: 
        * For drawing a line can get start point and end point and go directly to end point 
        * Can also plot a bunch of different points in between the start and end point and move at intervals
        * Having multiple windows for commands and turtle stuff compared to only having one window that contains everything for the scene and such
    * Interaction with other APIs: Receives commands from external view to be drawn and added to the scene and the history of commands. Does not interact with the model. 
* View  (External)
    * Purpose: Sending UI information back to the model (buttons, menus, etc) to update it.
    * Two implementations:
        * Using a controller to send information to the model, this would separate the graphical interface from the interpreter and let them communicate when necessary. This is. how the information from the UI will also get to the model if needed
        * Also use binding in order to reduce complications in the controller
        * Sends information directly to the model just using getter and setter methods
    * Interaction with other APIs: Interacting with the external model to let it know what to do based off of a click or command in the UI. Also allows the internal view API to know what to do/ draw after recieving information from the external model API

![Overview](Overview.png)

# User Interface

* We will have our main UI be on one window, with the main turtle and movements of it/drawings be in the middle or the main part of the screen, possibly in a BorderPane type of layout.
* On the side will be where you can type in a command and also where the history of commands pops up and keeps track of what you have already typed. 
* On the top will be the title
* In another window will be some buttons, such as new/reset, main menu, preset shapes, a help button/pulldown that tells you all of the possible commands that one could type, and preset shape buttons 
* Errors:
    * Invalid command (direction in wrong language, wrong spelling, nonexistent, invalid degree)
    * Out of bounds (sends turtle off the screen)
    * Empty data (no direction, no degree, missing information)
* We plan to have these errors appear as red alert text in the command line (like how the command line on your computer displays errors).
![UI](Turtle.png)

# Design Details

* Model External 
    * Features supported and how: Parses commands and generates command objects
        * Parser Class - takes in strings from command window 
        * Turtle Class - returns x and y position
    * Resources: The properties files in order to be able to parse the commands and add them to a map
    * Closed to modification from the user beyond switching languages
    * With every new command need to make a new command subclass 
    * Parser class can have a constructor that generates correct mappings and doesn't necessarily have to be extended. 
* Model Internal 
    * Features supported and how: Tracks the turtle and its states through the Turtle class. Creates command objects and interprets them. 
        * Turtle Class - Tracks movement and changes according to the command
        * Command Class - have an inheritance heirarchy where each child represents a unique command
    * Resources: The parsed commands and different languages
    * Throws errors dealing with invalid commands (wrong format, don't exist) and commands that send the turtle off the screen
    * The command objects will be immutable
    * Extendable: 
        * There is room for the turtle class to be extended but as of now this seems unnecessary
        * Add new child command classes as Command is going to be its own package, and Command Class will be superclass. 
    * All private and protected methods
* View Internal 
    * Features supported and how: Creates stages and sets scene through the Visualizer Class, Application Class.
        * Visualizer Class - creates lines, visually updates turtle position
        * Application Class - includes the setting of the stage and Main class 
    * Extendable: There is room for extension but as of now this seems unnecessary
    * Closed to modification (especially Application)
    * Resources: Any images needed to create the scene or stage. The current and future position of the turtle. Also, any styling files we might create.  
* View External 
    * Features supported and how: Takes commands and gives them to the Model through the Controller Class
        * Controller Class - deals with all of the actions that would interact the view external and the model external
    * Resources: none
    * Extendable: Different types of controllers could take different types of syntax. A controller that allows for program writing in the command line versus reading of programs (such as a txt file of commands) could both be extended. 
    * All public methods

# API as Code

 * Parser Class
     * Read Command (public)
         * Throws errors described above
         * reads commands from command window
     * Store Command (private)
         * stores these commands into its parts
     * Get Command (public)
         * gets certain command
 * Command Class (Super)
     * X Position (public)
     * Y Position (public)
     * Do Motion (public)
     * Command Subclasses (private, depend on what commands are created)
         * Inherit all of command superclass methods
         * Additional methods for each specific sub command class
 * Interpreter Class
     * Check command 
         * Decides if command goes to the Turtle class or if command goes to the view
 * Turtle Class
     * Turtle Position (public)
         * stores current position
     * Turtle Degree (public)
         * stores current degree in which the turtles head is pointing
     * Turtle Pen color (public)
         * stores the turtles pen color
     * Turtle pen up or pen down (public)
         * boolean deciding if turtle draws
 * Controller Class
     * possible controller super classes
     * Getters (public)
     * Setters (public)
     * Return a node with all of the UI elements
 * Visualizer Class
     * SetUp scene (public)
     * Do stuff with colors  (private)
     * Will return a node with all of the visual elements
 * Application Class
     * Set Stage (private)

# Design Considerations

Each command should be its own object (such as a move object, and turn object) that has different constructers based on what type of input (coordinates, no input, etc.)

Map that corresponds entries to a `String` of command
(eg. {fd, foreward, adelante} to "Forward"), where that `String` is then used to create an instance of the class object with the same name. This way, we can easily add multiple command entries that correspond to one command, as well as change the language of the command without having to make new objects.

We discussed different ways to add the entries to the list that correspondes to the map, including having all possible entries in the list, and taking the possible entries from a properties file.

**Conditionals**
|**Pro** | **Cons**|
|--|--|
| Clear to see what happens after each command | Easy to miss changing something; which can lead to duplicated code and incorrect logic |
| | leads to long code |
| | can lead to unexpected behavior if one thing is changed somewhere but not changed everywhere |
| | logic won't always be used|
| | adding a new command would mean writing a new check|

**Map + Properties File**
|**Pros** | **Con**|
|--|--|
| Different languages can easily be added by adding another properties file | |
| Multiple ways of typing one command (the entry will be the key) can be associated with the same value | Duplicated values (example fd and forward are two keys that have the same value of "Forward") |
| Can use reflection from the value `String` to create the correct Command object| |
| Easy to add in a new command by changing the properties file | making the map will be long, and each time a new command is added this method will need to be updated in order for the command to be used|


**Add commands vs buttons**

**Typing in Semi-Functional Commands**
|**Pro** | **Cons**|
|--|--|
| Intuitive to programmers (for example, typing git status)| puts a lot more stress on the model to directly communicate with the user via the display |
| | not pretty and disruptive to code flow |

**Adding Buttons for Semi-Functional Commands**
|**Pros** | **Con**|
|--|--|
| Allow for extra functionality that should only be in view (such as changing the pen color) |  |
| Can still go down to the model and pull up information |  |
| Allows the change to be separate from the code being written by the user (such as pulling up a help menu, or changing the background color) | Changes cannot visibly be put into the code being written (such as changing pen color)|
We will probably implement the use of both typing in the extra commands and having clickable buttons to accomplish the same thing. This way, things that should stay in view (such as colors or changing the dimensions of something) will stay in view, while things that should access the model (such as a list of useable commands) can be taken from the model. Additionally, it will make for a more 


# Team Responsibilities

* Tyler:
    * Working on the model (both internal and external/working with Dana)
* Hannah:
    * View External
* Dana:
    * Working on the model (both internal and external/working with Tyler)
* Alex:
    * View Internal

# Use Cases
**Clearly show the flow of calls to public methods described in your design needed to complete each example below, indicating in some way which class contains each method called:**

>The user types 'fd 50' in the command window, and sees the turtle move in the display window leaving a trail, and the command is added to the environment's history.
Note, it is not necessary to understand exactly how parsing works in order to complete this example, just what the result of parsing the command will be.
* The Controller will send the string inputted into its console to the Parser's readCommand() method.
* Inside this method, the Parser will verify the command and create the corresponding Command objects which it will then pass to the Interpreter (possibly in the form of a queue)
* The interpreter will read this stream of commands and check whether each command should go back to the View side immediately (a console command) or go to the Turtle object.
* The turtle object will read the stream of turtle specific commands and change its states based on what these commands do. It will then call upon the View to update its position based on how the commands change it, or in this case, move forward 50.
* 
>The user sets the pen's color using the UI so subsequent lines drawn when the turtle moves use that color.
* The Controller sends this updated UI color to the Turtle class, which sets a new color using its potential setColor() method.

> The user types in "diagnol 75" into the command line.

* The View External gets the command and with the controller 
* It sends it to the interpreter class which then decides that this command goes to the Turtle Class
* The Turtle Class does not recognize the command and throws an error 
* The View External receives this error and tells the Visualizer to display an error in the command box

> The user has just received errors that they typed in the wrong command and presses the help button.

* Once the user receives this error message, they do not know the correct command to use. 
* The user clicks the "help" button in the View Internal in the visualizer class
* This button handles the event and calls the view External to receive a list of all the commands from the model.
* The model External returns a list of the commands to the View External. 
* This list is passed to the visualizer and is displayed in the command window. 

>The user types in the command `fd 10, rt 90, fd 10` to move forward, turn right 90 degrees, and move forward again, creating a right angle drawn
* View external gets the command and sends it to the controller class. 
* The controller will send each command to the parser class, which will read them in the "readCommand()" method
* After reading, it creates objects for the corresponding commmands and give them to the interpreter to decide if it goes straight to view or to the turtle class
* After deciding to go to the turtle class, it will call the turtle's states based off of these commands.
* It will then call upon the view so it can update the position of the turtle on the screen based off of each command (first it will move. forward, then turn, then move. forward again)

>The user types the command `repeat 5 (fd 50, rt 144)`, trying to create a star
* View external gets the command and sends it to the controller class. 
* The controller will send each command to the parser class, which will read them in the "readCommand()" method
* After reading, it creates objects for the corresponding commmands and give them to the interpreter to decide if it goes straight to view or to the turtle class
* Since there is a repeat, it will create the objects 5 different times to complete the commands
* After deciding to go to the turtle class, it will call the turtle's states based off of these commands.
* It will then call upon the view so it can update the position of the turtle on the screen based off of each command it will do a loop 5 times of moving forward and then turning so that it can create the start

>The user types in the command `help`
* View external gets the command and sends it to the controller class. 
* The controller will send each command to the parser class, which will read them in the "readCommand()" method
* After reading, it creates objects for the corresponding commmand and gives it to the interpreter to decide if it goes straight to view or to the turtle class
* Since it doesn't change anything about the turtle, it creates a new ViewCommand Help object
* It will then send a string of all of the possible commands to use to the View, where it will be handled accordingly
* In view it will possibly be printed to the console or shown in a new window (to be decided by those working on View)

>The user types in the command `reset`
* View external gets the command and sends it to the controller class. 
* The controller will send each command to the parser class, which will read them in the "readCommand()" method
* After reading, it creates objects for the corresponding commmands and give them to the interpreter to decide if it goes straight to view or to the turtle class
* Creates a ViewCommand Reset object that internally changes the x and y position within the Turtle object back to the starting positions