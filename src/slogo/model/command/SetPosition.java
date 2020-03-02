package slogo.model.command;

import slogo.model.Turtle;

public class SetPosition extends Command {

    private Turtle t;
    private Double xPos;
    private Double yPos;

    /**
     * Set position constructor, sets position of turtle without actually turning it, moves to that position
     * Calls super and sets the new position, with turtle facing same way
     * @param body the current turtle that is going to have its position changed
     * @param x the new x position that the turtle will be at
     * @param y the new  y position that the turtle will be at
     */
    public SetPosition(Turtle body, Double y, Double x){
        super();
        t = body;
        xPos = x;
        yPos = y;
    }

    /**
     * Move to position xPos, yPos, and return the distance travelled
     * @return distance travelled by the turtle
     */
    @Override
    public Double execute(){
        return t.moveToPosition(xPos, yPos);
    }
    public Double getResult() {
        return t.distanceToPosition(xPos, yPos);
    }

    public static void main(String[] args) {
        Turtle t = new Turtle();
        //SetPosition sp = new SetPosition(t,);
    }
}