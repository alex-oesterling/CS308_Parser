package slogo.model.command.turtlecommand.querycommand;

import slogo.model.Turtle;

public class IsPenDown extends QueryCommand {

    public IsPenDown(Turtle body){
        super(body.getDrawingStatus());

    }
}
