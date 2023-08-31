// Alien.java
// Used for SpaceInvader

import ch.aplu.jgamegrid.*;

import java.util.List;

public class Alien extends Actor
{
  private final int maxNbSteps = 16;
  private int nbSteps;
  private static int stepSize = 1;
  protected int healthPoints = 1; // NDL changed visibility from private to protected
  private boolean isMoving = true;
  private boolean isAutoTesting;
  private List<String> movements;
  private int movementIndex = 0;
  private int rowIndex;
  private int colIndex;

  public Alien(int rowIndex, int colIndex)
  {
    super("sprites/alien.gif");
    setSlowDown(7);
    this.rowIndex = rowIndex;
    this.colIndex = colIndex;
  }

  /**
   * An extra constructor for its subclasses to use.
   * @param fileName: file path to the image of the Invulnerable Alien.
   * @param rowIndex: the row in which the alien will situate on.
   * @param colIndex: the column in which the alien will situate on.
   *
   * @author DonLam
   */
  public Alien(String fileName, int rowIndex, int colIndex)
  {
    super(fileName);
    setSlowDown(7);
    this.rowIndex = rowIndex;
    this.colIndex = colIndex;
  }


  public int getRowIndex() {
    return rowIndex;
  }

  public int getColIndex() {
    return colIndex;
  }

  public void reset()
  {
    nbSteps = 7;
  }

  public void setTestingConditions(boolean isAutoTesting, List<String> movements) {
    this.isAutoTesting = isAutoTesting;
    this.movements = movements;
  }

  private void checkMovements() {
    if (isAutoTesting) {
      if (movements != null && movementIndex < movements.size()) {
        String movement = movements.get(movementIndex);
        if (movement.equals("S")) {
          isMoving = false;
        } else if (movement.equals("M")) {
          isMoving = true;
        }
        movementIndex++;
      }
    }
  }

  public void act()
  {
    checkMovements();
    if (!isMoving) {
      return;
    }
    for (int i = 0; i < stepSize; i++) {
      if (nbSteps < maxNbSteps)
      {
        move();
        nbSteps++;
      }
      else
      {
        nbSteps = 0;
        int angle;
        if (getDirection() == 0)
          angle = 90;
        else
          angle = -90;
        turn(angle);
        move();
        turn(angle);
      }
      if (getLocation().y > 90)
        removeSelf();
    }
  }

  // Function to check if n is between a range of numbers
  private static boolean isBetween(int n, int lower, int upper) {
    return lower <= n && n <= upper;
  }

  // Function to take the number of shots and increase the alien's speed accordingly
  public static boolean setSpeed(int nbShots){
    int newStepSize;
    if (nbShots < 10) {
      newStepSize = 1;
    } else if (isBetween(nbShots, 10, 49)) {
      newStepSize = 2;
    } else if (isBetween(nbShots, 50, 99)) {
      newStepSize = 3;
    } else if (isBetween(nbShots, 100, 499)) {
      newStepSize = 4;
    } else {
      newStepSize = 5;
    }
    if (stepSize == newStepSize) return false;
    stepSize = newStepSize;
    return true;
  }

  /**
   * Processes when the alien got hit
   * @return whether it has been hit or not.
   * @author DonLam, AndreChiang
   */
  public boolean gotHit() {
    healthPoints--;
    SpaceInvader game = (SpaceInvader)gameGrid;
    if (healthPoints == 0) {
      game.spawnExplosion(getLocation());
      removeSelf();
    }
    return true;
  }

  /**
   * @return the type of the alien, to be used for Logging only
   * @author DonLam
   */
  public String getType() {
    return "alien";
  }
  public void setStep(int newStep) {
    nbSteps = newStep;
  }
  public int getStep() {
    return nbSteps;
  }
  public int getStepSize() {
    return stepSize;
  }
}
