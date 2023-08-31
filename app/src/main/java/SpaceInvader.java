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
  private boolean isGameOver = false;
  private boolean isAutoTesting = false;
  private Properties properties = null;
  private StringBuilder logResult = new StringBuilder();
  private ArrayList<AlienGridLocation> powerfulAlienLocations = new ArrayList<AlienGridLocation>();
  private ArrayList<AlienGridLocation> invulnerableAlienLocations = new ArrayList<AlienGridLocation>();
  private ArrayList<AlienGridLocation> multipleAlienLocations = new ArrayList<AlienGridLocation>();
  private Alien[][] alienGrid = null;
  public SpaceInvader(Properties properties) {
    super(200, 100, 5, false);
    this.properties = properties;
  }

  private ArrayList<AlienGridLocation> convertFromProperty(String propertyName) {
    String powerfulAlienString = properties.getProperty(propertyName);
    ArrayList<AlienGridLocation> alienLocations = new ArrayList<>();
    if (powerfulAlienString != null) {
      String [] locations = powerfulAlienString.split(";");
      for (String location: locations) {
        String [] locationPair = location.split("-");
        int rowIndex = Integer.parseInt(locationPair[0]);
        int colIndex = Integer.parseInt(locationPair[1]);
        alienLocations.add(new AlienGridLocation(rowIndex, colIndex));
      }
    }

    return alienLocations;
  }

  private void setupAlienLocations() {
    powerfulAlienLocations = convertFromProperty("Powerful.locations");
    invulnerableAlienLocations = convertFromProperty("Invulnerable.locations");
    multipleAlienLocations = convertFromProperty("Multiple.locations");
  }

  private boolean arrayContains(ArrayList<AlienGridLocation>locations, int rowIndex, int colIndex) {
    for (AlienGridLocation location : locations) {
      if (location.rowIndex == rowIndex && location.colIndex == colIndex) {
        return true;
      }
    }

    return false;
  }

  /**
   * Create and set up all aliens
   *
   * Modified to work with different alien classes instead of
   * type strings by
   * @author DonLam
   */
  private void setupAliens() {
    setupAlienLocations();
    isAutoTesting = Boolean.parseBoolean(properties.getProperty("isAuto"));
    String aliensControl = properties.getProperty("aliens.control");
    List<String> movements = null;
    if (aliensControl != null) {
      movements = Arrays.asList(aliensControl.split(";"));
    }
    alienGrid = new Alien[nbRows][nbCols];
    for (int i = 0; i < nbRows; i++) {
      for (int k = 0; k < nbCols; k++) {
        Alien alien;
        if (arrayContains(powerfulAlienLocations, i, k)) {
          alien = new PowerfulAlien(i, k);
        } else if (arrayContains(invulnerableAlienLocations, i, k)) {
          alien = new InvulnerableAlien(i, k);
        } else if (arrayContains(multipleAlienLocations, i, k)) {
          alien = new MultipleAlien(i, k);
        } else {
          alien = new Alien(i, k);
        }
        addActor(alien, new Location(100 - 5 * nbCols + 10 * k, 10 + 10 * i));
        alien.setTestingConditions(isAutoTesting, movements);
        alienGrid[i][k] = alien;
      }
    }
  }

  private void setupSpaceShip() {
    SpaceShip ss = new SpaceShip(this);
    addActor(ss, new Location(100, 90));

    String spaceShipControl = properties.getProperty("space_craft.control");
    List<String> controls = null;
    if (spaceShipControl != null) {
      controls = Arrays.asList(spaceShipControl.split(";"));
    }

    ss.setTestingConditions(isAutoTesting, controls);
    addKeyListener(ss);
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
    logResult.append("Alien locations: ");
    for (int i = 0; i < nbRows; i++) {
      for (int j = 0; j < nbCols; j++) {
        Alien alienData = alienGrid[i][j];

        String isDeadStatus = alienData.isRemoved() ? "Dead" : "Alive";
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
  }

  public void notifyAliensMoveFast() {
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
  public void notifyAlienHit(List<Actor> actors) {
    for (Actor actor: actors) {
      System.out.println("HELLO");
      Alien alien = (Alien)actor;
      alien.gotHit();
      String alienData = String.format("%s@%d-%d",
              alien.getType(), alien.getRowIndex(), alien.getColIndex());
      logResult.append("An alien has been hit.");
      logResult.append(alienData + "\n");
    }
  }

  public void setIsGameOver(boolean isOver) {
    isGameOver = isOver;
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

  // Use the location to spawn a bomb
  public void spawnBomb(Location location){}

  /**
   * Create an explosion at the designated location
   * @param location: location for Explosion to be created at
   * @author DonLam
   */
  public void spawnExplosion(Location location) {
    Explosion explosion = new Explosion();
    addActor(explosion, location);
  }

}
