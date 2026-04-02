package events;

import economies.FederalEconomy;
import economies.StateEconomy;
import gui.AppMain;

import java.util.*;

public class ApplyEvent {
    public static void apply(Events type, String stateName) {
        FederalEconomy fed = AppMain.getFederal();
        StateEconomy state = AppMain.getStateMap().get(stateName);

        switch(type) {

            case DEBT_CRISIS:
                double debtShock = state.getDebt() * 0.18 + state.getRealGdp() * 0.12;
                state.setDebt(state.getDebt() + debtShock);

                state.setCash(state.getCash() * 0.88);
                state.setInflationRate(state.getInflationRate() * 1.15);
                state.setStability(state.getStability() * 0.92);

                // mild structural damage (recoverable)
                state.setTaxRate(Math.max(0.05, state.getTaxRate() * 0.98));

                // controlled global effect
                fed.debtInterest = Math.min(fed.debtInterest * 1.08, 0.22);
                break;

            case WAR:
                state.setDebt(state.getDebt() + state.getRealGdp() * 0.22);
                state.setCash(state.getCash() * 0.82);
                state.setStability(state.getStability() * 0.78);
                state.setPopulation((long)(state.getPopulation() * 0.92));

                state.setInfrastructure(Math.max(0, state.getInfrastructure() - 4));
                break;

            case INSURGENCY:
                state.setCash(state.getCash() * 0.9);
                state.setDebt(state.getDebt() + state.getRealGdp() * 0.08);
                state.setStability(state.getStability() * 0.88);

                state.setInfrastructure(Math.max(0, state.getInfrastructure() - 2));
                break;

            case OIL_BOOM:
                fed.oilPriceIndex *= 1.12;
                state.setCash(state.getCash() * 1.07);
                state.setStability(Math.min(100, state.getStability() * 1.05));
                break;

            case OIL_CRASH:
                fed.oilPriceIndex *= 0.88;
                state.setCash(state.getCash() * 0.93);
                state.setStability(state.getStability() * 0.93);

                // slight real impact
                state.setRealGdp(state.getRealGdp() * 0.97);
                break;

            case PANDEMIC:
                state.setPopulation((long)(state.getPopulation() * 0.93));
                state.setRealGdp(state.getRealGdp() * 0.92);
                state.setCash(state.getCash() * 0.9);
                state.setStability(state.getStability() * 0.78);

                state.setInflationRate(state.getInflationRate() * 1.2);
                break;

            case FOREX_BOOM:
                state.setRealGdp(state.getRealGdp() * 1.08);
                state.setCash(state.getCash() * 1.05);
                break;
        }
    }
}
