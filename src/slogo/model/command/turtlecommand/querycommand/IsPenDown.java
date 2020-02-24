package slogo.model.command.turtlecommand.querycommand;

import slogo.model.Turtle;

public class IsPenDown extends QueryCommand {

    /**
     * Return 1 if the pen is down, 0 otherwise
     * @param body turtle with the pen
     */
    public IsPenDown(Turtle body){
        super(body.getDrawingStatus());
    }
}
