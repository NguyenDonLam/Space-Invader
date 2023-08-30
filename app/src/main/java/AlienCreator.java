import java.util.ArrayList;
import java.util.Properties;

public class AlienCreator {

    // TODO: use singleton pattern and finish the createAlien function
    private static AlienCreator instance;
    private AlienCreator() {}
    public static AlienCreator getInstance() {
        if (instance == null) {
            instance = new AlienCreator();
        }
        return instance;
    }

    public ArrayList<Alien> createAliens(Properties properties, int nbRows, int nbCols){
        return new ArrayList<Alien>();
    }
}
