package slogo.model.command.booleancommand;

import slogo.model.command.Command;

abstract public class BooleanCommand extends Command{// implements Cloneable{

  private static final boolean DEFAULT = false;
  private boolean booleanA;
  private boolean booleanB;
  private Object objectA;
  private Object objectB;

  public BooleanCommand(boolean condition1, boolean condition2){
    booleanA = condition1;
    booleanB = condition2;
  }
  public BooleanCommand(Object object1, Object object2){
    objectA = object1;
    objectB = object2;
  }

  //@Override
 /// protected Object clone() throws CloneNotSupportedException {
    //return super.clone();
  //}

  protected boolean getFirstCondition(){
    return booleanA;
  }
  protected boolean getSecondCondition(){
    return booleanB;
  }

  protected /*int*/ Object getFirstObject(){
    return objectA;//.hashCode();
  }

  protected /*int*/ Object getSecondObject(){
    return objectB;//.hashCode();
  }

  abstract public boolean getResult();
}
