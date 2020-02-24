package slogo.model.command.turtlecommand.querycommand;

import slogo.model.Turtle;
import slogo.model.command.Command;

public class IsPenDown extends Command {

    /**
     * Return 1 if the pen is down, 0 otherwise
     * @param body turtle with the pen
     */
    public IsPenDown(Turtle body){
        super(body.getDrawingStatus());
    }
}
