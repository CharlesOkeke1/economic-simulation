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
        StateEconomy state = states.get(stat);

        HashMap<String, PolicyClass> policyMap = new HashMap<>();

        // SCORE VARIABLES
        int raiseTaxScore = 0, cutTaxScore = 0, securityScore = 0, borrowScore = 0;
        int eduScore = 0, infraScore = 0, traderSupportScore = 0, subsidyScore = 0, austerityScore = 0;

        int loopholeScore = 0, vatScore = 0, agriScore = 0, industryScore = 0, currencyScore = 0, restructureScore = 0;

        // FISCAL CONDITIONS
        if (state.getMonthlyProfit() < 0) {
            raiseTaxScore += 4;
            austerityScore += 3;
            borrowScore += 2;
        }

        if (state.getPolicySpend() > 0.025 * state.getGdp()) {
            austerityScore += 3;
            restructureScore += 2;
        }

        if (state.getDebtToGdpRatio() > 0.65) {
            restructureScore += 5;
            raiseTaxScore += 3;
            borrowScore -= 3;
        }

        // GROWTH
        if (state.getInfrastructure() < 18) infraScore += 4;

        if (state.getGdpGrowth() < 0) {
            eduScore += 4;
            industryScore += 4;
        }

        if (state.getStability() > 55 && state.getDebtToGdpRatio() < 0.45) {
            infraScore += 3;
            industryScore += 3;
        }

        // STABILITY
        if (state.getStability() < 35) {
            securityScore += 5;
            subsidyScore += 4;
            agriScore += 3;
        }

        if (state.getStability() > 35 && state.getStability() < 50) {
            traderSupportScore += 4;
        }

        if (state.getStability() > 65 && state.getCash() > 0.3 * state.getGdp()) {
            cutTaxScore += 4;
        }

        // LIQUIDITY / RESERVES
        if (state.getCash() < state.getMemory().getLastCash()) {
            raiseTaxScore += 3;
            borrowScore += 3;
            infraScore -= 2;
            eduScore -= 2;
        }

        if (state.getStateReserve() < 0.1 * state.getGdp()) {
            currencyScore -= 3;
        }

        // MEMORY EFFECTS
        if (state.getDebt() > state.getMemory().getLastDebt()) {
            restructureScore += 4;
            raiseTaxScore += 3;
            borrowScore -= 2;
        }

        if (state.getStability() < state.getMemory().getLastStability()) {
            securityScore += 4;
            subsidyScore += 3;
            raiseTaxScore -= 2;
        }

        // ROLLING METRICS
        if (state.getMemory().getRollingGrowth12M() < 0) {
            infraScore += 4;
            industryScore += 4;
            traderSupportScore += 3;
        }

        if (state.getMemory().getRollingInflation12M() > 0.06) {
            austerityScore += 3;
            currencyScore += 4;
            subsidyScore -= 3;
            vatScore -= 2;
        }

        if (state.getMemory().getRollingProfit12M() < 0) {
            raiseTaxScore += 3;
            borrowScore += 4;
            restructureScore += 3;
        }

        // GOVERNMENT TYPE
        switch (state.getGovernmentType()) {
            case GROWTH_FOCUSED:
                infraScore += 4;
                industryScore += 4;
                eduScore += 3;
                break;

            case SOCIALIST:
                subsidyScore += 4;
                traderSupportScore += 3;
                agriScore += 3;
                break;

            case FISCAL_CONSERVATIVE:
                austerityScore += 4;
                raiseTaxScore += 3;
                restructureScore += 3;
                break;

            case DEBT_FIGHTER:
                restructureScore += 5;
                austerityScore += 3;
                break;

            case SECURITY_ORIENTED:
                securityScore += 5;
                break;
        }

        // POLICY MAP (ALL 15)
        policyMap.put("Cut Taxes", new PolicyClass("Cut Taxes", cutTaxScore));
        policyMap.put("Raise Taxes", new PolicyClass("Raise Taxes", raiseTaxScore));
        policyMap.put("Invest Infrastructure", new PolicyClass("Invest Infrastructure", infraScore));
        policyMap.put("Invest Education", new PolicyClass("Invest Education", eduScore));
        policyMap.put("Boost Security", new PolicyClass("Boost Security", securityScore));
        policyMap.put("Borrow", new PolicyClass("Borrow", borrowScore));
        policyMap.put("Austerity", new PolicyClass("Austerity", austerityScore));
        policyMap.put("Subsidise Transport & Food", new PolicyClass("Subsidise Transport & Food", subsidyScore));
        policyMap.put("Market Trader Support", new PolicyClass("Market Trader Support", traderSupportScore));

        policyMap.put("Close Tax Loopholes", new PolicyClass("Close Tax Loopholes", loopholeScore + raiseTaxScore / 2));
        policyMap.put("Expand VAT Base", new PolicyClass("Expand VAT Base", vatScore + raiseTaxScore / 2));
        policyMap.put("Invest Agriculture", new PolicyClass("Invest Agriculture", agriScore));
        policyMap.put("Invest Industry", new PolicyClass("Invest Industry", industryScore));
        policyMap.put("Defend Currency", new PolicyClass("Defend Currency", currencyScore));
        policyMap.put("Debt Restructuring", new PolicyClass("Debt Restructuring", restructureScore));

        // REPETITION PENALTY
        String lastPolicy = state.getMemory().getLastPolicy();
        if (state.getMemory().getSamePolicyCount() > 1 && policyMap.containsKey(lastPolicy)) {
            policyMap.get(lastPolicy).reducePolicyScore(policyMap.get(lastPolicy).getPolicyScore());
        }

        // SORT
        List<PolicyClass> sortedList = new ArrayList<>(policyMap.values());
        sortedList.sort(Comparator.comparingInt((PolicyClass s) -> s.policyScore).reversed());

        Random rand = new Random();
        double roll = rand.nextDouble();

        double good = 0.0, mid = 0.0;
        PolicyClass chosen;

        switch (difficulty) {
            case EASY:
                good = 0.20; mid = 0.65;
                break;
            case MEDIUM:
                good = 0.40; mid = 0.75;
                break;
            case HARD:
                good = 0.65; mid = 0.85;
                break;
            case EXPERT:
                good = 0.85; mid = 0.98;
                break;
        }

        if (roll < good) {
            // Top 5 policies (indices 0–4)
            chosen = sortedList.get(rand.nextInt(5));
        } else if (roll < mid) {
            // Mid 7 policies (indices 5–11)
            chosen = sortedList.get(5 + rand.nextInt(7));
        } else {
            // Bottom 3 policies (indices 12–14)
            chosen = sortedList.get(12 + rand.nextInt(3));
        }

        return chosen.policyName;
    }
}