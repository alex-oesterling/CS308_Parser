package slogo.controller;

import slogo.model.Parser;
import slogo.model.Turtle;
import slogo.model.command.Command;
import slogo.view.Visualizer;

public class Controller {
    Turtle turtle = new Turtle();
    Errors error = new Errors();
    Command command;
    Parser myParser;
    Visualizer myView;

    public Controller(Parser parser, Visualizer visualizer) {
        myParser = parser;
        myView = visualizer;
    }

    //get command from the view and give to model, need a reference to the command class
    public void setCommand(String command){
        //this.command = command;
        myParser.readCommandFromString(command);
    }

    /**
     * Gets parsed command from model
     * @return command
     */
    public double returnCommand(){
        return command.getResult();
    }

    /**
     * Gets x position from Turtle class
     * @return double
     */
    public double getXPosition(){
        return turtle.getX();
    }

    /**
     * Gets y position from Turtle class
     * @return double
     */
    public double getYPosition(){
        return turtle.getY();
    }

    /**
     * Takes an error from the model
     * @param e
     */

    //FIXME commented out by Alex. when creating the error class its constructor creates a new error and there is a stack overflow
    public void setError(Errors e){
        this.error = e;
    }

    //do actions that buttons execute

    //for the help button, gets a list of commands
//    public List<String> getCommandsList(){
//
//        return list;
//    }


}
