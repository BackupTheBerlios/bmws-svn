package de.mbws.client.gui.ingame;

/**
 * This class represents an orientation in 2D space. Combine n objects of this type to get 
 * orientation in n-space. A turn changes the orientation and although its amount is one degree
 * per default it accepts other values, too.
 * <p>Ranges:</p>
 * <ul>
 * <li> 0 <= orientation, degreeOfTurn < 360 <li>
 * <ul>
 * @author mafw
 */
public class Orientation {
    private double orientation;
    private double degreeOfTurn;

    public Orientation() {
        degreeOfTurn = 1;
    }

    public void setOrientation(double newDirection) {
        orientation = newDirection;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setStep(double newStep) {
        degreeOfTurn = newStep;
        degreeOfTurn %= 360;
    }

    public void turnRight(double degree) {
        orientation += degree;
        orientation %= 360;
    }

    public void turnLeft(double degree) {
        orientation -= degree;
        orientation %= 360;
    }

    public void turnRight() {
        turnRight(degreeOfTurn);
    }

    public void turnLeft() {
        turnLeft(degreeOfTurn);
    }
}
