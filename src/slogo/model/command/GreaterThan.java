package slogo.model.command;

public class GreaterThan extends Command {

  /**
   * GreaterThan constructor for checking a>b
   * Only works for ints, Integers, doubles, Doubles, floats, and Floats
   * @param value1 a
   * @param value2 b
   */
  public GreaterThan(Double value1, Double value2){
    super(value1>value2);
  }

  public static void main(String[] args) {
    double a = 3;
    double b = 1;
    GreaterThan c = new GreaterThan(a,b);
    System.out.println(a+">"+b+": "+c.execute());

    c = new GreaterThan(b,a);
    System.out.println(b+">"+a+": "+c.execute());

    a = 3;
    b = 3;
    c = new GreaterThan(a,b);
    System.out.println(a+">"+b+": "+c.execute());

    double aa = 3.1;
    b = 3;
    c = new GreaterThan(aa,b);
    System.out.println(aa+">"+b+": "+c.execute());

    a = 3;
    double bb = 3.1;
    c = new GreaterThan(a,bb);
    System.out.println(a+">"+bb+": "+c.execute());

    aa = 3.00000;
    bb = 3.00000;
    c = new GreaterThan(aa,bb);
    System.out.println(aa+">"+bb+": "+c.execute());
  }
}
