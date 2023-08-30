/**
 * This class represents an Invulnerable Alien in the game, in which it
 * swaps from an invulnerable state to normal from time to time.
 *
 * @author DonLam
 * @version 1.0
 */
public class InvulnerableAlien extends Alien {
    private boolean isInvulnerable = false;        // Current invulnerability state
    private int invulnerabilityTracker = 0;        // A counter to the next state change

    /**
     *
     * @param rowIndex: the row in which the alien will situate on
     * @param colIndex: the column in which the alien will situate on
     */
    public InvulnerableAlien(int rowIndex, int colIndex)
    {
        super("sprites/invulnerable_alien.gif", rowIndex, colIndex);
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
        if (isInvulnerable) {
            System.out.println("isInvulnerable");
        }
        System.out.println(invulnerabilityTracker);
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
