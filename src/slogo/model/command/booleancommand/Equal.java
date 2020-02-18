package slogo.model.command.booleancommand;

public class Equal extends BooleanCommand{

  public Equal(Object object1, Object object2){
    super(object1, object2);
  }

  @Override
  public boolean getResult() {
    return getFirstObject().equals(getSecondObject());
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

    String d = "1";
    String e = "1";
    c = new Equal(d,e);
    System.out.println(d+"==" + e + ": "+c.getResult());

    d = "2";
    e = "1";
    c = new Equal(d,e);
    System.out.println(d+"==" + e + ": "+c.getResult());

    d = "cat";
    e = "cat";
    c = new Equal(d,e);
    System.out.println(d+"==" + e + ": "+c.getResult());

    d = "CAT";
    e = "cat";
    c = new Equal(d,e);
    System.out.println(d+"==" + e + ": "+c.getResult());

    int f = 1;
    String test = "1";
    c = new Equal(f,test);
    System.out.println(f+"==" + test + ": "+c.getResult());

    char g = 'a';
    e = "a";
    c = new Equal(g,e);
    System.out.println(g+"==" + e + ": "+c.getResult());

    Boolean h = true;
    c = new Equal(b,h);
    System.out.println(b+"==" + h + ": "+c.getResult());
  }
}
