/**
 * This class represents a Multiple Alien, which multiplies randomly
 * when space is detected.
 *
 * @author DonLam, Chi-Yuan, AndreChiang
 */
import java.util.Random;

public class MultipleAlien extends Alien {
    private static final String SPRITE_FILE = "sprites/multiple_alien.gif";
    private final String TYPE = "multiple";
    private final Alien children = new Alien(getRowIndex(), getColIndex());
    private static final int SPAWN_CODE = 1;
    private static final int CHANCE = 8;

    /**
     * @param rowIndex: the row in which the alien will situate on
     * @param colIndex: the column in which the alien will situate on
     *
     * @author DonLam
     */
    public MultipleAlien(int rowIndex, int colIndex) {
        super(SPRITE_FILE, rowIndex, colIndex);
    }

    public void act() {
        super.act();
        handleSpawning();
    }

    /**
     * Handles the spawning of new Alien.
     * @author DonLam
     */
    public void handleSpawning() {
        SpaceInvader game = (SpaceInvader) gameGrid;
        if (game.haveTopSpace()) {
            Random randomizer = new Random();
            int result = randomizer.nextInt(SPAWN_CODE, CHANCE);
            if (result == SPAWN_CODE) {
                game.generateTopAlienRow();
                game.addAlien(children, getLocation(), this);
                removeSelf();
            }
        }
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
