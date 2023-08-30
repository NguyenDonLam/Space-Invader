/**
 * This class represents an Invulnerable Alien in the game, in which it
 * swaps from an invulnerable state to normal from time to time.
 *
 * @author DonLam
 * @version 1.0
 */
public class InvulnerableAlien extends Alien {
    private static final String SPRITE_FILE = "sprites/multiple_alien.gif";
    private boolean isInvulnerable = false;        // Current invulnerability state
    private int invulnerabilityTracker = 0;        // A counter to the next state change

    /**
     * @param rowIndex: the row in which the alien will situate on.
     * @param colIndex: the column in which the alien will situate on.
     */
    public InvulnerableAlien(int rowIndex, int colIndex)
    {
        super(SPRITE_FILE, rowIndex, colIndex);
    }
    public void act() {
        super.act();
        InvulnerabilityHandler();
    }

    /**
     * The method handles the alien's invulnerability state, altering it
     * from time to time when called in act()
     */
    private void InvulnerabilityHandler() {
        invulnerabilityTracker++;
        if (invulnerabilityTracker % 4 == 0) {
            swapState();
            invulnerabilityTracker = 0;
        }
    }

    /**
     * Swap the state the Alien is currently in.
     */
    private void swapState() {
        if (isInvulnerable)
            isInvulnerable = false;
        else
            isInvulnerable = true;
    }

    /**
     * Manages the Alien when it collides with a bomb.
     *
     * @return whether the Alien got hit or not.
     */
    @Override
    public boolean gotHit() {
        if (isInvulnerable)
            return false;
         else
            return super.gotHit();
    }
}
