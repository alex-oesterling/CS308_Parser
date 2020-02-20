package slogo.model.command.booleancommand;

public class GreaterThan extends BooleanCommand {

  /**
   * GreaterThan constructor for checking a>b
   * Only works for ints, Integers, doubles, Doubles, floats, and Floats
   * @param value1 a
   * @param value2 b
   */
  public GreaterThan(double value1, double value2){
    super();
    super.changeBooleanResultToDouble(value1>value2);
  }

  public static void main(String[] args) {
    int a = 3;
    int b = 1;
    GreaterThan c = new GreaterThan(a,b);
    System.out.println(a+">"+b+": "+c.getResult());

    c = new GreaterThan(b,a);
    System.out.println(b+">"+a+": "+c.getResult());

    a = 3;
    b = 3;
    c = new GreaterThan(a,b);
    System.out.println(a+">"+b+": "+c.getResult());

    double aa = 3.1;
    b = 3;
    c = new GreaterThan(aa,b);
    System.out.println(aa+">"+b+": "+c.getResult());

    a = 3;
    double bb = 3.1;
    c = new GreaterThan(a,bb);
    System.out.println(a+">"+bb+": "+c.getResult());

    aa = 3.00000;
    bb = 3.00000;
    c = new GreaterThan(aa,bb);
    System.out.println(aa+">"+bb+": "+c.getResult());

    float aaa = 3.6f;
    float bbb = 3.4f;
    c = new GreaterThan(aaa,bbb);
    System.out.println(aaa+">"+bbb+": "+c.getResult());

    Integer aaaa = 1;
    Integer bbbb = 0;
    c = new GreaterThan(aaaa,bbbb);
    System.out.println(aaaa+">"+bbbb+": "+c.getResult());

    c = new GreaterThan(bbb, aaaa);
    System.out.println(bbb+">"+aaaa+": "+c.getResult());
  }
}