package config;

public class DisplayConfig {
    private String displayState;
    private GameDifficulty difficulty;
    private boolean devType;

    public DisplayConfig() {}

    public String getDisplayState() {
        return this.displayState;
    }
    public void setDisplayState(String state) { this.displayState = state; }

    public GameDifficulty getDifficulty() {
        return this.difficulty;
    }
    public void setDifficulty(GameDifficulty diff) {
        this.difficulty = diff;
    }

    public boolean getDevType() {
        return this.devType;
    }
    public void setDevType(Boolean type) { this.devType = type; }

}
