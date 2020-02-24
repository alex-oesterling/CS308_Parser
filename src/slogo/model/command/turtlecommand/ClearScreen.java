package slogo.model.command.turtlecommand;

import slogo.model.Turtle;
import slogo.model.command.Command;

public class ClearScreen extends Command {
    /**
     * The  clear screen constructor which erases the turtle's trails and sends it to its home position
     * @param body the current turtle being used
     */
    public ClearScreen(Turtle body){
        super(body.goHome());
    }
}
