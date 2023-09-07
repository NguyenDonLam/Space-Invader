/**
 * The class handles the spawning of the original aliens in a space invader game
 *
 * @author DonLam, Chi-Yuan, AndreChiang
 */
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlienCreator {

    private static AlienCreator instance;

    /**
     * Gets the instance of the AlienCreator class, else create one (Singleton pattern)
     * @return the current instance of AlienCreator
     *
     * @author AndreChiang
     */
    public static AlienCreator getInstance() {
        if (instance == null) {
            instance = new AlienCreator();
        }
        return instance;
    }

    /**
     * Create the Aliens as specified in the provided properties
     *
     * @param properties: the property containing initialisation information of the game
     * @param nbRows:     number of rows of aliens
     * @param nbCols:     number of columns of aliens
     * @return an ArrayList of 1D-Array of Aliens, created on all specified location
     *
     * @author DonLam, Chi-Yuan
     */
    public ArrayList<Alien[]> createAliens(Properties properties, int nbRows, int nbCols) {
        ArrayList<Alien[]> alienGrid = new ArrayList<Alien[]>();
        String search = "(\\d+)-(\\d+)";
        Pattern pattern = Pattern.compile(search);

        for (int i = 0; i < nbRows; i++) {
            alienGrid.add(new Alien[nbCols]);
            for (int j = 0; j < nbCols; j++) {
                alienGrid.get(i)[j] = new Alien(i, j);
            }
        }
        for (AlienType alienType : AlienType.values()) {
            String locations = properties.getProperty(alienType.getPropertyName());
            if (locations != null) {
                Matcher matcher = pattern.matcher(locations);
                while (matcher.find()) {
                    int rowIndex = Integer.parseInt(matcher.group(1));
                    int colIndex = Integer.parseInt(matcher.group(2));
                    if (alienType == AlienType.Powerful) {
                        alienGrid.get(rowIndex)[colIndex] = new PowerfulAlien(rowIndex, colIndex);
                    } else if (alienType == AlienType.Invulnerable) {
                        alienGrid.get(rowIndex)[colIndex] = new InvulnerableAlien(rowIndex, colIndex);
                    } else if (alienType == AlienType.Multiple) {
                        alienGrid.get(rowIndex)[colIndex] = new MultipleAlien(rowIndex, colIndex);
                    }
                }
            }
        }
        return alienGrid;
    }


}
