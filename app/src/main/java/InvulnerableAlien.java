/**
 * This class represents an Invulnerable Alien in the game, in which it
 * swaps from an invulnerable state to normal from time to time.
 * Tuesday-9AM-Team1a
 * @author DonLam, Chi-Yuan, AndreChiang
 */
public class InvulnerableAlien extends Alien {
    private static final String SPRITE_FILE = "sprites/invulnerable_alien.gif";
    private final String TYPE = "invulnerable";
    private boolean isInvulnerable = false;        // Current invulnerability state
    private int invulnerabilityTracker = 0;        // A counter to the next state change
    private static final int INVULNERABLE_DURATION = 4;

    /**
     * @param rowIndex: the row in which the alien will situate on.
     * @param colIndex: the column in which the alien will situate on.
     *
     * @author DonLam
     */
    public InvulnerableAlien(int rowIndex, int colIndex) {
        super(SPRITE_FILE, rowIndex, colIndex);
    }

    public void act() {
        super.act();
        invulnerabilityHandler();
    }

    /**
     * The method handles the alien's invulnerability state, altering it
     * from time to time when called in act()
     *
     * @author DonLam
     */
    private void invulnerabilityHandler() {
        invulnerabilityTracker++;
        if (invulnerabilityTracker % INVULNERABLE_DURATION == 0) {
            swapState();
            invulnerabilityTracker = 0;
        }
    }

    /**
     * Swap the state the Alien is currently in.
     * @author DonLam
     */
    private void swapState() {
        isInvulnerable = !isInvulnerable;
    }

    /**
     * Manages the Alien when it collides with a bomb.
     *
     * @return whether the Alien got hit or not.
     *
     * @author DonLam
     */
    @Override
    public boolean gotHit() {
        if (isInvulnerable)
            return false;
        else
            return super.gotHit();
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
