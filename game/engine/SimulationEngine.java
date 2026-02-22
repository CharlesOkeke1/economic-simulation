package game.engine;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import game.economies.SimulationResult;
import java.util.*;
import utils.MyUtils;

public class SimulationEngine {
    public static SimulationResult simulate(Map<String, StateEconomy> states, FederalEconomy federal,
        String playerStateName, String playerPolicy, int counter) {

        String[] policies = {"Raise Taxes", "Cut Taxes", "Invest Infrastructure", "Invest Education", "Boost Security", "Borrow", "Austerity", "Market Trader Support", "Subsidise Transport & Food"};
        Random rand = new Random();
        
        StateEconomy playerState = states.get(playerStateName);
        
        /*FEDERATION ACCOUNTING */
        FederalAccountingEngine.federalAccounting(states, federal);
        
        /*STATE POLICY IMPLEMENTATION*/
        for (StateEconomy s : states.values()) {
            String aiPolicy = AiPolicyMaker.smartChoice(states, federal, s.name);
            if (s != playerState) PolicyEngine.applyPolicy(states, federal, s.name, aiPolicy);
            else PolicyEngine.applyPolicy(states, federal, playerStateName, playerPolicy);

            if ((s.position < 6) && (counter > 0)) {
                if (s.name == playerStateName) MyUtils.SteppedPrinting("Policy applied by " + MyUtils.Ordinalize(s.position) + " position was " + playerPolicy + " by " + playerStateName, 30);
                else MyUtils.SteppedPrinting("Policy applied by " + MyUtils.Ordinalize(s.position) + " position was " + aiPolicy + " by " + s.name, 30);
            }
        }

        /*ECONOMIC CRASH MODEL*/
        for (StateEconomy s : states.values()) {
            if (counter > 1) { //Should only occur after 1st month
                
       
                if (s.cash < 0) {
                    s.stability -= 15;
                    s.infrastructure -= 10; //Assume government sells off some infrastructure
                    s.crashCount++;             
                    BailOutCalculator.stateBailOut(states, federal, s.name); //If reserve depletes federal government bailsout the state;
                }
                
            }
        }

        RankingEngine.calculateRank(states, federal);

        return new SimulationResult(states, federal);


    }
}