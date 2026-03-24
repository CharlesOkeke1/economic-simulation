package engine;

import economies.FederalEconomy;
import economies.StateEconomy;
import gui.AppMain;

import java.util.*;

public class EconomicCrash {
    public static void apply(int currentMonth) {
        HashMap<String, StateEconomy> states = AppMain.getStateMap();
        /*ECONOMIC CRASH MODEL*/
        for (StateEconomy s : states.values()) {    
            if (currentMonth > 1) { //Should only occur after 1st month
                if (s.getCash() < 0) {
                    BailOutCalculator.stateBailOut(s.getName()); //If reserve depletes federal government bails out the state;
                }        
            }
        }
    }
}