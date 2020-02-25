package slogo.model.command;

public class Not extends Command {

  /**
   * Not constructor, flip the value of whatever given;
   * call super constructor, and update result
   * @param a
   */
  public Not(Double a){
    super(!(a!=0.0));
  }

  public static void main(String[] args) {
    double a = 3.0;
    double b = 0;
    Not c = new Not(a);
    System.out.println("!"+a+" : "+c.execute());
    c = new Not(b);
    System.out.println("!"+b+" : "+c.execute());
  }

}
