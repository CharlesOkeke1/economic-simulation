package events;

import economies.FederalEconomy;
import economies.StateEconomy;
import java.util.*;

public class ApplyEvent {
    public static void apply(Map<String, StateEconomy> states, FederalEconomy fed, Events type, String stateName) {
        StateEconomy state = states.get(stateName);

        /*EXAMPLE/BASIC IMPLEMENTATION OF EVENT APPLICATION*/
        switch(type) {
                case DEBT_CRISIS:
                    state.setDebt(state.getDebt() * 1.4);
                    fed.debtInterest *= 1.5;
                    state.setCash(state.getCash() * 0.8);
                    state.setStability(state.getStability() * 0.95);
                    break;

                case WAR:
                    state.setDebt(state.getDebt() * 1.2);
                    state.setCash(state.getCash() * 0.8);
                    state.setStability(state.getStability() * 0.7);
                    state.setPopulation((long)(state.getPopulation() * 0.85));
                    break;

                case INSURGENCY:
                    state.setDebt(state.getDebt() * 1.2);
                    state.setCash(state.getCash() * 0.9);
                    state.setStability(state.getStability() * 0.8);
                    state.setPopulation((long)(state.getPopulation() * 0.9));
                    break;

                case OIL_BOOM:
                    fed.oilPriceIndex = fed.oilPriceIndex + 100;
                    state.setStability(state.getStability() * 1.2);
                    break;

                case OIL_CRASH:
                    fed.oilPriceIndex = fed.oilPriceIndex - 100;
                    state.setStability(state.getStability() * 0.9);
                    break;

                case PANDEMIC:
                    state.setPopulation((long)(state.getPopulation() * 0.85));
                    state.setGdp(state.getGdp() * 0.925);
                    state.setStability(state.getStability() * 0.6);
                    state.setInflationRate(state.getInflationRate() * 1.5);
                    break;

                case FOREX_BOOM:
                    state.setGdp(state.getGdp() * 1.15);
                    break;

        }
    }
}
