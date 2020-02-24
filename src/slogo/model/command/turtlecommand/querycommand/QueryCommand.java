package slogo.model.command.turtlecommand.querycommand;

import slogo.model.command.Command;

public class QueryCommand extends Command {

    /**
     * Query command constructor for booleans
     * @param returnValueAsBool boolean to be converted to a double
     */
    public QueryCommand(boolean returnValueAsBool){
        super(returnValueAsBool?1.0:0.0); //convert to a double
    }

    /**
     * Query command constructor for booleans
     * @param returnValue boolean to be converted to a double
     */
    public QueryCommand(double returnValue){
        super(returnValue); //convert to a double
    }

}
