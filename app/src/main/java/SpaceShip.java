// SpaceShip.java
// Used for SpaceInvader
/**
 * This class represents a spaceship within the game, which has the ability to
 * move, shoot, and die to any Alien if collided
 *
 * @author Chi-Yuan
 */
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class SpaceShip extends Actor {

    private static final int LEFT_BOUNDARY = 10;
    private static final int RIGHT_BOUNDARY = 190;
    private static final String SPRITE_FILE = "sprites/spaceship.gif";

    public SpaceShip() {
        super(SPRITE_FILE);
    }

    /**
     * Moves the spaceship to the next location
     * @param location: location to move to.
     *
     * @author Chi-Yuan
     */
    public void moveTo(Location location) {
        if (location.x > LEFT_BOUNDARY && location.x < RIGHT_BOUNDARY)
            setLocation(location);
    }

    /**
     * Spaceship notifies SpaceInvader that a bomb has been fired
     * @author Chi-Yuan
     */
    public void shoot() {
        SpaceInvader spaceInvader = (SpaceInvader) gameGrid;
        spaceInvader.spawnBomb(getLocation());
    }

    /**
     * Game over when the spaceship collides with an alien
     *
     * @param actor1: the first collided actor
     * @param actor2: the second collided actor
     * @return a default integer 0
     *
     * @author Chi-Yuan
     */
    @Override
    public int collide(Actor actor1, Actor actor2) {
        if (actor1 instanceof Alien || actor2 instanceof Alien) {
            SpaceInvader spaceInvader = (SpaceInvader) gameGrid;
            spaceInvader.setIsGameOver(true, false, getLocation());
        }
        return 0;
    }
}