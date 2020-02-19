package slogo.controller;

import slogo.model.Turtle;
import slogo.model.command.Command;

public class Controller {
    Turtle turtle = new Turtle();
    Errors error = new Errors();
    Command command;

    public Controller() {
    }

    //get command from the view and give to model, need a reference to the command class
    public void setCommand(String command){
        //this.command = command;
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
