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
  private boolean multiply = false;
  private boolean isGameOver = false;
  private boolean isAutoTesting = false;
  private Properties properties = null;
  private StringBuilder logResult = new StringBuilder();
  private ArrayList<Alien[]> alienGrid = null;
  public SpaceInvader(Properties properties) {
    super(200, 100, 5, false);
    this.properties = properties;
  }

  /**
   * Create and set up all aliens
   *
   * Modified to work with different alien classes instead of
   * type strings by
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
    for (int i = 0; i < nbRows; i++) {
      for (int j = 0; j < nbCols; j++) {
        Alien alienData = alienGrid.get(i)[j];

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
    updateAlienGrid();
    if (haveTopSpace() && multiply) {
      generateTopAlienRow();
      multiply = false;
    }
  }

  public void notifyAliensMoveFast() {
    Alien.setSpeed(nbShots);
    logResult.append("Aliens start moving fast");
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
      System.out.println("HELLO");
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
    SpaceShipController spaceShipController = new SpaceShipController(this);

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
    int minY = Integer.MAX_VALUE, cellSize = 0;
    for (int i = 0; i < nbRows; i++) {
      for (int j = 0; j < nbCols; j++) {
        Alien alien = alienGrid.get(i)[j];
        if (!alien.isRemoved()) minY = Math.min(alien.getY(), minY);
      }
    }
    if (minY >= 15) return true;
    return false;
  }
  public void generateTopAlienRow() {
    Alien anchor = findTopLeftMostAlien();
    Alien[] newAlienRow = new Alien[nbCols];
    for (int c = 0; c < nbCols; c++) {
      Location spawnPoint = new Location(anchor.getX() + (c - anchor.getColIndex()) * 10, anchor.getY() - 10);
      Alien alien = new Alien(-1, c);
      newAlienRow[c] = alien;
      addActor(alien, spawnPoint);
      alien.setStep(anchor.getStep());
      alien.setDirection(anchor.getDirection());
    }
    alienGrid.add(0, newAlienRow);
    System.out.println("++++++++++++++++++");
    System.out.println("Anchor steps:" + anchor.getStep());
    for(Alien [] aliens: alienGrid) {
      for (Alien alien : aliens) {
        System.out.println(alien.getStep());

      }
    }
    System.out.println("++++++++++++++++++");
  }

  /**
   * Find the top-left-most active alien still in the game
   * @return the top-left-most active alien
   *
   * @author DonLam
   */
  public Alien findTopLeftMostAlien() {
    for (int r = 0; r < alienGrid.size(); r++) {
      for (int c = 0; c < nbCols; c++) {
        if (!alienGrid.get(r)[c].isRemoved()) {
          return alienGrid.get(r)[c];
        }
      }
    }
    return null;
  }

  /**
   * Remove top-most rows when all aliens are gone
   *
   * @author DonLam
   */
  public void updateAlienGrid() {
    int row = 0;
    while (allAliensGone(row) && row < alienGrid.size()){
      alienGrid.remove(row);
      row++;
    }
  }

  /**
   * Check if all aliens of a particular row is gone.
   * @param row: the row to be checked at.
   * @return whether the row still has active alien(s).
   *
   * @author DonLam
   */
  public boolean allAliensGone(int row) {
    for (int i = 0; i < nbCols; i++) {
      if(!alienGrid.get(row)[i].isRemoved()) {
        return false;
      }
    }
    return true;
  }

  public void setMultiply(boolean multiply) {
    this.multiply = multiply;
  }

  public boolean getMultiply() {return multiply;}
}
