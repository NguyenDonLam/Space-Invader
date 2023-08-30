// Bomb.java
// Used for SpaceInvader

import ch.aplu.jgamegrid.*;

import java.util.List;

public class Bomb extends Actor
{
  private SpaceInvader spaceInvader;
  public Bomb()
  {
    super("sprites/bomb.gif");
  }

  public void reset()
  {
    setDirection(Location.NORTH);
  }

  public void act()
  {
    // Acts independently searching a possible target and bring it to explosion
    move();
    CollisionHandler();
    if (gameGrid != null && getLocation().y < 5)
      removeSelf();
  }

  /**
   * Handles when bomb collides with Aliens, or any other entities
   * it is supposed to check for
   *
   * @Author DonLam, JimYang
   */
  public void CollisionHandler() {
    SpaceInvader spaceInvader = (SpaceInvader) gameGrid;
    List<Actor> actors = gameGrid.getActorsAt(getLocation(), Alien.class);
    if (actors.size() > 0)
    {
      spaceInvader.notifyAlienHit(actors);
      removeSelf();
    }
  }
}
