/**
 * This class represents a Multiple Alien, which multiplies randomly
 * when space is detected.
 *
 * @author DonLam
 */
public class MultipleAlien extends Alien {
    private static final String SPRITE_FILE = "sprites/multiple_alien.gif";
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
    @Override
    public String getType() {
        return "multiple";
    }
}
