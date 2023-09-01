// SpaceInvader.java
// Sprite images from http://www.cokeandcode.com/tutorials
// Nice example how the different actor classes: SpaceShip, Bomb, SpaceInvader, Explosion
// act almost independently of each other. This decoupling simplifies the logic of the application

import ch.aplu.jgamegrid.*;

import java.awt.Point;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SpaceInvader extends GameGrid implements GGKeyListener
{
  private int nbRows = 3;
  private int nbCols = 11;
  private int nbShots = 0;
  private boolean isGameOver = false;
  private boolean isAutoTesting = false;
  private boolean plus = false;
  private Properties properties;
  private StringBuilder logResult = new StringBuilder();
  private ArrayList<Alien[]> alienGrid = null;
  private SpaceShipController spaceShipController = null;

  public SpaceInvader(Properties properties) {
    super(200, 100, 5, false);
    this.properties = properties;
  }

  /**
   * Create and set up all aliens
   *
   * Now modified to work with different alien classes instead of
   * Alien.type strings
   * @author DonLam
   */
  private void setupAliens() {
    AlienCreator alienCreator = AlienCreator.getInstance();
    isAutoTesting = Boolean.parseBoolean(properties.getProperty("isAuto"));
    String aliensControl = properties.getProperty("aliens.control");
    List<String> movements = null;
    alienGrid = alienCreator.createAliens(properties, nbRows, nbCols);
    if (aliensControl != null) {
      movements = Arrays.asList(aliensControl.split(";"));
    }
    for (int i = 0; i < nbRows; i++) {
      for (int k = 0; k < nbCols; k++) {
        Alien alien = alienGrid.get(i)[k];
        addActor(alien, new Location(100 - 5 * nbCols + 10 * k, 10 + 10 * i));
        alien.setTestingConditions(isAutoTesting, movements);
      }
    }
  }

  public String runApp(boolean isDisplayingUI) {
    setSimulationPeriod(Integer.parseInt(properties.getProperty("simulationPeriod")));
    if (properties.getProperty("version").equals("plus")) plus = true;
    nbRows = Integer.parseInt(properties.getProperty("rows"));
    nbCols = Integer.parseInt(properties.getProperty("cols"));
    setupAliens();
    setupSpaceShip();
    addKeyListener(this);
    getBg().setFont(new Font("SansSerif", Font.PLAIN, 12));
    getBg().drawText("Use <- -> to move, spacebar to shoot", new Point(400, 330));
    getBg().drawText("Press any key to start...", new Point(400, 350));

    if (isDisplayingUI) {
      show();
    }

    if (isAutoTesting) {
      setBgColor(java.awt.Color.black);  // Erase text
      doRun();
    }

    while(!isGameOver) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    doPause();
    return logResult.toString();
  }

  @Override
  public void act() {
    boolean win = true;
    logResult.append("Alien locations: ");
    for (int i = 0; i < alienGrid.size(); i++) {
      for (int j = 0; j < nbCols; j++) {
        Alien alienData = alienGrid.get(i)[j];
        if (alienData.getRowIndex() == -1) continue; // Does not Log new Aliens spawned from MultipleAlien

        String isDeadStatus;
        if (alienData.isRemoved()) {
          isDeadStatus = "Dead";
        } else {
          isDeadStatus = "Alive";
          win = false;
        }
        String gridLocation = "0-0";
        if (!alienData.isRemoved()) {
          gridLocation = alienData.getX() + "-" + alienData.getY();
        }
        String alienDataString = String.format("%s@%d-%d@%s@%s#", alienData.getType(),
                alienData.getRowIndex(), alienData.getColIndex(), isDeadStatus, gridLocation);
        logResult.append(alienDataString);
      }
    }
    logResult.append("\n");
    if (win) setIsGameOver(true, true, null);
  }

  public void notifyAliensMoveFast() {
    if (!plus) return;
    if (Alien.setSpeed(nbShots)) logResult.append("Aliens start moving fast");
  }

  // Change the way this work based on our discussion, keep the logResult part

  /**
   * Log the actors who will be notified of being hit.
   * @param actors: the list of actors to be notified.
   *
   * modified by calling gotHit() on all actors provided by
   * @author DonLam
   */
  public boolean notifyAlienHit(List<Actor> actors) {
    boolean hasHit = false;
    for (Actor actor: actors) {
      Alien alien = (Alien)actor;
      hasHit = alien.gotHit();
      String alienData = String.format("%s@%d-%d",
              alien.getType(), alien.getRowIndex(), alien.getColIndex());
      logResult.append("An alien has been hit.");
      logResult.append(alienData + "\n");
    }
    return hasHit;
  }

  public boolean keyPressed(KeyEvent evt)
  {
    if (!isRunning())
    {
      setBgColor(java.awt.Color.black);  // Erase text
      doRun();
    }
    return false;  // Do not consume key
  }

  public boolean keyReleased(KeyEvent evt)
  {
    return false;
  }

  /**
   * Create an explosion at the designated location
   * @param location: location for Explosion to be created at
   * @author DonLam
   */
  public void spawnExplosion(Location location) {
    Explosion explosion = new Explosion();
    addActor(explosion, location);
  }

  private void setupSpaceShip() {
    // Create a Spaceship Controller to handle all the spaceship controls
    spaceShipController = new SpaceShipController(this);

    // Retrieve auto testing controls
    String spaceShipControl = properties.getProperty("space_craft.control");
    List<String> controls = null;
    if (spaceShipControl != null) {
      controls = Arrays.asList(spaceShipControl.split(";"));
    }

    // Set auto testing options in spaceship controller
    spaceShipController.setTestingConditions(isAutoTesting, controls);

    // Allow spaceship controller to take input or run auto testing controls
    addKeyListener(spaceShipController);
    addActListener(spaceShipController);
  }
  public void setIsGameOver(boolean isGameOver, boolean win, Location location) {
    this.isGameOver = isGameOver;
    // Display win messages when the player wins otherwise show explosion
    if (win) {
      getBg().drawText("Number of shots: " + nbShots, new Point(10, 30));
      getBg().drawText("Game constructed with JGameGrid (www.aplu.ch)", new Point(10, 50));
      addActor(new Actor("sprites/you_win.gif"), new Location(100, 60));
    } else {
      removeAllActors();
      addActor(new Actor("sprites/explosion2.gif"), location);
    }
  }
  public void spawnBomb(Location location){
    Bomb bomb = new Bomb();
    bomb.addCollisionActors(getActors(Alien.class));
    bomb.addActorCollisionListener(bomb);
    addActor(bomb, location);
    nbShots++;
    notifyAliensMoveFast();
  }

  /**
   * Checks whether there are space on the top-most area of the game
   * @return whether the top area od the game is spawnable.
   * @author DonLam, Jim
   */
  public boolean haveTopSpace() {
    int minY = Integer.MAX_VALUE;
    for (int i = 0; i < alienGrid.size(); i++) {
      for (int j = 0; j < nbCols; j++) {
        Alien alien = alienGrid.get(i)[j];
        if (!alien.isRemoved()) minY = Math.min(alien.getY(), minY);
      }
    }
    if (minY >= 15) return true;
    return false;
  }

  /**
   * Spawn a new row of Aliens, on top of the current top-most alien
   *
   * @author DonLam, Chi-Yuan
   */
  public void generateTopAlienRow() {
    Alien anchor = findTopLeftMostAlien();
    Alien[] newAlienRow = new Alien[nbCols];
    for (int c = 0; c < nbCols; c++) {
      Location spawnPoint = new Location(anchor.getX() + (c - anchor.getColIndex()) * 10, anchor.getY() - 10);
      Alien alien = new Alien(-1, c);
      newAlienRow[c] = alien;
      addAlien(alien, spawnPoint, anchor);
      if (anchor.getRowIndex() != -1) {
        alien.act();
      }
    }
    alienGrid.add(newAlienRow);
  }

  /**
   * Find the top-left-most active alien still in the game
   * @return the top-left-most active alien
   *
   * @author DonLam
   */
  public Alien findTopLeftMostAlien() {
    int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
    Alien anchor = null;
    for (int r = 0; r < alienGrid.size(); r++) {
      for (int c = 0; c < nbCols; c++) {
        Alien alien = alienGrid.get(r)[c];
        if (!alien.isRemoved()) {
          if (alien.getY() < minY) {
            anchor = alien;
            minY = anchor.getY();
            minX = anchor.getX();
          } else if (alien.getY() == minY && alien.getX() < minX) {
            anchor = alien;
            minX = anchor.getX();
          }
        }
      }
    }
    return anchor;
  }

  /**
   * Adds a new Alien to the game
   * @param newAlien: the new Alien to be added.
   * @param location: the location in which the new Alien will be added to.
   * @param anchor: an old Alien, in order for the new Alien to sync with the rest.
   *
   * @author DonLam
   */
  public void addAlien(Alien newAlien, Location location, Alien anchor) {
    addActor(newAlien, location);
    newAlien.setDirection(anchor.getDirection());
    newAlien.setStep(anchor.getStep());
    spaceShipController.updateCollisionActors();
  }
}
