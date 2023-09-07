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
