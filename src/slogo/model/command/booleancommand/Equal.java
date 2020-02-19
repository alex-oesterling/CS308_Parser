package slogo.model.command.booleancommand;

public class Equal extends BooleanCommand{

  /**
   * Equal constructor comparing booleans
   * @param bool1 first object
   * @param bool2 second object
   */
  public Equal(boolean bool1, boolean bool2){
    super();
    super.changeBooleanResultToDouble(bool1==bool2);
  }

  /**
   * Equal constructor comparing doubles
   * @param double1 first object
   * @param double2 second object
   */
  public Equal(double double1, double double2){
    super();
    super.changeBooleanResultToDouble(double1==double2);
  }

  public static void main(String[] args) {
    boolean a = false;
    boolean b = true;
    Equal c = new Equal(a,b);
    System.out.println(a+"==" + b + ": "+c.getResult());

    a = true;
    b = false;
    c = new Equal(a,b);
    System.out.println(a+"==" + b + ": "+c.getResult());

    a = false;
    b = false;
    c = new Equal(a,b);
    System.out.println(a+"==" + b + ": "+c.getResult());

    a = true;
    b = true;
    c = new Equal(a,b);
    System.out.println(a+"==" + b + ": "+c.getResult());

    Boolean h = true;
    c = new Equal(b,h);
    System.out.println(b+"==" + h + ": "+c.getResult());

    int i = 1;
    int j = 0;
    c = new Equal(i,j);
    System.out.println(i+"=="+j+": " +c.getResult());
  }
}
