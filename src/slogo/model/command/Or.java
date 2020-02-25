package slogo.model.command;

public class Or extends Command {

  /**
   * Or constructors, checks for value of a||b,
   * call super constructor and update result
   * @param value1 a
   * @param value2 b
   */
  public Or(double value1, Double value2){
    super(value1!=0.0||value2!=0.0);
  }

  public static void main(String[] args) {
    double a = 3.0;
    double b = 1.0;
    double c = 0.0;
    double f = 0.0;
    Or d = new Or(a,b);
    System.out.println(a+"||"+b+": "+d.execute());
    d = new Or(a,c);
    System.out.println(a+"||"+c+": "+d.execute());
    d = new Or(c,f);
    System.out.println(c+"||"+f+": "+d.execute());
  }
}
