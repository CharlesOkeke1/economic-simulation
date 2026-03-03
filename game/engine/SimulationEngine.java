package game.engine;

import java.util.*;
import game.data.Constants;
import game.economies.*;
import game.events.EventTrigger;
import game.metrics.PolicyChecks;
import game.config.GameConfig;
import game.ui.PrintPolicies;
import game.ui.PrintReports;
import utils.MyUtils;
import utils.EnumToString;
import game.metrics.*;

public class SimulationEngine {
    private Map<String, StateEconomy> states;
    private FederalEconomy federal;
    private GameConfig config;
    private int currentMonth = 1;

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
            
            /*
            if (s.position < 6 && currentMonth > 1) {
                String msg = "Policy applied by " + s.name + " at " + MyUtils.Ordinalize(s.position)
                        + " position was " + policyToApply;
                MyUtils.SteppedPrinting(msg, Constants.REPORT_DELAY_TIME);
            }
            */
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
        for (Map.Entry<String, StateEconomy> entry : states.entrySet()) {
            initialStates.put(entry.getKey(), new StateEconomy(entry.getValue()));    
        }   
        
        //MyUtils.SteppedPrinting("GAME DIFFICULTY: " + EnumToString.convert(config.getDifficulty()," "), Constants.REPORT_DELAY_TIME);
        //PrintReports.printStateReport(initialStates, federal, config.getChosenState(), currentMonth, "Initial"); 
        //PrintReports.printFederationReport(federal, config.getTotalMonths(), "Initial");  

        String playName = config.getChosenState();
        StateEconomy st = states.get(playName);

        while (currentMonth <= config.getTotalMonths()) {
            // Get User policy
            String playerPolicy = "";
            //PrintPolicies.policies();

            if (config.getDevType()) {
                playerPolicy = AiPolicyMaker.smartChoice(states, federal, st.name, config.getDifficulty());
                PolicyChecks.SimulationCheck(states, config.getChosenState(), playerPolicy);
                //MyUtils.SteppedPrinting("Your policy for month " + currentMonth + " is: " + playerPolicy + ".", Constants.REPORT_DELAY_TIME);
            } else {
                playerPolicy = getPolicy();
            }
            
            // Simulate one month

            simulateMonth(playerPolicy);
            System.out.println("Current Month: " + currentMonth);

            // Print monthly report
            //PrintReports.printStateReport(states, federal, config.getChosenState(), currentMonth, "Monthly");
            //PrintReports.printFederationReport(federal, currentMonth, "Monthly"); 
        }

        // Final report after loop ends
        List<StateEconomy> sorted = new ArrayList<>(states.values());
        sorted.sort(Comparator.comparingInt(s -> s.position));
        //PrintReports.printStateReport(states, federal, config.getChosenState(), currentMonth, "Final"); 
        for (StateEconomy s : sorted) {              
            //MyUtils.SteppedPrinting(s.name + "'s peformance score final month : " + String.format("%.2f", s.rankingScore) + " coming in " + MyUtils.Ordinalize(s.position) + " position.", 1);
            //MyUtils.SteppedPrinting(s.name + "'s Real GDP is: " + MyUtils.formatNumber(s.gdp) + " Naira, State Reserve is: " + MyUtils.formatNumber(s.stateReserve) + " Naira and Operating Cash " + MyUtils.formatNumber(s.cash) + " Naira" , 1);
             
        }  

        System.out.println(" ");
        //PrintReports.printFederationReport(federal, config.getTotalMonths(), "Final");
    }
}
