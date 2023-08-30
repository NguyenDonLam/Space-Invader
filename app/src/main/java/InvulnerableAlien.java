/**
 * This class represents an Invulnerable Alien in the game, in which it
 * swaps from an invulnerable state to normal from time to time.
 *
 * @author Nguyen Don Lam
 * @version 1.0
 */
public class InvulnerableAlien extends Alien {
    private boolean isInvulnerable = false;        // Current invulnerability state
    private int invulnerabilityTracker = 0;        // A counter to the next state change

    /**
     *
     * @param imageName: file path to the image of the Invulnerable Alien
     * @param rowIndex: the row in which the alien will situate on
     * @param colIndex: the column in which the alien will situate on
     */
    public InvulnerableAlien(String imageName, int rowIndex, int colIndex)
    {
        super(imageName, rowIndex, colIndex);
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
        if (invulnerabilityTracker % 10 == 0) {
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
     * @return the amount of healthPoints the alien got left.
     */
    @Override
    public int gotHit() {
        return super.gotHit();
    }
}
