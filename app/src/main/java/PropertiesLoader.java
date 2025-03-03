import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    public static Properties loadPropertiesFile(String propertiesFile) {
        try (InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream(propertiesFile)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            if (prop.getProperty("space_craft.control").equals("")) {
                prop.remove("space_craft.control");
            }

            if (prop.getProperty("Powerful.locations").equals("")) {
                prop.remove("Powerful.locations");
            }

            if (prop.getProperty("Invulnerable.locations").equals("")) {
                prop.remove("Invulnerable.locations");
            }

            if (prop.getProperty("Multiple.locations").equals("")) {
                prop.remove("Multiple.locations");
            }

            if (prop.getProperty("aliens.control").equals("")) {
                prop.remove("aliens.control");
            }

            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
