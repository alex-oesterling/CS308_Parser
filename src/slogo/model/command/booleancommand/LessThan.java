package slogo.model.command.booleancommand;

public class LessThan extends BooleanCommand {

  /**
   * LessThan constructor for checking a<b
   * Only works for ints and doubles
   * @param value1 a
   * @param value2 b
   */
  public LessThan(double value1, double value2){
    super();
    super.changeBooleanResultToDouble(value1<value2);
  }

  public static void main(String[] args) {
    int a = 3;
    int b = 1;
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

    float aaa = 3.6f;
    float bbb = 3.4f;
    c = new LessThan(aaa,bbb);
    System.out.println(aaa+"<"+bbb+": "+c.getResult());

    Integer aaaa = 1;
    Integer bbbb = 0;
    c = new LessThan(aaaa,bbbb);
    System.out.println(aaaa+"<"+bbbb+": "+c.getResult());

    c = new LessThan(bbb, aaaa);
    System.out.println(bbb+"<"+aaaa+": "+c.getResult());
  }
}