package metrics;

import economies.FederalEconomy;
import economies.StateEconomy;
import gui.AppMain;

import java.util.HashMap;
import java.util.Map;

public class PolicyChecks {
    public static void SimulationCheck(String name, String policyToApply) {
        HashMap<String, StateEconomy> states = AppMain.getStateMap();
        int monthSinceInfra = 0;
        int monthSinceEdu = 0;
        StateEconomy state = states.get(name);

        state.getMemory().updatePolicyTracking(policyToApply);

        if (policyToApply.equals("Invest Infrastructure")) {
            state.getMemory().incrementInfraCount(); 
        } else {
            state.getMemory().resetInfraCount();
            monthSinceInfra++;
            state.getMemory().setMonthsSinceInfra(monthSinceInfra);
        }

        if (policyToApply.equals("Invest Education")) {
            state.getMemory().incrementEducationCount(); 
        } else {
            state.getMemory().resetEducationCount();
            monthSinceEdu++;
            state.getMemory().setMonthsSinceEducation(monthSinceEdu);
        }

        /*AI state.getMemory() UPDATES*/
        state.getMemory().setLastNominalGdp(state.getGdp());
        state.getMemory().setLastRealGdp(state.getRealGdp());
        state.getMemory().setLastDebt(state.getDebt());
        state.getMemory().setLastCash(state.getCash());
        state.getMemory().setLastStability(state.getStability());

        state.getMemory().updateRollingGrowth(state.getGdpGrowth());
        state.getMemory().updateRollingInflation(state.getInflationRate());
        state.getMemory().updateRollingProfit(state.getMonthlyProfit());
        state.getMemory().updateRollingDebtRatio(state.getDebtToGdpRatio());
        state.getMemory().updateInflationStreak(state.getInflationRate());
        state.getMemory().updateStabilityChange(state.getStability());

    }
}
