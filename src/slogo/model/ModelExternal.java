package slogo.model;

import java.util.List;
import slogo.controller.Controller;
import slogo.model.command.Command;

/**
 * Class that holds all of the model external public methods
 * @author Tyler Meier and Dana Mulligan
 */
public class ModelExternal implements ModelExternalAPI {

  private CommandCreator creator;

  /**
   * external model interface implemented, to be given to controller
   * @param control Controller to be given to the command creator
   * @param language language the slogo model is working in
   */
  public ModelExternal(Controller control, String language){
    creator = new CommandCreator(control, language);
  }

  @Override
  /**
   * Sets the turtle at specific position
   * @param x x position
   * @param y y position
   * @param heading degrees fixing
   */
  public void orientTurtle(double x, double y, double heading) {
    creator.orientTurtle(x, y, heading);
  }

  @Override
  /**
   * Sets specific turtle
   * @param t the turtle being set
   */
  public void setTurtle(Turtle t){
    creator.setTurtle(t);
  }

  @Override
  /**
   * Change to a new language of input
   * @param language input language: English, Spanish, Urdu, etc.
   */
  public void setLanguage(String language){
    creator.setLanguage(language);
  }

  @Override
  /**
   * Receives the commands to be done from the view/UI
   * @param commands the commands the user typed in
   */
  public List<Command> getCommandsOf(String commands){
    return creator.getCommandsOf(commands);
  }
}
