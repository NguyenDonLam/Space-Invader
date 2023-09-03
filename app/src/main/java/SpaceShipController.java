import ch.aplu.jgamegrid.GGActListener;
import ch.aplu.jgamegrid.GGKeyAdapter;
import ch.aplu.jgamegrid.Location;

import java.awt.event.KeyEvent;
import java.util.List;

public class SpaceShipController extends GGKeyAdapter implements GGActListener {
    private final SpaceShip spaceShip;
    private final SpaceInvader spaceInvader;
    private boolean isAutoTesting = false;
    private List<String> controls = null;
    private int controlIndex = 0;

    public SpaceShipController(SpaceInvader spaceInvader) {
        // Create a spaceship and add it to the game grid
        this.spaceInvader = spaceInvader;
        spaceShip = new SpaceShip();
        spaceShip.addCollisionActors(spaceInvader.getActors(Alien.class));
        spaceShip.addActorCollisionListener(spaceShip);
        this.spaceInvader.addActor(spaceShip, new Location(100, 90));
    }

    /**
     * Handle user input for the spaceship
     * @param keyEvent: the key that is pressed
     * @return do not consume
     */
    @Override
    public boolean keyPressed(KeyEvent keyEvent) {
        Location next = null;
        if (isAutoTesting) return false;

        // Take user input if the mode is not auto testing
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_LEFT:
                next = spaceShip.getLocation().getAdjacentLocation(Location.WEST);
                spaceShip.moveTo(next);
                break;

            case KeyEvent.VK_RIGHT:
                next = spaceShip.getLocation().getAdjacentLocation(Location.EAST);
                spaceShip.moveTo(next);
                break;

            case KeyEvent.VK_SPACE:
                spaceShip.shoot();
                break;
        }

        return false;
    }

    @Override
    public boolean keyReleased(KeyEvent keyEvent) {
        return false;
    }

    /**
     * Read auto-testing controls
     */
    @Override
    public void act() {
        // Take one control from the control list every simulation when auto testing
        if (isAutoTesting) {
            if (controls != null && controlIndex < controls.size()) {
                String control = controls.get(controlIndex);
                Location next = null;

                switch(control) {
                    case "L":
                        next = spaceShip.getLocation().getAdjacentLocation(Location.WEST);
                        spaceShip.moveTo(next);
                        break;

                    case "R":
                        next = spaceShip.getLocation().getAdjacentLocation(Location.EAST);
                        spaceShip.moveTo(next);
                        break;

                    case "F":
                        spaceShip.shoot();
                        break;
                    case "E":
                        spaceInvader.setIsGameOver(true, false, null);
                        break;
                }
                controlIndex++;
            }
        }
    }

    public void setTestingConditions(boolean isAutoTesting, List<String> controls) {
        this.isAutoTesting = isAutoTesting;
        this.controls = controls;
    }

    /**
     * Update collidable actors for the spaceship when new aliens are created
     */
    public void updateCollisionActors() {
        spaceShip.addCollisionActors(spaceInvader.getActors(Alien.class));
    }
}