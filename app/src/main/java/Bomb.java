// Bomb.java
// Used for SpaceInvader
/**
 * The class represents a projectile shot by the spaceship(player) in a
 * space invader game
 * Tuesday-9AM-Team1a
 * @author DonLam, Chi-Yuan, AndreChiang
 */

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class Bomb extends Actor {
    private static final int TOP_BOUNDARY = 5;
    private static final String SPRITE_FILE = "sprites/bomb.gif";

    public Bomb() {
        super(SPRITE_FILE);
    }

    public void reset() {
        setDirection(Location.NORTH);
    }

    /**
     * Continue moving until reaches the end of the game window
     */
    public void act() {
        move();
        if (getLocation().y < TOP_BOUNDARY)
            removeSelf();
    }

    /**
     * Searching a possible target and notify spaceInvader if hit
     *
     * @param actor1: the first actor that collide with the bomb
     * @param actor2: the second actor that collide with the bomb
     * @return a default integer 0
     *
     * @author Chi-Yuan
     */
    @Override
    public int collide(Actor actor1, Actor actor2) {
        boolean hasHit = false;
        if (actor1 instanceof Alien || actor2 instanceof Alien) {
            SpaceInvader spaceInvader = (SpaceInvader) gameGrid;
            hasHit = spaceInvader.notifyAlienHit(gameGrid.getActorsAt(getLocation(), Alien.class));
        }
        if (hasHit) removeSelf();
        return 0;
    }
}