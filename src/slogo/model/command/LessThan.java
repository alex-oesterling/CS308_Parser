package slogo.model.command;

public class LessThan extends Command {

  /**
   * LessThan constructor for checking a<b
   * Only works for ints and doubles
   * @param value1 a
   * @param value2 b
   */
  public LessThan(Double value1, Double value2){
    super(value1<value2);
  }

  public static void main(String[] args) {
    double a = 3;
    double b = 1;
    LessThan c = new LessThan(a,b);
    System.out.println(a+"<"+b+": "+c.getResult());

    c = new LessThan(b,a);
    System.out.println(b+"<"+a+": "+c.getResult());

    a = 3;
    b = 3;
    c = new LessThan(a,b);
    System.out.println(a+"<"+b+": "+c.getResult());

    double aa = 3.1;
    b = 3;
    c = new LessThan(aa,b);
    System.out.println(aa+"<"+b+": "+c.getResult());

    a = 3;
    double bb = 3.1;
    c = new LessThan(a,bb);
    System.out.println(a+"<"+bb+": "+c.getResult());

    aa = 3.00000;
    bb = 3.00000;
    c = new LessThan(aa,bb);
    System.out.println(aa+"<"+bb+": "+c.getResult());
  }
}
