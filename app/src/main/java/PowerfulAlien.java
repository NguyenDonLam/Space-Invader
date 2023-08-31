/**
 * This class represents a Powerful Alien in the game, in which it
 * requires 5 shots from the player to explode.
 *
 * @author DonLam
 * @version Incomplete
 */
public class PowerfulAlien extends Alien {
    private static final String SPRITE_FILE = "sprites/powerful_alien.gif";
    /**
     * @param rowIndex: the row in which the alien will situate on
     * @param colIndex: the column in which the alien will situate on
     */
    public PowerfulAlien(int rowIndex, int colIndex)
    {
        super(SPRITE_FILE, rowIndex, colIndex);
        healthPoints = 5;
    }

    public void act() {
        super.act();
    }
    /**
     * @return the type of the alien, to be used for Logging only
     */
    @Override
    public String getType() {
        return "powerful";
    }

}
