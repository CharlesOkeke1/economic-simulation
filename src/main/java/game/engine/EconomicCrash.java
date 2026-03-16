package game.engine;

import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import java.util.*;

public class EconomicCrash {
    public static void apply(Map<String, StateEconomy> states, FederalEconomy federal, int currentMonth) {
        /*ECONOMIC CRASH MODEL*/
        for (StateEconomy s : states.values()) {    
            if (currentMonth > 1) { //Should only occur after 1st month
                if (s.cash < 0) {
                    BailOutCalculator.stateBailOut(states, federal, s.name); //If reserve depletes federal government bailsout the state;
                }        
            }
        }
    }
}