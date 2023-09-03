// Alien.java
// Used for SpaceInvader

import ch.aplu.jgamegrid.Actor;

import java.util.List;

public class Alien extends Actor {
    private static int stepSize = 1;
    private final int maxNbSteps = 16;
    protected int healthPoints = 1; // NDL changed visibility from private to protected
    private int nbSteps;
    private boolean isMoving = true;
    private boolean isAutoTesting;
    private List<String> movements;
    private int movementIndex = 0;
    private final int rowIndex;
    private final int colIndex;

    public Alien(int rowIndex, int colIndex) {
        super("sprites/alien.gif");
        setSlowDown(7);
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    /**
     * An extra constructor for its subclasses to use.
     *
     * @param fileName: file path to the image of the Invulnerable Alien.
     * @param rowIndex: the row in which the alien will situate on.
     * @param colIndex: the column in which the alien will situate on.
     * @author DonLam
     */
    public Alien(String fileName, int rowIndex, int colIndex) {
        super(fileName);
        setSlowDown(7);
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    // Function to check if n is between a range of numbers
    private static boolean isBetween(int n, int lower, int upper) {
        return lower <= n && n <= upper;
    }

    /**
     * Move an alien by a certain number of steps based on the current speed
     *
     * @param nbShots : number of shots that the spaceship has fired
     * @return whether the speed is increased
     */
    public static boolean setSpeed(int nbShots) {
        int newStepSize;
        // Increase the alien's speed accordingly to nbShots
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
        // Check if the speed has changed
        if (stepSize == newStepSize) return false;
        stepSize = newStepSize;
        return true;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void reset() {
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

    /**
     * Move an alien by a certain number of steps based on the current speed
     */
    public void act() {
        checkMovements();
        if (!isMoving) {
            return;
        }
        for (int i = 0; i < stepSize; i++) {
            if (nbSteps < maxNbSteps) {
                move();
                nbSteps++;
            } else {
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

    /**
     * Processes when the alien got hit
     *
     * @return whether it has been hit or not.
     * @author DonLam, AndreChiang
     */
    public boolean gotHit() {
        healthPoints--;
        SpaceInvader game = (SpaceInvader) gameGrid;
        // Spawn an explosion at the alien's location if the alien is dead
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

    public int getStep() {
        return nbSteps;
    }

    public void setStep(int newStep) {
        nbSteps = newStep;
    }

    /**
     * @return how far Alien is stepping per step.
     */
    public int getStepSize() {
        return stepSize;
    }
}
