package engine;
import economies.FederalEconomy;
import economies.StateEconomy;
import gui.AppMain;

import java.util.*;

public class BailOutCalculator {
    public static void stateBailOut(String BailedState) {
        StateEconomy state = AppMain.getStateMap().get(BailedState);
        FederalEconomy federal = AppMain.getFederal();

        double bailOut;
        bailOut = 1_000_000;
        if (state.getStateReserve() < 0) {
            federal.federalReserve -= bailOut; // Federal reserve sends money
            state.setCash(state.getCash() + bailOut); //bail out to operating cash
            state.setStateReserve(0);
            state.setInflationRate(state.getInflationRate() * 1.05); // Little inflation boost
            state.setCrashCount(state.getCrashCount() + 1); //Increase crash count
            System.out.println(state.getName() + " is experiencing financial collapse");
        } else {
            double withdraw = state.getStateReserve() * 0.33;
            state.setCash(state.getCash() + withdraw);
            state.setStateReserve(state.getStateReserve() - withdraw);
        }
    }
}