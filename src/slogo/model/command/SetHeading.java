package slogo.model.command;

import slogo.model.Turtle;

public class SetHeading extends Command {

    private Turtle t;
    private Double heading;

    /**
     * Set Heading constructor, takes in the value and turns the turtle to that
     * specific value
     * @param body the turtle being used and what will turn to value
     * @param value the value/angle that the value will turn to
     */
    public SetHeading(Turtle body, Double value){
        super();
        t = body;
        heading = value;
    }

    @Override
    public Double getResult(){
        return t.getDeltaTheta(heading, t.getHeading());
    }
    /**
     * Allows the SetHeading command to be executed
     * @return the value of the heading, where the turtle is facing
     */
    @Override
    public Double execute() {
        t.setHeading(heading);
        return this.getResult();
    }

    public static void main(String[] args) {
        Turtle t = new Turtle();
        SetHeading sh = new SetHeading(t, 45.0);
        System.out.println(sh.getResult());
        System.out.println(t.getHeading());
        sh.execute();
        System.out.println(t.getHeading());
    }
}