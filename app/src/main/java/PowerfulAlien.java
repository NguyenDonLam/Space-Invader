/**
 * This class represents a Powerful Alien in the game, in which it
 * requires 5 shots from the player to explode.
 *
 * @author DonLam, Chi-Yuan, AndreChiang
 */
public class PowerfulAlien extends Alien {
    private static final String SPRITE_FILE = "sprites/powerful_alien.gif";
    private static final int HEALTH = 5;
    private final String TYPE = "powerful";

    /**
     * @param rowIndex: the row in which the alien will situate on
     * @param colIndex: the column in which the alien will situate on
     *
     * @author DonLam
     */
    public PowerfulAlien(int rowIndex, int colIndex) {
        super(SPRITE_FILE, rowIndex, colIndex);
        healthPoints = HEALTH;
    }

    public void act() {
        super.act();
    }

    /**
     * @return the type of the Alien, for logging only.
     * @author DonLam
     */
    @Override
    public String getType() {
        return TYPE;
    }

}
