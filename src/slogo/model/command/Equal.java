package slogo.model.command;

public class Equal extends Command {

  /**
   * Equal constructor comparing doubles
   * @param double1 first object
   * @param double2 second object
   */
  public Equal(Double double1, Double double2){
    super(double1==double2);
  }

  public static void main(String[] args) {
    double a = 1.0;
    double b = 0.0;
    double c = 1.0;
    double d = -1.0;
    Equal e = new Equal(a,b);
    System.out.println(a+"=="+b+": "+e.getResult());
    e = new Equal(a,c);
    System.out.println(a+"=="+c+": "+e.getResult());
    e = new Equal(a,d);
    System.out.println(a+"=="+d+": "+e.getResult());
    e = new Equal(d,b);
    System.out.println(d+"=="+b+": "+e.getResult());  }
}
