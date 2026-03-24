package engine;

import java.util.*;

import data.Constants;
import economies.FederalEconomy;
import economies.StateEconomy;
import economies.SimulationResult;
import events.EventTrigger;
import election.ElectionEngine;
import analytics.Telemetry;
import metrics.PolicyChecks;
import config.GameConfig;
import utils.MyUtils;

public class SimulationEngine {
    private Map<String, StateEconomy> states;
    private FederalEconomy federal;
    private GameConfig config;
    private ElectionEngine elect;
    private int currentMonth = 0;
    private boolean election = false;

    public SimulationEngine(Map<String, StateEconomy> states, FederalEconomy federal, GameConfig config) {

        this.states = states;
        this.federal = federal;
        this.config = config;
    }

    public SimulationResult simulateMonth(String playerPolicy) {
        String[] policies = {"Raise Taxes", "Cut Taxes", "Invest Infrastructure", "Invest Education", "Boost Security", "Borrow", "Austerity", "Market Trader Support", "Subsidise Transport & Food"};
        String playerStateName = config.getChosenState();
        StateEconomy playerState = states.get(playerStateName);
        
        /*FEDERATION ACCOUNTING */
        FederalAccountingEngine.federalAccounting();
        applyPolicies(playerState, playerPolicy);

        RankingEngine.calculateRank();
        currentMonth++;

        if ((currentMonth % 18) == 0) {
            ElectionEngine.runElection();
            this.election = true;
        }

        Telemetry.logMonth(currentMonth);
        return new SimulationResult(states, federal);
    }

    public static double getElectCost() {
        return ElectionEngine.getElectionCost();
    }

    public boolean isElection() {
        return this.election;
    }

    private void applyPolicies(StateEconomy playerState, String playerPolicy) {
        String playerStateName = playerState.getName();

        for (StateEconomy s : states.values()) {
            String policyToApply;
            EventTrigger.trigger(s.getName(), currentMonth);

            if (s == playerState) {
                policyToApply = playerPolicy;

            } else {
                policyToApply = AiPolicyMaker.smartChoice(s.getName(), config.getDifficulty());
                s.setPolicy(policyToApply);
                PolicyChecks.SimulationCheck(s.getName(), policyToApply);
            }

            /*STATE POLICY IMPLEMENTATION*/  
            if (s != playerState) {
                PolicyEngine.applyPolicy(s.getName(), policyToApply);
                EventTrigger.trigger(s.getName(), currentMonth);
            }else {
                PolicyEngine.applyPolicy(playerStateName, policyToApply);
                EventTrigger.trigger(playerStateName, currentMonth);
            }
        }
    }

    public String getPolicy() {
        Scanner sc = new Scanner(System.in);
        MyUtils.SteppedPrinting("What is your " + MyUtils.Ordinalize(currentMonth) + "  month's policy", Constants.REPORT_DELAY_TIME);
        return sc.nextLine();
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
