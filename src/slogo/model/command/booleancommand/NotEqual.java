package slogo.model.command.booleancommand;

public class NotEqual extends BooleanCommand{

  /**
   * NotEqual constructor using doubles, call super constructor and update result
   * @param double1 first double
   * @param double2 second double
   */
  public NotEqual(double double1, double double2){
    super();
    super.changeBooleanResultToDouble(double1!=double2);
  }
  
  /**
   * NotEqual constructor using booleans
   * @param bool1 first boolean
   * @param bool2 second boolean
   */
  public NotEqual(boolean bool1, boolean bool2){
    super();
    super.changeBooleanResultToDouble(bool1!=bool2);
  }

  public static void main(String[] args) {
    boolean a = false;
    boolean b = true;
    NotEqual c = new NotEqual(a,b);
    System.out.println(a+"!=" + b + ": "+c.getResult());

    a = true;
    b = false;
    c = new NotEqual(a,b);
    System.out.println(a+"!=" + b + ": "+c.getResult());

    a = false;
    b = false;
    c = new NotEqual(a,b);
    System.out.println(a+"!=" + b + ": "+c.getResult());

    a = true;
    b = true;
    c = new NotEqual(a,b);
    System.out.println(a+"!=" + b + ": "+c.getResult());

    Boolean h = true;
    c = new NotEqual(b,h);
    System.out.println(b+"!=" + h + ": "+c.getResult());
  }
}
