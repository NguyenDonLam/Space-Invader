import java.util.Random;

/**
 * This class represents a Multiple Alien, which multiplies randomly
 * when space is detected.
 *
 * @author DonLam
 * @version Incomplete
 */

public class MultipleAlien extends Alien {
    private static final String SPRITE_FILE = "sprites/multiple_alien.gif";
    private boolean hasMultiplied = false;
    /**
     * @param rowIndex: the row in which the alien will situate on
     * @param colIndex: the column in which the alien will situate on
     */
    public MultipleAlien(int rowIndex, int colIndex) {
        super(SPRITE_FILE, rowIndex, colIndex);
    }
    /**
     * @return the type of the alien, to be used for Logging only
     */
    public void act() {
        super.act();
        if (!hasMultiplied) handleSpawning();
    }

    public void handleSpawning() {
        SpaceInvader game = (SpaceInvader) gameGrid;
        if (game.haveTopSpace()) {
            Random randomizer = new Random();
            int result = randomizer.nextInt(1, 8);
            if (result == 1 && !game.getMultiply()) {
                game.setMultiply(true);
                hasMultiplied = true;
            }
        }
    }

    @Override
    public String getType() {
        return "multiple";
    }
}
