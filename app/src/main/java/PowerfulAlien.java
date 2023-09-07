/**
 * This class represents a Powerful Alien in the game, in which it
 * requires 5 shots from the player to explode.
 *
 * @author DonLam
 * @version 1.0
 */
public class PowerfulAlien extends Alien {
    private static final String SPRITE_FILE = "sprites/powerful_alien.gif";
    private static final int HEALTH = 5;

    /**
     * @param rowIndex: the row in which the alien will situate on
     * @param colIndex: the column in which the alien will situate on
     */
    public PowerfulAlien(int rowIndex, int colIndex) {
        super(SPRITE_FILE, rowIndex, colIndex);
        healthPoints = HEALTH;
    }

    public void act() {
        super.act();
    }

    @Override
    public String getType() {
        return "powerful";
    }

}
