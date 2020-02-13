Dana Mulligan (dmm107)
Hannah Taubenfeld (hbt3)
Tyler Meier (tkm22)
Alex Oesterling (axo)

### Form Reponse Summary
* What behaviours should the turtle have?
    * The turtle should be able to move and turn
    * It should also have pick up and put down pen
    * The model should handle **how** the turtle moves
    * The view should handle the depiction of the turtle and the line created
* What behaviours does the result of a command need to have to be used by the front end?
    * The front end just needs to know where the turtle ends its position and at what angle
    * It can then draw a line from the initial position to this final position (assuming the turtle only moves in straight lines)
* How is the GUI updated after a command has completed execution?
    * The command is stored in some sort of history (be a command line or .txt of historic commands)
    * New lines drawn by the turtle are added to the root of the scene


### High Level Design Questions
* When does parsing need to take place and what does it need to start properly?
    * Parsing needs to take place only when a command has finished being written and the user "sends" it to the turtle.
    * This requires it to start parsing when the user presses "enter" in the text field (common syntax for sending commands).
* What is the result of parsing and who receives it?
    * If a command is always entered in a standard format, we can parse accordingly to receive a direction and a distance which is then received by the model
    * The result can also give a non movement action to be completed by the turtle (i.e. picking up and putting down a pen)
* When are errors detected and how are they reported?
    * If the command is not of a predetermined format, the parser can detect and error and tell the model to print an "invalid command" message in the command.
    * If the command sends the turtle off the screen, the model can detect and error and print an "out of bounds command" message in the command screen.
* What do commands know, when do they know it, and how do they get it?
    * Commands only need to know the bare minimum: A move command does not need to know where the turtle is, only how far it is moving. The turn command does not need to know where the turtle is either, only what angle it is turning to. 
    * Commands know it when they are sent to the model by the user pressing "enter"
    * The model gets the commands from the parser after the parser validates the command
* How is the GUI updated after a command has completed execution?
    * The command is stored in some sort of history (be a command line or .txt of historic commands)
    * New lines drawn by the turtle are added to the root of the scene