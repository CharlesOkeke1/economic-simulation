package game.config;

public class GameConfig {
    private final String chosenState;
    private final int totalMonths;
    private final GameDifficulty difficulty;

    public GameConfig(String chosenState, int totalMonths, GameDifficulty difficulty) {
        this.chosenState = chosenState;
        this.totalMonths = totalMonths;
        this.difficulty = difficulty;
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


}