Dana Mulligan (dmm107)
Hannah Taubenfeld (hbt3)
Tyler Meier (tkm22)
Alex Oesterling (axo)

# API_CHANGES.md
APIS as code

Parser Class
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
 * (Controller?) Class
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
 * Controller Class (not part of view or model API, just here as part of plan)
     * possible controller super classes
     * Getters (public)
     * Setters (public)
     * Return a node with all of the UI elements
 * Visualizer Class
     * SetUp scene (public)
     * Do stuff with colors  (private)
     * Will return a node with all of the visual elements
     * **NEW ADDITIONS:**
         * Return the current selected Turle image
         * Clears the scene
         * Adds command to the user defined list
         * Sets the pen color
         * Sets the background color
         * Sets the pen width
         * Sets image from a pallette

 * **NEW ADDITIONS:
     * Turtle View
         * choosing a turtle
         * updates turtle position 
         * plays animation
         * reset turtle position 
         * update pen color 
         * update rendered image 
         * update pen size
         * update pen status 
         * reports pen color 
     * Command Line
         * Sets up the terminal
         * Creates an on-click event handler to allow users to click on commands in the history and reuse them
         * Scrolls up in the command history and allows the user to reuse them
         * Sets the text in the terminal to a certain string
     * Styler
         * creates labels
         * creates buttons
     * Help Window 
         * creates a new window with all possible commands depending on the language
     * Shape Palette
         * creates a new palette with shapes mapped to a value which the user can change
     * Color Palette
         * creates a new palette with colors mapped to a value which the user can change 
 
 * View External
     * View External Class implements External API
         * Update turtle position
         * Update pen color
         * Update pen color from pallete
         * Update background color from pallete
         * Update pen width
         * Update turtle image from pallete
         * Update background color
         * Clear turtles
         * Shows and hides the turtle
         * Picks up/puts down pen
         * Plays the turtle animation
         * Adds user defined commands to a list of commands
         * Adds user defined variables to a list of variables

APIS

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
    * Features supported and how: Sets scene through the Application Class.
        * Application Class - includes the setting of the stage and Main class 
            * Extendable: We can create multiple instances of an application, allowing for multiple programs to run at once
            * Closed to modification (especially Application)
            * Resources: Any images needed to create the scene or stage. The current and future position of the turtle. Also, any styling files we might create.  
        * Visualizer Class - combines all UI elements for rendering
            * Extendable: we can add many more features and supported classes to the visualizer which are added to the main scene
            * Closed to modification
            * Resources: all other dependent classes as well as images
        * UI Elements:
            * Help Window Class
            * ShapePalette Class
            * ColorPalette Class
            * CommandLine Class
            * ToolBar Class
        * TurtleView Class - Handles the rendering and tracking of the view instance of each turtle. Contains information about coordinates, pen color, and more.
            * Extendable: A new turtleview extension could contain more information like multiple images, new ways of moving, and any additional properties
            * Closed to modification
            * Resources: Requires imagefiles to be rendered with the Turtle
* View External  
    * Features supported and how: Updates turtle's rendering, position, pen status, etc using ViewExternal Class:
        * Resources: Visualizer class needed to call methods on
        * Extendable: ViewExternal implements the ViewExternalAPI, and you could add more public methods to the API depending on what new functionalities you owuld need.

* **NEW ADDITIONS:
* CONTROLLER CLASS
    * public Controller(ViewExternal visualizer, String language) 
    * public void addTurtle()
    * public void addTurtle(String name, double startingX, double startingY, int startingHeading)
    * public String getTurtleName()
    * public void updateCommandVariable(String key, String newValue)
    * public void addUserVariable(String key, String value)
    * public void addUserCommand(String key, String syntax)
    * public void chooseTurtle(String name)
    * public void addLanguage(String language)
    * public void reset()
    * public void sendCommands(String commands)
 


