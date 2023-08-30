/**
 * This class represents a Powerful Alien in the game, in which it
 * requires 5 shots from the player to explode.
 *
 * @author DonLam
 * @version 1.0
 */
public class PowerfulAlien extends Alien {
    public PowerfulAlien(int rowIndex, int colIndex)
    {
        super("sprites/powerful_alien.gif", rowIndex, colIndex);
        healthPoints = 5;
        System.out.println("HELLO");
    }

    public void act() {
        super.act();
    }

}
