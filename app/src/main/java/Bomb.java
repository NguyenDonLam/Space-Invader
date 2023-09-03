// Bomb.java
// Used for SpaceInvader

import ch.aplu.jgamegrid.*;

public class Bomb extends Actor
{
  public Bomb()
  {
    super("sprites/bomb.gif");
  }

  public void reset()
  {
    setDirection(Location.NORTH);
  }

  /**
   * Continue moving until reaches the end of the game window
   */
  public void act()
  {
    move();
    if (getLocation().y < 5)
      removeSelf();
  }

  /**
   * Searching a possible target and notify spaceInvader if hit
   * @param actor1: the first actor that collide with the bomb
   * @param actor2: the second actor that collide with the bomb
   * @return a default integer 0
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