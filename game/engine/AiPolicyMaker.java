package game.engine;

import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import java.util.*;

public class AiPolicyMaker {

    public static class PolicyClass {
        String policyName;
        int policyScore; 

        public PolicyClass(String policyName, int policyScore) {
            this.policyName = policyName;
            this.policyScore = policyScore;
        }
    }

    public static String smartChoice(Map<String, StateEconomy> states, 
                                    FederalEconomy federal, String stat) {
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
        if (state.monthlyProfit < 0.03 * state.gdp) {
            if (state.taxRate > 0.3) raiseTaxScore += 6;
            else austerityScore += 4;
        }

        //Policy Spend too high
        if (state.policySpend > 0.0275 * state.gdp) austerityScore += 4;
      
        //Debt to gdp too high
        if (state.debtToGdpRatio > 0.65) {
            if (state.stability > 45) austerityScore += 4;
            else raiseTaxScore += 6;
        }


        /*GROWTH ORIENTED POLICIES*/
        //Infrastructure too low
        if (state.infrastructure < 20 && state.infrastructure > 15) infraScore += 4;

        //Societal stability and debt rate are good
        if (state.stability > 55 && state.debtToGdpRatio < 0.4) infraScore += 4;

        //Infrastructure too low
        if (state.gdpGrowth < 0) eduScore += 6;

        // Population growth is slowing down
        if (state.stability < 15) securityScore += 5;


        /*POLITICAL AND SOCIETAL SURVIVAL POLICIES*/
        //Stability Low
        int stabCount = 0;
        if (state.stability < 35) {
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
        if (state.stability > 35 && state.stability < 45) {
            traderSupportScore += 8;
        } 

        //High Stability and decent cash flow
        if (state.stability > 63 && state.cash > (0.35 * state.gdp)) cutTaxScore += 7;

        /*STRATEGIC BORROWING*/ 
        int infraCount = 0;
        if (state.infrastructure <= 15) {
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
        if ((state.cash > 0.20 * state.gdp) &&(state.debtToGdpRatio < 0.50) && (state.stability > 50)) {
            if (Math.random() > 0.65) infraScore += 4;
            else eduScore += 6;
        }

        switch (state.governmentType) {
            case GROWTH_FOCUSED: //Growth focused economies care more about investments
                infraScore += 6;
                eduScore += 6;
                break;

            case SOCIALIST: //socialists are more about welfare and subsidies
                subsidyScore += 6;
                cutTaxScore += 5;
                traderSupportScore += 5;
                break;

            case FISCAL_CONSERVATIVE: //Heavy on reducing government speding
                austerityScore += 5;
                raiseTaxScore += 5;
                break;

            case DEBT_FIGHTER: //Debt clearers are also more austerous
                austerityScore += 4;
                break;

            case SECURITY_ORIENTED: //Security Oriented value boosting security and ensuring stabilityy
                securityScore += 6;
                state.stability += 3;
                break;
        }

        policyMap.put("Cut Taxes", new PolicyClass("Cut Taxes", cutTaxScore));
        policyMap.put("Raise Taxes", new PolicyClass("Raise Taxes", raiseTaxScore));
        policyMap.put("Invest Infrastructure", new PolicyClass("Invest Infrastructure", infraScore));
        policyMap.put("Invest Education", new PolicyClass("Invest Education", eduScore));
        policyMap.put("Boost Security", new PolicyClass("Boost Security", securityScore));
        policyMap.put("Borrow", new PolicyClass("Borrow", borrowScore));
        policyMap.put("Austerity", new PolicyClass("Austerity", austerityScore));
        policyMap.put("Subsidise Transport & Food", new PolicyClass("Subsidise Transport & Food", subsidyScore));
        policyMap.put("Market Trader Support", new PolicyClass("Market Trader Support", traderSupportScore));

        List<PolicyClass> sortedList = new ArrayList<>(policyMap.values());
        sortedList.sort(Comparator.comparingInt((PolicyClass s)-> s.policyScore).reversed());
        Random rand = new Random();
        double roll = rand.nextDouble();
        double midRoll = rand.nextDouble();
        PolicyClass chosen;

        //choose between the best 2 options 75% of the time
        if (roll < 0.75) {
            int index = rand.nextDouble() < 0.5 ? 0 : 1;
            chosen = sortedList.get(index);
        //choose between the middle 3 options 20% of the time
        } else if (roll < 0.95) {
            if (midRoll < 0.333) {
                chosen = sortedList.get(3);  // Best
            } else if (midRoll < 0.667) {
                chosen = sortedList.get(4);  // Second best
            } else {
                chosen = sortedList.get(5);
            }
        //choose the worst option 5% of the time
        } else {
            chosen = sortedList.get(sortedList.size() - 1);
        }

        // Return the policy itself  
        policy = chosen.policyName;
        return policy;
    }
}