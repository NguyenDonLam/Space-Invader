// SpaceShip.java
// Used for SpaceInvader

import ch.aplu.jgamegrid.*;

public class SpaceShip extends Actor
{

  public SpaceShip() {
    super("sprites/spaceship.gif");
  }

  // Move the spaceship to the next location
  public void moveTo(Location location)
  {
    if (location.x > 10 && location.x < 190)
      setLocation(location);
  }

  // Spaceship notifies SpaceInvader that a bomb has been fired
  public void shoot() {
    SpaceInvader spaceInvader = (SpaceInvader) gameGrid;
    spaceInvader.spawnBomb(getLocation());
  }

  // Game over when the spaceship collides with an alien
  @Override
  public int collide(Actor actor1, Actor actor2) {
    if (actor1 instanceof Alien || actor2 instanceof Alien) {
      SpaceInvader spaceInvader = (SpaceInvader) gameGrid;
      spaceInvader.setIsGameOver(true, false, getLocation());
    }
    return 0;
  }
}