package game.config;

public class GameConfig {
    private final String chosenState;
    private final int totalMonths;
    private final GameDifficulty difficulty;
    private final boolean devType;

    public GameConfig(String chosenState, int totalMonths, GameDifficulty difficulty, boolean devType) {
        this.chosenState = chosenState;
        this.totalMonths = totalMonths;
        this.difficulty = difficulty;
        this.devType = devType;
    }

    public String getChosenState() {
        return this.chosenState;
    }

    public int getTotalMonths() {
        return this.totalMonths;
    }

    public GameDifficulty getDifficulty() {
        return this.difficulty;
    }

    public boolean getDevType() {
        return this.devType;
    }

}