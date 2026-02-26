package game.engine;

import java.util.*;

import game.data.Constants;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import game.economies.SimulationResult;

import game.config.GameConfig;
import game.engine.FederalAccountingEngine;
import game.engine.PolicyEngine;
import game.engine.AiPolicyMaker;
import game.engine.RankingEngine;
import game.engine.EconomicCrash;

import game.ui.PrintReports;
import game.ui.Initializer;

import utils.MyUtils;

public class SimulationEngine {
    private Map<String, StateEconomy> states;
    private FederalEconomy federal;
    private GameConfig config;
    private int currentMonth;

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
        

        /*ECONOMIC CRASH MODEL*/
        if (currentMonth > 1) { //Should only occur after 1st month
            new EconomicCrash().apply(states, federal, currentMonth);        
        }

        RankingEngine.calculateRank(states, federal);
        currentMonth++;
        return new SimulationResult(states, federal);
    }

    private void applyPolicies(StateEconomy playerState, String playerPolicy) {
        String playerStateName = playerState.name;

        for (StateEconomy s : states.values()) {
            String policyToApply;

            if (s == playerState) {
                policyToApply = playerPolicy;
            } else {
                policyToApply = AiPolicyMaker.smartChoice(states, federal, s.name, config.getDifficulty());
            }

            /*STATE POLICY IMPLEMENTATION*/  
            if (s != playerState) PolicyEngine.applyPolicy(states, federal, s.name, policyToApply);
            else PolicyEngine.applyPolicy(states, federal, playerStateName, policyToApply);
            
            if (s.position < 6 && currentMonth > 0) {
                String msg = "Policy applied by " + s.name + " at " + MyUtils.Ordinalize(s.position)
                        + " position was " + policyToApply;
                MyUtils.SteppedPrinting(msg, Constants.REPORT_DELAY_TIME);
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
        Scanner sc = new Scanner(System.in);
        HashMap<String, StateEconomy> initialStates = new HashMap<>();
        for (Map.Entry<String, StateEconomy> entry : states.entrySet()) {
            initialStates.put(entry.getKey(), new StateEconomy(entry.getValue()));
            
        }

        String admin = "admin"; //Prompt user for admin password which is used for testing purposes
        String policy = "";
        System.out.println("Are you a developer? If so enter the developer password. If you are not a developer just enter no: ");
        String devPass = sc.nextLine();

        PrintReports.printStateReport(initialStates, federal, config.getChosenState(), currentMonth, "Initial"); 
        PrintReports.printFederationReport(federal, config.getTotalMonths(), "Initial");  

        while (currentMonth < config.getTotalMonths()) {
            // Get User policy
            String playerPolicy = getPolicy();

            // Simulate one month
            simulateMonth(playerPolicy);

            // Print monthly report
            PrintReports.printStateReport(states, federal, config.getChosenState(), currentMonth, "Monthly");
        }

        // Final report after loop ends
        List<StateEconomy> sorted = new ArrayList<>(states.values());
        sorted.sort(Comparator.comparingInt(s -> s.position));
        PrintReports.printStateReport(states, federal, config.getChosenState(), currentMonth, "Final"); 
        for (StateEconomy s : sorted) {              
            MyUtils.SteppedPrinting(s.name + "'s peformance score final month : " + String.format("%.2f", s.rankingScore) + " coming in " + MyUtils.Ordinalize(s.position) + " position.", 10);
            MyUtils.SteppedPrinting(s.name + "'s Real GDP is: " + MyUtils.formatNumber(s.gdp) + " Naira, State Reserve is: " + MyUtils.formatNumber(s.stateReserve) + " Naira and Operating Cash " + MyUtils.formatNumber(s.cash) + " Naira" , 10);
             
        }  

        System.out.println(" ");
        PrintReports.printFederationReport(federal, config.getTotalMonths(), "Final");
    }
}
