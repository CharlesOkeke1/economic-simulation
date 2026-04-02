package engine;

import java.util.*;

import config.DisplayConfig;
import data.Constants;
import economies.FederalEconomy;
import economies.StateEconomy;
import economies.SimulationResult;
import events.EventTrigger;
import election.ElectionEngine;
import analytics.Telemetry;
import AI.PolicyChecks;
import config.GameConfig;
import utils.MyUtils;

public class SimulationEngine {
    private Map<String, StateEconomy> states;
    private FederalEconomy federal;
    private GameConfig config;
    private DisplayConfig displayConfig;
    private ElectionEngine elect;
    private int currentMonth = 0;

    public SimulationEngine(Map<String, StateEconomy> states, FederalEconomy federal, GameConfig config, DisplayConfig displayConfig) {

        this.states = states;
        this.federal = federal;
        this.config = config;
        this.displayConfig = displayConfig;
    }

    public SimulationResult simulateMonth(String playerPolicy) {
        String playerStateName = config.getChosenState();
        StateEconomy playerState = states.get(playerStateName);
        
        /*FEDERATION ACCOUNTING */
        FederalAccountingEngine.federalAccounting();
        applyPolicies(playerState, playerPolicy);
        EventTrigger.trigger();

        RankingEngine.calculateRank();
        currentMonth++;

        if ((currentMonth % Constants.ELECTION_SPACING) == 0) {
            ElectionEngine.runElection();
        }

        Telemetry.logMonth(currentMonth);
        return new SimulationResult(states, federal);
    }

    public static double getElectCost() {
        return ElectionEngine.getElectionCost();
    }


    private void applyPolicies(StateEconomy playerState, String playerPolicy) {
        String playerStateName = playerState.getName();

        for (StateEconomy s : states.values()) {
            String policyToApply;
            //EventTrigger.trigger(s.getName(), currentMonth);

            if (s == playerState) {
                policyToApply = playerPolicy;

            } else {
                policyToApply = AiPolicyMaker.smartChoice(s.getName(), displayConfig.getDifficulty());
                s.setPolicy(policyToApply);
                PolicyChecks.SimulationCheck(s.getName(), policyToApply);
            }

            /*STATE POLICY IMPLEMENTATION*/  
            if (s != playerState) {
                PolicyEngine.applyPolicy(s.getName(), policyToApply);
            }else {
                PolicyEngine.applyPolicy(playerStateName, policyToApply);
            }
        }
    }
    public int getCurrentMonth() {
        return currentMonth;
    }

    public void run() {     
        HashMap<String, StateEconomy> initialStates = new HashMap<>();
        String playName = config.getChosenState();
        StateEconomy st = states.get(playName);
        if(currentMonth >= config.getTotalMonths()) return;

        // Get User policy
        String playerPolicy = "";
        if (config.getDevType()) {
            playerPolicy = AiPolicyMaker.smartChoice(st.getName(), config.getDifficulty());
            PolicyChecks.SimulationCheck(config.getChosenState(), playerPolicy);
            st.setPolicy(playerPolicy);
        } else {
            playerPolicy = st.getPolicy();
        }
        simulateMonth(playerPolicy); // Simulate one month

    }
}
