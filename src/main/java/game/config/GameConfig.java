package game.config;

public class GameConfig {
    private String chosenState;
    private int totalMonths;
    private GameDifficulty difficulty;
    private boolean devType;

    public GameConfig() {}

    public String getChosenState() {
        return this.chosenState;
    }
    public void setChosenState(String state) { this.chosenState = state; }

    public int getTotalMonths() {
        return this.totalMonths;
    }
    public void setTotalMonths(int m) { this.totalMonths = m; }

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