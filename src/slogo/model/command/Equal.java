package slogo.model.command;

public class Equal extends Command {

  /**
   * Equal constructor comparing doubles
   * @param double1 first object
   * @param double2 second object
   */
  public Equal(Double double1, Double double2){
    super(double1==double2);
  }

}
