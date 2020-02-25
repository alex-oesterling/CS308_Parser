package slogo.model.command;

import slogo.model.Turtle;

public class YCoordinate extends Command {

    /**
     * Takes in a turtle, and returns the y coordinate of that turtle
     * @param body turtle
     */
    public YCoordinate(Turtle body){
        super(body.getY());
    }
}
