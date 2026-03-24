package engine;

import economies.FederalEconomy;
import economies.StateEconomy;
import config.GameDifficulty;
import gui.AppMain;

import java.util.*;

public class AiPolicyMaker {

    public static class PolicyClass {
        String policyName;
        int policyScore;

        public PolicyClass(String policyName, int policyScore) {
            this.policyName = policyName;
            this.policyScore = policyScore;
        }

        public int getPolicyScore() {
            return this.policyScore;
        }

        public void reducePolicyScore(int val) {
            this.policyScore -= val;
        }
    }

    public static String smartChoice(String stat, GameDifficulty difficulty) {
        HashMap<String, StateEconomy> states = AppMain.getStateMap();
        FederalEconomy federal = AppMain.getFederal();
        StateEconomy state = states.get(stat);
        HashMap<String, PolicyClass> policyMap = new HashMap<>();
        String policy = "";        

        int raiseTaxScore = 0;
        int cutTaxScore = 0; 
        int securityScore = 0;
        int borrowScore = 0; 
        int eduScore = 0; 
        int infraScore = 0; 
        int traderSupportScore = 0; 
        int subsidyScore = 0; 
        int austerityScore =  0; 

        /*FISCAL CRISIS POLICIES*/
        //Monthly Profit Shortage
        if (state.getMonthlyProfit() < 0.03 * state.getGdp()) {
            if (state.getTaxRate() > 0.3) raiseTaxScore += 6;
            else austerityScore += 4;
        }

        //Policy Spend too high
        if (state.getPolicySpend() > 0.0275 * state.getGdp()) austerityScore += 4;
      
        //Debt to gdp too high
        if (state.getDebtToGdpRatio() > 0.65) {
            if (state.getStability() > 45) austerityScore += 4;
            else raiseTaxScore += 6;
        }


        /*GROWTH ORIENTED POLICIES*/
        //Infrastructure too low
        if (state.getInfrastructure() < 20 && state.getInfrastructure() > 15) infraScore += 4;

        //Societal stability and debt rate are good
        if (state.getStability() > 55 && state.getDebtToGdpRatio() < 0.4) infraScore += 4;

        //Infrastructure too low
        if (state.getGdpGrowth() < 0) eduScore += 6;

        // Population growth is slowing down
        if (state.getStability() < 15) securityScore += 5;


        /*POLITICAL AND SOCIETAL SURVIVAL POLICIES*/
        //Stability Low
        int stabCount = 0;
        if (state.getStability() < 35) {
            switch(stabCount) { 
                case 0:
                    securityScore += 5;
                    break;
                case 1:
                    subsidyScore += 8;
                    break;
            }
            stabCount++; //Allows us to apply policies in the next month if conditions persist.
        }
        
        //Low but not too bad stability, run social 
        if (state.getStability() > 35 && state.getStability() < 45) {
            traderSupportScore += 8;
        } 

        //High Stability and decent cash flow
        if (state.getStability() > 63 && state.getCash() > (0.35 * state.getGdp())) cutTaxScore += 7;

        /*STRATEGIC BORROWING*/ 
        int infraCount = 0;
        if (state.getInfrastructure() <= 15) {
            switch(infraCount) { 
                case 0:
                    borrowScore += 7;
                    break;
                case 1:
                    infraScore += 3;
                    break;
            }
            infraCount++; //Allows us to apply policies in the next month if conditions persist.
        }

        /*REASONABLE GROWTH WINDOW*/
        if ((state.getCash() > 0.20 * state.getGdp()) &&(state.getDebtToGdpRatio() < 0.50) && (state.getStability() > 50)) {
            if (Math.random() > 0.65) infraScore += 4;
            else eduScore += 6;
        }

        switch (state.getGovernmentType()) {
            case GROWTH_FOCUSED: //Growth focused economies care more about investments
                infraScore += 6;
                eduScore += 6;
                break;

            case SOCIALIST: //socialists are more about welfare and subsidies
                subsidyScore += 6;
                cutTaxScore += 5;
                traderSupportScore += 5;
                break;

            case FISCAL_CONSERVATIVE: //Heavy on reducing government spending
                austerityScore += 5;
                raiseTaxScore += 5;
                break;

            case DEBT_FIGHTER: //Debt clearers are also more austerous
                austerityScore += 4;
                break;

            case SECURITY_ORIENTED: //Security Oriented value boosting security and ensuring stability
                securityScore += 6;
                break;
        }

        /*state.getMemory() IMPLICATIONS*/
        //GDP EFFECTS
        if (state.getMemory().getLastRealGdp() < state.getRealGdp()) {
            infraScore += 4;
            eduScore += 4;
            traderSupportScore += 5;
            subsidyScore += 5;
            austerityScore -= 4;
        }

        //DEBT EFFECTS
        if (state.getDebt() > state.getMemory().getLastDebt()) {
            raiseTaxScore += 5;
            borrowScore -= 4;
            austerityScore += 4;
            subsidyScore -= 5;
        }

        //CASH EFFECTS
        if (state.getCash() < state.getMemory().getLastCash()) {
            raiseTaxScore += 5;
            borrowScore += 5;
            austerityScore += 4;
            infraScore -= 3;
            eduScore -= 4;
        }

        //STABILITY EFFECTS
        if (state.getStability() < state.getMemory().getLastStability()) {
            securityScore += 6;
            subsidyScore += 4;
            traderSupportScore += 4;
            raiseTaxScore -= 4;
            austerityScore -= 3;
        }

        /*ROLLING METRICS*/
        //GROWTH EFFECTS
        if (state.getMemory().getRollingGrowth12M() < 0) {
            infraScore += 6;
            //eduScore += 5;
            traderSupportScore += 4;
            subsidyScore += 4;
        } else if (state.getMemory().getRollingGrowth12M() > 0) {
            austerityScore += 4;
            raiseTaxScore += 5;
            subsidyScore -= 3;
        }

        if (state.getMemory().getRollingInflation12M() > 0) {
            austerityScore += 4;
            raiseTaxScore += 4;
            subsidyScore -= 3;
            infraScore -= 3;
            traderSupportScore -= 3;
        }

        if (state.getMemory().getRollingProfit12M() < 0) {
            raiseTaxScore += 3;
            austerityScore += 4;
            borrowScore += 6;
            subsidyScore += 4;
            infraScore += 2;
        }

        if (state.getMemory().getRollingDebtRatio12M() > state.getDebtToGdpRatio()) {
            raiseTaxScore += 3;
            austerityScore += 3;
            borrowScore -= 3;
            infraScore -= 2;
        } 

        //STREAK EFFECTS
        if (state.getMemory().getDeficitStreak() > 3) {
            raiseTaxScore += 3;
            austerityScore += 3;
            borrowScore += 4;
        }

        if (state.getMemory().getHighInflationStreak() > 2) {
            raiseTaxScore += 3;
            austerityScore += 3;
            infraScore -= 2;
            subsidyScore -= 3;
        } 

        if (state.getMemory().getMonthsSinceInfra() > 3) {
            infraScore += 7;
        }

        if (state.getMemory().getMonthsSinceEducation() > 3) {
            eduScore += 6;
        }

        //Put all scores in a map with their names
        policyMap.put("Cut Taxes", new PolicyClass("Cut Taxes", cutTaxScore));
        policyMap.put("Raise Taxes", new PolicyClass("Raise Taxes", raiseTaxScore));
        policyMap.put("Invest Infrastructure", new PolicyClass("Invest Infrastructure", infraScore));
        policyMap.put("Invest Education", new PolicyClass("Invest Education", eduScore));
        policyMap.put("Boost Security", new PolicyClass("Boost Security", securityScore));
        policyMap.put("Borrow", new PolicyClass("Borrow", borrowScore));
        policyMap.put("Austerity", new PolicyClass("Austerity", austerityScore));
        policyMap.put("Subsidise Transport & Food", new PolicyClass("Subsidise Transport & Food", subsidyScore));
        policyMap.put("Market Trader Support", new PolicyClass("Market Trader Support", traderSupportScore));

        String lastPolicy = state.getMemory().getLastPolicy();

        if (state.getMemory().getSamePolicyCount() > 1) {
            if (policyMap.containsKey(lastPolicy)) {
                policyMap.get(lastPolicy).reducePolicyScore(20);
            }
        }

        //Put all scores in a list and rank them
        List<PolicyClass> sortedList = new ArrayList<>(policyMap.values());
        sortedList.sort(Comparator.comparingInt((PolicyClass s)-> s.policyScore).reversed());
        
        //Random numbers for policy selection
        Random rand = new Random();
        double roll = rand.nextDouble();
        double midRoll = rand.nextDouble();

        //Values that determine how good the AI is
        double good = 0.0;
        double mid = 0.0;
        PolicyClass chosen;

        switch(difficulty) {
            case EASY: //20% best, 45% mid and 35% worst
                good = 0.20;
                mid = 0.65;
                break;

            case MEDIUM: //40% best, 35% mid and 25% worst
                good = 0.40;
                mid = 0.75;
                break;

            case HARD: //65% best, 20% mid and 15% worst
                good = 0.65;
                mid = 0.85;
                break;

            case EXPERT: //85% best, 13% mid and 2% worst
                good = 0.85;
                mid = 0.98;
                break;

        }

        //choose between the best 2 options
        if (roll < good) {
            int index = rand.nextDouble() < 0.5 ? 0 : 1;
            chosen = sortedList.get(index);
        //choose between the middle 3 options
        } else if (roll < mid) {
            if (midRoll < 0.333) {
                chosen = sortedList.get(3);  // Best
            } else if (midRoll < 0.667) {
                chosen = sortedList.get(4);  // Second best
            } else {
                chosen = sortedList.get(5);
            }
        //choose the worst option
        } else {
            chosen = sortedList.getLast();
        }

        return chosen.policyName;
    }
} 