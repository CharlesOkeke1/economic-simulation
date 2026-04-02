package engine;
import economies.FederalEconomy;
import economies.StateEconomy;
import data.Constants;
import gui.AppMain;

import java.util.*;

public class PolicyEngine {
    public static void applyPolicy(String stat, String policy) {
        HashMap<String, StateEconomy> states = AppMain.getStateMap();
        FederalEconomy federal = AppMain.getFederal();
        StateEconomy state = states.get(stat);
        StateEconomy playerState = states.get(AppMain.getConfig().getChosenState());
        double lastCash = state.getCash();
        double lastInflation = state.getInflationRate();


        double baseGrowth = 0.0025; // 0.5% per month
        double growthRate;
        double infraFactor;
        double stabFactor;
        double taxDistortion = 0;
        double debtPenalty;
        double policyGrowth = 0;
        state.setPolicySpend(0);
        state.setMonthlyRevenue(0);
        state.setMonthlySpend(0);
        state.setMonthlyProfit(0);

        switch(state.getPolicy()) {

            case "Raise Taxes":
                state.setTaxRate(state.getTaxRate() + 0.015);
                state.setTaxRate(state.getTaxRate() * (1 - Math.log(state.getAttributes().getTaxSensitivity())));
                state.setStability(state.getStability() - 1.3);
                state.setPopulation(state.getPopulation() - 200);
                state.setMonthlyRevenue(state.getMonthlyRevenue() + state.getRealGdp() * 0.012); // NEW: actual revenue gain
                break;

            case "Cut Taxes":
                state.setTaxRate(state.getTaxRate() - 0.015);
                state.setTaxRate(state.getTaxRate() * (1 + Math.log(state.getAttributes().getTaxSensitivity())));
                state.setStability(state.getStability() + 1.1);
                state.setPolicySpend(state.getPolicySpend() + state.getRealGdp() * 0.01);
                state.setPopulation(state.getPopulation() + 250);
                state.setMonthlyRevenue(state.getMonthlyRevenue() - state.getRealGdp() * 0.01); // NEW: revenue loss
                break;

            case "Invest Infrastructure":
                double infraSpend = state.getRealGdp() * 0.0325;
                state.setInfrastructure(state.getInfrastructure() + 3);
                state.setPolicySpend(state.getPolicySpend() + infraSpend);
                double infraReturn = Math.log(1 + state.getInfrastructure()) * 0.00015;
                policyGrowth = (infraReturn * state.getAttributes().getInfraEfficiency());
                state.setStability(state.getStability() + 0.5); // NEW: small social approval
                break;

            case "Invest Education":
                double eduSpend = state.getRealGdp() * 0.02;
                state.setPolicySpend(state.getPolicySpend() + eduSpend);
                state.setStability(state.getStability() + 1.2);
                policyGrowth = (state.getAttributes().getEducationEfficiency() * 0.0035);
                state.setInflationRate(state.getInflationRate() * 0.995); // NEW: long-term stabilising effect
                break;

            case "Boost Security":
                state.setStability(state.getStability() + 2);
                double policyCost = (state.getRealGdp() * 0.006);
                state.setPolicySpend(state.getPolicySpend() + policyCost);
                state.setPopulation(state.getPopulation() + 400);
                policyGrowth = 0.003;
                break;

            case "Subsidise Transport & Food":
                double subsidyCost = state.getRealGdp() * 0.012;
                state.setPolicySpend(state.getPolicySpend() + subsidyCost);
                state.setStability(state.getStability() + 2.0);
                policyGrowth = 0.005;
                state.setInflationRate(state.getInflationRate() * 0.992); // NEW: reduces inflation slightly
                break;

            case "Market Trader Support":
                double grantCost = state.getRealGdp() * 0.0105;
                state.setPolicySpend(state.getPolicySpend() + grantCost);
                state.setStability(state.getStability() + 1.8);
                policyGrowth = 0.006;
                state.setInfrastructure(state.getInfrastructure() + 1);
                break;

            case "Borrow":
                double borrowAmount = state.getRealGdp() * 0.03;
                state.setCash(state.getCash() + borrowAmount);
                state.setDebt(state.getDebt() + borrowAmount);
                state.setStability(state.getStability() - 1);
                state.setTaxRate(state.getTaxRate() * 1.02);
                break;

            case "Austerity":
                state.setTaxRate(state.getTaxRate() + 0.002);
                state.setStability(state.getStability() - 2);
                state.setInflationRate(state.getInflationRate() * 0.975);
                state.setMonthlySpend(state.getMonthlySpend() * 0.95); // NEW: actual spending cut
                break;

            // NEW TARGETED POLICIES
            case "Close Tax Loopholes":
                state.setMonthlyRevenue(state.getMonthlyRevenue() + state.getRealGdp() * 0.008);
                state.setStability(state.getStability() - 0.5);
                state.setTaxRate(state.getTaxRate() * 0.99); // efficiency replaces brute tax
                break;

            case "Expand VAT Base":
                state.setMonthlyRevenue(state.getMonthlyRevenue() + state.getRealGdp() * 0.01);
                state.setInflationRate(state.getInflationRate() * 1.01);
                state.setStability(state.getStability() - 0.8);
                break;

            case "Invest Agriculture":
                double agriSpend = state.getRealGdp() * 0.015;
                state.setPolicySpend(state.getPolicySpend() + agriSpend);
                state.setStability(state.getStability() + 1.0);
                state.setInflationRate(state.getInflationRate() * 0.985); // food supply effect
                policyGrowth = 0.004;
                break;

            case "Invest Industry":
                double indSpend = state.getRealGdp() * 0.02;
                state.setPolicySpend(state.getPolicySpend() + indSpend);
                policyGrowth = 0.0065;
                state.setInfrastructure(state.getInfrastructure() + 1);
                break;

            case "Defend Currency":
                double fxCost = state.getStateReserve() * 0.08;
                state.setStateReserve(state.getStateReserve() - fxCost);
                state.setInflationRate(state.getInflationRate() * 0.97);
                state.setStability(state.getStability() + 0.8);
                break;

            case "Debt Restructuring":
                double debtCut = state.getDebt() * 0.08;
                state.setDebt(state.getDebt() - debtCut);
                state.setStability(state.getStability() - 1.2); // austerity-like backlash
                state.setTaxRate(state.getTaxRate() * 1.01);
                break;

            default:
                System.out.println("ERROR - Invalid command");
                break;
        }

        /*GDP GROWTH RATE MODEL*/
        infraFactor = 1 + ((state.getInfrastructure() - 50) * 0.002);
        infraFactor = Math.max(0.8, Math.min(1.2, infraFactor)); //clamp the Infrastructure factor between 0.8 and 1.2
        
        stabFactor = 1 + ((state.getStability() - 40) * 0.0015);
        stabFactor = Math.max(0.75, Math.min(1.15, stabFactor)); //clamp the Stability factor between 0.75 and 1.15

        double t = state.getTaxRate();
        if (t > 0.25) taxDistortion = (t - 0.25) * 0.04;
        if (t > 0.35) taxDistortion += Math.pow(t - 0.35, 2) * 1.2;
        taxDistortion = Math.min(0.015, taxDistortion);
        
        double debtRatio = state.getDebt() / Math.max(state.getRealGdp(), 1e-6);
        debtPenalty = 1 - Math.min(0.4, debtRatio * 0.25); //debt penalty should not be less than 0.4

        growthRate = (baseGrowth * infraFactor * stabFactor * debtPenalty);
        double infraEffect = 0.003 * Math.log(1 + state.getInfrastructure());
        growthRate += (infraEffect - taxDistortion);

        double gdpScale = state.getGdp() * 5 / federal.nationalGDP;
        double growthPenalty = 1 / (1 + gdpScale);

        growthRate *= growthPenalty;

        if (state.getDebtToGdpRatio() > 0.6) growthRate *= 0.825;
        if (state.getDebtToGdpRatio() > 1) growthRate *= 0.75;


        if (state.getInflationRate() < 0) growthRate += state.getInflationRate();

        state.setDebtToGdpRatio(state.getDebt()/Math.max(state.getRealGdp(), 1e-6));
        if (state.getDebtToGdpRatio() > 0.80 && state.getDebtToGdpRatio() < 1.00) growthRate -= 0.005;
        if (state.getDebtToGdpRatio() > 1.00) growthRate -= 0.01;

        /*Calculate GDP and monthly growth rate is capped at 3.75%*/
        growthRate /= 1.75;
        growthRate = Math.max(-0.035, Math.min(0.03, growthRate));
        state.setGdpGrowth(growthRate + policyGrowth);
        state.setRealGdp(state.getRealGdp() * (1 + state.getGdpGrowth()));
        double inflationAdj = Math.max(-0.01, state.getInflationRate());
        state.setGdp(state.getRealGdp() * (1 + inflationAdj));

        state.setStability(state.getStability() - Math.max(0, state.getInflationRate()) * 10); //Inflation effect
        state.setStability(state.getStability() * state.getAttributes().getSecuritySensitivity()); //ATTRIBUTE ADJUSTMENT


        if (state.getGdpGrowth() < 0) state.setStability(state.getStability() + (state.getGdpGrowth() * 50));
        double effectiveStability = state.getStability()/(state.getStability() + 36) * 100;
        state.setStability(effectiveStability);

        /*POPULATION GROWTH METRIC*/
        double popGrowth = 0.008; // 0.8% monthly
        if (state.getStability() < 45) popGrowth -= 0.002;
        else popGrowth += 0.002;
        state.setPopulation((long) Math.max(state.getPopulation() * (1 + popGrowth), 5000)); //Cap minimum population at 5000


        /*GOVERNMENT REVENUE GROWTH MODEL*/
        double taxRevenue;
        double collection;
        double nonTaxRevenue = state.getGdp() * 0.01;

        //collection = taxRate * scaled stability factor
        collection = 0.4 + 0.35 * (state.getStability() / 100);
        taxRevenue = state.getGdp() * state.getTaxRate() * collection * 0.4;
        state.setMonthlyRevenue(taxRevenue + nonTaxRevenue + state.getFederalAllocation());


        /*GOVERNMENT SPENDING GROWTH MODEL */
        double baseSpend = state.getGdp() * 0.015; // 1.5% monthly
        baseSpend *= (1 + state.getInflationRate());
        double populationBurden = state.getPopulation() * Math.log(state.getPopulation()) * 0.5;

        double cashFactor = Math.log10(state.getCash() + 1);
        double inefficiency = 1 + (cashFactor * 0.02);
        state.setMonthlySpend((baseSpend + populationBurden + state.getPolicySpend()) * inefficiency);


        /*PROFIT AND LOSS ACCOUNTING*/
        state.setMonthlyProfit(state.getMonthlyRevenue() - state.getMonthlySpend());
        double realizedProfit = state.getMonthlyProfit() * (1 - (state.getAttributes().getCorruptionFactor() * 1.25));
        state.setMonthlyProfit(realizedProfit);


        /*DEBT PAYMENT*/
        if (state.getDebt() > 1 && !state.getPolicy().equals("Borrow") && !state.getPolicy().equals("Debt Restructuring")) {
            double interestPayment = state.getDebt() * federal.debtInterest;
            state.setMonthlyProfit(state.getMonthlyProfit() - Math.max(interestPayment, 0));
            state.setDebtPayment(interestPayment); // Amount of Debt Cleared (Interest only)
            if (state.getInflationRate() > 0.06) state.setDebt(state.getDebt() * 1.025);
            else state.setDebt(state.getDebt() * 0.9875);

            double principalRepayment = Math.max(state.getMonthlyProfit(), 0) * 0.175;
            state.setMonthlyProfit(state.getMonthlyProfit() - principalRepayment);
            state.setDebt(state.getDebt() - principalRepayment);
            state.setDebtPayment(state.getDebtPayment() + principalRepayment);// If there's profit, monthly debt payment should also pay off principal
        } else {
            state.setDebt(0);
            state.setDebtPayment(0);
        }
        state.setCash(state.getCash() + (state.getMonthlyProfit() * 0.9)); //CORRUPTION Loss I guess

        /*State's Contribute 28% of their operating cash at the end of the month*/
        double stateRemittance = Constants.REMITTANCE_RATE * state.getMonthlyProfit();
        double disasterContr = Constants.DISASTER_MGMT_RATE * state.getMonthlyProfit();
        state.setCash(state.getCash() - stateRemittance);
        state.setCash(state.getCash() - disasterContr);
        federal.federalReserve += stateRemittance;
        federal.operatingCash += disasterContr;

        /*DEBT AND CASH MODEL */
        federal.debtInterest = Constants.FEDERAL_DEBT_INTEREST;

        /*STATE RESERVE ACCUMULATION*/
        if (state.getCash() > 0) {
            //Save 30% of the months profit
            if (state.getMonthlyProfit() > 0) {
                double reserveTransfer = state.getMonthlyProfit() * 0.25; //Reserve 25% of monthly profit
                state.setStateReserve(state.getStateReserve() + reserveTransfer);
                state.setCash(state.getCash() - reserveTransfer);
            }
        } else {
            if (state.getStateReserve() > 0) state.setStateReserve(state.getStateReserve() - Math.abs(state.getCash() * 0.5));
            else state.setStateReserve(0);
        }


        /*ECONOMIC CRASH*/
        if ((state.getCash() < 1) && (state.getStateReserve() < 1)) EconomicCrash.apply(AppMain.getSim().getCurrentMonth());
        double stateReserve = state.getStateReserve();
        stateReserve /= 1 + (state.getInflationRate()/2);
        state.setStateReserve(stateReserve * 0.975);
        if (state.getInflationRate() > 0.06) state.setCash(state.getCash() * (1 - state.getInflationRate() * 0.3));


        /*CALCULATE NEXT MONTH'S INFLATION RATE*/
        //Calculate cash growth
        double cashGrowth = state.getCash() - lastCash;
        double deficitRatio = (-state.getMonthlyProfit() / state.getRealGdp());
        double moneyPressure = cashGrowth / state.getRealGdp();
        Random ran = new Random();
        double shock = (ran.nextDouble() - 0.5) * 0.04; // ±0.2% noise
        double target = 0.025; // 2.5% monthly
        double newInflation =
                (0.7 * lastInflation)            // strong persistence
                        + (0.08 * moneyPressure)          // monetary pressure
                        + (0.04 * deficitRatio)           // fiscal pressure
                        - (0.2 * state.getGdpGrowth())        // growth dampens inflation
                        + (target - lastInflation) * 0.12 // mean reversion
                        + shock;

        /* LIMIT HOW FAST INFLATION CAN MOVE */
        double maxStep = 0.004; // max 0.4% monthly change
        double change = newInflation - lastInflation;

        if (change > maxStep) change = maxStep;
        if (change < -maxStep) change = -maxStep;
        newInflation = lastInflation + change;

        /* SOFT BOUNDS */
        newInflation = Math.max(-0.02, Math.min(0.1, newInflation));
        state.setInflationRate(newInflation + (ran.nextDouble() * 0.005));

        state.setCash(weirdDoubleChecks(state.getCash(), Constants.MINIMUM_CASH));
        state.setInflationRate(weirdDoubleChecks(state.getInflationRate(), Constants.MINIMUM_INFLATION)); //3.75%
        state.setRealGdp(weirdDoubleChecks(state.getRealGdp(), Constants.MINIMUM_GDP));
        if (state.getTaxRate() > 0.4) state.setTaxRate(state.getTaxRate() * (0.85 - (Math.random()* 0.2)));
        if (AppMain.getSim().getCurrentMonth() % 8 == 0) state.setInfrastructure(state.getInfrastructure() - 1);
    }

    public static double weirdDoubleChecks(double val, double min) {
        if ((Double.isNaN(val)) || (Double.isInfinite(val))) val = min;
        return val;
    }
}