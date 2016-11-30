public enum DodgeballIntent {
    STANDINGS("StandingsIntent"),
    WHEN("WhenIntent");

    private final String intentName;

    DodgeballIntent(String intentName) {
        this.intentName = intentName;
    }

    public String getIntentName() {
        return intentName;
    }
}
