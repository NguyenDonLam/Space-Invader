/**
 * The enum represents the extra 3 alien types within a property file,
 * for iteration in AlienCreator only.
 * Tuesday-9AM-Team1a
 * @author Chi-Yuan
 */
public enum AlienType {
    Powerful("Powerful.locations"),
    Invulnerable("Invulnerable.locations"),
    Multiple("Multiple.locations");

    private final String propertyName;

    AlienType(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

}
