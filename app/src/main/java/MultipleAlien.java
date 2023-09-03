import java.util.Random;

/**
 * This class represents a Multiple Alien, which multiplies randomly
 * when space is detected.
 *
 * @author DonLam
 * @version 1.1
 */

public class MultipleAlien extends Alien {
    private static final String SPRITE_FILE = "sprites/multiple_alien.gif";
    private final Alien postMultiplied = new Alien(getRowIndex(), getColIndex());

    /**
     * @param rowIndex: the row in which the alien will situate on
     * @param colIndex: the column in which the alien will situate on
     */
    public MultipleAlien(int rowIndex, int colIndex) {
        super(SPRITE_FILE, rowIndex, colIndex);
    }
    public void act() {
        super.act();
        handleSpawning();
    }

    /**
     * Handles the spawning of new Aliens.
     */
    public void handleSpawning() {
        SpaceInvader game = (SpaceInvader) gameGrid;
        if (game.haveTopSpace()) {
            Random randomizer = new Random();
            int result = randomizer.nextInt(1, 8);
            if (result == 1) {
                game.generateTopAlienRow();
                game.addAlien(postMultiplied, getLocation(), this);
                removeSelf();
            }
        }
    }

    @Override
    public String getType() {
        return "multiple";
    }
}
