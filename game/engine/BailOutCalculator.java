package game.engine;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import java.util.*;

public class BailOutCalculator {
    public static void stateBailOut(Map<String, StateEconomy> states, FederalEconomy federal, String BailedState) {
        StateEconomy state = states.get(BailedState);

        double bailOut;
        bailOut = Math.abs(state.cash);
        federal.federalReserve -= bailOut; //Fedral reserve sends money to failing state
        state.gdp += (bailOut * 1.17); //Give small Gdp boost Support
        state.cash += bailOut; //States receive federal bail out if they are low on reserves.
    }
}