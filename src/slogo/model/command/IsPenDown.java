package slogo.model.command;

import slogo.model.Turtle;

public class IsPenDown extends Command {

    private Turtle t;
    /**
     * Return 1 if the pen is down, 0 otherwise
     * @param body turtle with the pen
     */
    public IsPenDown(Turtle body){
        super();
        t = body;
    }

    /**
     * Allows the isPenDown command to be executed and tells whether the turtle is drawing or not
     * @return 1 if drawing or 0 if not drawing
     */
    @Override
    public Double execute() {
        return t.getDrawingStatus();
    }
}
