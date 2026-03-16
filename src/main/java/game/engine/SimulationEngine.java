package game.engine;

import java.util.*;
import game.data.Constants;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import game.economies.SimulationResult;
import game.events.EventTrigger;
import game.election.ElectionEngine;
import game.gui.AppMain;
import game.analytics.Telemetry;
import game.metrics.PolicyChecks;
import game.config.GameConfig;
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
        FederalAccountingEngine.federalAccounting(states, federal);
        applyPolicies(playerState, playerPolicy);



        RankingEngine.calculateRank(states, federal);
        currentMonth++;


        if ((currentMonth % 18) == 0) {
            elect.elect(states, federal);
            this.election = true;
        }

        Telemetry.logMonth(currentMonth, states, federal);
        return new SimulationResult(states, federal);
    }

    public boolean isElection() {
        return this.election;
    }

    private void applyPolicies(StateEconomy playerState, String playerPolicy) {
        String playerStateName = playerState.name;

        for (StateEconomy s : states.values()) {
            String policyToApply;
            EventTrigger.trigger(states, federal, s.name, currentMonth);

            if (s == playerState) {
                policyToApply = playerPolicy;

            } else {
                policyToApply = AiPolicyMaker.smartChoice(states, federal, s.name, config.getDifficulty());
                s.setPolicy(policyToApply);
                PolicyChecks.SimulationCheck(states, s.name, policyToApply);
            }

            /*STATE POLICY IMPLEMENTATION*/  
            if (s != playerState) {
                PolicyEngine.applyPolicy(states, federal, s.name, policyToApply);
                EventTrigger.trigger(states, federal, s.name, currentMonth);
            }else {
                PolicyEngine.applyPolicy(states, federal, playerStateName, policyToApply);
                EventTrigger.trigger(states, federal, playerStateName, currentMonth);
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
                playerPolicy = AiPolicyMaker.smartChoice(states, federal, st.name, config.getDifficulty());
                PolicyChecks.SimulationCheck(states, config.getChosenState(), playerPolicy);
                st.setPolicy(playerPolicy);
            } else {
                playerPolicy = st.getPolicy();
            }

            simulateMonth(playerPolicy); // Simulate one month
    }
}
