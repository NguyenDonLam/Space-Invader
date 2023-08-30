/**
 * This class represents a Powerful Alien in the game, in which it
 * requires 5 shots from the player to explode.
 *
 * @author Nguyen Don Lam
 * @version 1.0
 */
public class PowerfulAlien extends Alien {
    public PowerfulAlien(String imageName, int rowIndex, int colIndex)
    {
        super(imageName, rowIndex, colIndex);
        healthPoints = 5;
    }

    public void act() {
        super.act();
    }

}
