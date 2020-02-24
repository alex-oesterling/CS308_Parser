package slogo.model.command.turtlecommand.querycommand;

import slogo.model.Turtle;
import slogo.model.command.Command;

public class YCoordinate extends QueryCommand {

    /**
     * Takes in a turtle, and returns the y coordinate of that turtle
     * @param body turtle
     */
    public YCoordinate(Turtle body){ super(body.getY());}
}
