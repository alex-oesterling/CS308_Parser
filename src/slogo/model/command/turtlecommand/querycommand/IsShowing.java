package slogo.model.command.turtlecommand.querycommand;

import slogo.model.Turtle;
import slogo.model.command.turtlecommand.TurtleCommand;

public class IsShowing extends TurtleCommand {
    public IsShowing(Turtle body){
        super();
        body.isTurtleVisible();
    }
}
