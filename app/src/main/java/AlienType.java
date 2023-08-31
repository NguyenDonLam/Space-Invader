public enum AlienType {
    Powerful("Powerful.locations"),
    Invulnerable("Invulnerable.locations"),
    Multiple("Multiple.locations");

    private String propertyName;

    AlienType(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {return propertyName;}

}
