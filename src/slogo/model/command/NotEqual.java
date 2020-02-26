package slogo.model.command;

public class NotEqual extends Command {

  /**
   * NotEqual constructor using doubles, call super constructor and update result
   * @param double1 first double
   * @param double2 second double
   */
  public NotEqual(Double double1, Double double2){
    super(double1!=double2);
  }

}
