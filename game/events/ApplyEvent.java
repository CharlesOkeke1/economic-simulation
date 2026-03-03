package game.events;

import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import java.util.*;

public class ApplyEvent {
    public static void apply(Map<String, StateEconomy> states, FederalEconomy fed, Events type, String stateName) {
        StateEconomy state = states.get(stateName);

        /*EXAMPLE/BASIC IMPLEMENTATION OF EVENT APPLICATION*/
        switch(type) {
            case DEBT_CRISIS:
                state.debt *= 1.4;
                fed.debtInterest *= 1.5;
                state.cash *= 0.8;
                state.stability *= 0.95;
                break;
            
            case WAR:
                state.debt *= 1.2;
                state.cash *= 0.8;
                state.stability *= 0.7;
                state.population *= 0.85;
                break;

            case INSURGENCY:
                state.debt *= 1.2;
                state.cash *= 0.9;
                state.stability *= 0.8;
                state.population *= 0.9;
                break;

            case OIL_BOOM:
                fed.oilPriceIndex += 100;
                state.stability *= 1.2;
                break;

            case OIL_CRASH:
                fed.oilPriceIndex -= 100;
                state.stability *= 0.9;
                break;

            case PANDEMIC:
                state.population *= 0.85;
                state.gdp *= 0.925;
                state.stability *= 0.6;
                state.inflationRate *= 1.5;
                break;

            case FOREX_BOOM:
                state.gdp *= 1.15;
                break;
        }
    }
}
