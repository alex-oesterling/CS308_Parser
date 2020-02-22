package slogo.model.command;

abstract public class Command {
  //TODO make error catching in each of the command classes, so that if the wrong parameters are passed in the code doesn't break

  /**
   * getter for result, what the command "returns"
   * @return result
   */
  abstract public double getResult();

  /**
   * change the value of result
   * @param value what result will be set to
   */
  //protected void setResult(double value){
    //result = value;
  //}

  //TODO add throwCommandParametersError()
}
