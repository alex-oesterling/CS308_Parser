package slogo.controller;

import java.util.List;

public class Controller {

    public Controller() {
    }

    //get command from the view and give to model, need a reference to the command class
    public void setCommand(String command){
        //this.command = command;
    }

    //get parsed command from the model and give to view to be displayed
    public void returnCommand(){
        //takes in parsed command and returns it, is a getter
    }

    //get x position from model and give to view

    //get y position from model and give to view

    //get error message from model and give to view, need an error class that uses switch cases
    public void setError(Error e){
        //this.error = error;
    }

    //do actions that buttons execute

    //for the help button, gets a list of commands
//    public List<String> getCommandsList(){
//
//        return list;
//    }


}
