package game.engine;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import java.util.*;

public class BailOutCalculator {
    public static void stateBailOut(Map<String, StateEconomy> states, FederalEconomy federal, String BailedState) {
        StateEconomy state = states.get(BailedState);

        double bailOut;
        bailOut = 1_000_000;
        federal.federalReserve -= bailOut; //Fedral reserve sends money to failing state
        state.gdp += (bailOut * 0.4); //Give small Gdp boost Support
        state.cash += bailOut * 0.75; //States receive federal bail out if they are low on reserves.
        state.stateReserve += bailOut * 0.25;
        //state.inflationRate += 0.025;
        state.crashCount++;
        System.out.println(state.name + " is experiencing financial collapse");
    }
}