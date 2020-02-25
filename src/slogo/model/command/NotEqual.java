package slogo.model.command;

public class NotEqual extends Command {

  /**
   * NotEqual constructor using doubles, call super constructor and update result
   * @param double1 first double
   * @param double2 second double
   */
  public NotEqual(Double double1, Double double2){
    super(double1!=double2);
  }

  public static void main(String[] args) {
    double a = 1.0;
    double b = 0.0;
    double c = 1.0;
    double d = -1.0;
    NotEqual e = new NotEqual(a,b);
    System.out.println(a+"!="+b+": "+e.execute());
    e = new NotEqual(a,c);
    System.out.println(a+"!="+c+": "+e.execute());
    e = new NotEqual(a,d);
    System.out.println(a+"!="+d+": "+e.execute());
    e = new NotEqual(d,b);
    System.out.println(d+"!="+b+": "+e.execute());
  }
}
