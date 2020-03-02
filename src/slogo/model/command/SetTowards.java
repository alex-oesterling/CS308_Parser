package slogo.model.command;

import java.util.Set;
import slogo.model.Turtle;

public class SetTowards extends Command {

    private Turtle t;
    private Double xPos;
    private Double yPos;

    /**
     * Set Towards constructor, turns the turtle to face the given point
     * @param body the turtle currently being used
     * @param x the x position of where the turtle will turn to
     * @param y the y position of where the turtle will turn to
     */
    public SetTowards(Turtle body, Double y, Double x){
        super();
        t = body;
        xPos = x;
        yPos = y;
    }
    @Override
    public Double getResult(){
        return t.headingTowards(xPos, yPos);
    }

    /**
     * Point the turtle towards xPos, yPos
     * @return angle changed
     */
    @Override
    public Double execute() {
        t.pointTowards(xPos,yPos);
        return this.getResult();
    }

    public static void main(String[] args) {
        Turtle t = new Turtle();
        SetTowards st = new SetTowards(t, 10.0, 10.0);
        System.out.println(st.getResult());
        System.out.println(st.execute());
    }
}