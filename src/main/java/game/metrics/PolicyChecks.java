package game.metrics;

import game.economies.StateEconomy;
import java.util.Map;

public class PolicyChecks {
    public static void SimulationCheck(Map<String, StateEconomy> states, String name, String policyToApply) {
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
        state.getMemory().setLastNominalGdp(state.gdp);
        state.getMemory().setLastRealGdp(state.realGdp);
        state.getMemory().setLastDebt(state.debt);
        state.getMemory().setLastCash(state.cash);
        state.getMemory().setLastStability(state.stability);

        state.getMemory().updateRollingGrowth(state.gdpGrowth);
        state.getMemory().updateRollingInflation(state.inflationRate);
        state.getMemory().updateRollingProfit(state.monthlyProfit);
        state.getMemory().updateRollingDebtRatio(state.debtToGdpRatio);
        state.getMemory().updateInflationStreak(state.inflationRate);
        state.getMemory().updateStabilityChange(state.stability);

    }
}
