### *This was my third project for CS308: Advanced Software Design and Implementation. We created an IDE with simple commands using the educational programming language, Logo. We have a console where users input commands, a command history view, a variable and function window, and a canvas where the object, a turtle, moves around the screen. I was responsible for the GUI for this project, displaying the turtle, animating it and drawing lines, providing a menu of controls, and displaying the command history, variables, and functions. In addition, I wrote the data management portion which handles the saving and loading of states so that one can save their work.*

parser
====

This project implements a development environment that helps users write SLogo programs.

Names:
Dana Mulligan (dmm107), Hannah Taubenfeld (hbt3), Tyler Meier (tkm22), Alex Oesterling (axo)

### Timeline

Start Date: 2/16/2020

Finish Date: 3/6/2020

Hours Spent: 40+ each

### Primary Roles
- Dana Mulligan: Model, parsing commands, writing commands
- Tyler Meier: Model, parsing commands, writing commands
- Hannah Taubenfeld: View, front end elements such as palettes, turtle values, and other user interface elements
- Alex Oesterling: View, front end elements such as turtle animation, XML files, and other user interface elements

### Resources Used

- class readings
- example lab code 
- stack over flow
- JavaFX tutorials

### Running the Program

Main class:
- Runs the SLogo App class which begins the entire simulation 
    - In this class creates a visualizer which essentially creates all other classes 
    - These other classes involve both the model and the view
    - The model is responsible for all the commands including parsing and defining the commands
    - The model is also responsible for the controller which connects the view and the model 
    - The view holds all the elements in which make up the visualizer
    - This is split into the turtle and how the result of commands are displayed and into the user interface and how the user can interact with the visualizer
    - The user interface is broken down even further depending on which user interface object requires a new window or scene to be created


Data files needed:
- XML templates 
- All language files corresponding to different commands
- All turtle images
- All buttons files in resources

Features implemented:
- Turtle has the ability to move based on commands entered by the user
- Turtle has the ability to create a path displaying how it arrived at its current position 
- User has the ability to change the color of the turtle's background and the turtle's pen color, this can be done both through user commands and the color palette as well as the color pickers
- User has the ability to change the shape of a turtle, this can be done both by selecting a new file and by user commands and the shape palette
- User can add multiple turtles and execute their commands and actions separately as well as customize the turtles separately
- User can upload an XML file with preexisting conditions or save their current conditions to an XML file
- User can execute multiple commands at once and replay the animation and /or reexecute the commands
- Each turtle's information is displayed on the screen
- User can move the turtle by clicking a range of buttons and if continuously clicked, will move the turtle 
- User can select a language in which the commands typed are interpretted 
- User can define their own commands and variables which are saved and editable upon click
- User can reexecute commands by clicking commands in the command history or clicking the up arrow
- User can specify the pen width and pen status either through use of a command or the pen properties window
- User can restart the simulation or create a new one all together or exit the current
- User can select which turtle they want to be active from a drop down menu thus changing the status and display of each turtle (in terms of the opacity)


### Notes/Assumptions

Assumptions or Simplifications:
- Given that we did not include instructions in our simulation, we are assuming that the user knows how to operate the simulation, how to execute commands, and where to look for the correct buttons/features
- Additionally, we assume that the size of the turtle area will not change and set it to a value of 500x500

Interesting data files:
- The XML files in which can be uploaded with pre existing information and settings
    - see turtleformat.xml

Known Bugs:
- Turtles command doesn't return the amount of turtles but the id number, even when declared as Double
instead of double

Extra credit:


### Impressions

- Once all the commands were written and the parser was created, it was easy to connect the view and the model
- Using path transitions made the turtle animations simpler and easier to extend 
- The additional features added during the second sprint were made easier to implement since the first sprint was completed early on and designed in an extendable format
- Refactoring classes is much harder to accomplish as the class become longer and longer. This is because of the intertwined
dependencies among different methods and classes. It is always a good idea to try and design shorter classes from the beginning.

