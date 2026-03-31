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
                state.setTaxRate(state.getTaxRate() + 0.015); //Increase taxes by 0.5% percent
                state.setTaxRate(state.getTaxRate() * (1 - Math.log(state.getAttributes().getTaxSensitivity())));
                state.setStability(state.getStability() - 1.3); //Mild Populace disapproval
                state.setPopulation(state.getPopulation() - 200);
                break;

            case "Cut Taxes":
                state.setTaxRate(state.getTaxRate() - 0.015); //Reduce taxes by 0.5% percent
                state.setTaxRate(state.getTaxRate() * (1 + Math.log(state.getAttributes().getTaxSensitivity())));
                state.setStability(state.getStability() + 1.1); //Mild Populace approval
                state.setPolicySpend(state.getPolicySpend() + state.getRealGdp() * 0.01); // small fiscal pressure
                state.setPopulation(state.getPopulation() + 250);
                break;

            case "Invest Infrastructure":
                double infraSpend = state.getRealGdp() * 0.0325; //Each infrastructure expenditure costs 0.15% of state gdp
                state.setInfrastructure(state.getInfrastructure() + 3);
                state.setPolicySpend(state.getPolicySpend() + infraSpend);
                double infraReturn = Math.log(1 + state.getInfrastructure()) * 0.00015;
                policyGrowth = (infraReturn * state.getAttributes().getInfraEfficiency()); //ATTRIBUTE ADJUSTMENT
                break;

            case "Invest Education":
                //When educational investment occurs, education increases, then policy spend increases and cash decreases by policy spend
                double eduSpend = state.getRealGdp() * 0.02;  // 0.1% of gdp
                state.setPolicySpend(state.getPolicySpend() + eduSpend);
                state.setStability(state.getStability() + 1.2);  // gradual social effect
                policyGrowth = (state.getAttributes().getEducationEfficiency() * 0.0035);
                break;

            case "Boost Security":
                state.setStability(state.getStability() + 2);
                double policyCost = (state.getRealGdp() * 0.006);
                state.setPolicySpend(state.getPolicySpend() + policyCost); //Increase policy spend by 0.6% of GDP
                state.setPopulation(state.getPopulation() + 400);
                policyGrowth = 0.003;  // +0.4% slight boost
                break;

            case "Subsidise Transport & Food":
                double subsidyCost = state.getRealGdp() * 0.012;  // 0.8%
                state.setPolicySpend(state.getPolicySpend() + subsidyCost);
                state.setStability(state.getStability() + 2.0);
                policyGrowth = 0.005;  // +0.6% slight boost
                break;

            case "Market Trader Support":
                double grantCost = state.getRealGdp() * 0.0105;  // 2%
                state.setPolicySpend(state.getPolicySpend() + grantCost); // finance logic
                state.setStability(state.getStability() + 1.8);
                policyGrowth = 0.006; //+0.6% productivity micro-boost
                state.setInfrastructure(state.getInfrastructure() + 1);  // soft boost
                break;

            case "Borrow":
                //When borrowing, increase state cash, state debt and reduce allocation pool by 2% of gdp
                double borrowAmount = state.getRealGdp() * 0.03;  // 3%
                state.setCash(state.getCash() + borrowAmount);
                state.setDebt(state.getDebt() + borrowAmount);
                state.setStability(state.getStability() - 1);
                state.setTaxRate(state.getTaxRate() * 1.02);
                break;

            case "Austerity":
                //Austerity is harsh economic times. Cut govt. spending and raise tax aggressively.
                state.setTaxRate(state.getTaxRate() + 0.002);  // smaller increase
                state.setStability(state.getStability() - 2);
                state.setInflationRate(state.getInflationRate() * 0.975); //Slight inflation drop.
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

        if (state.getInflationRate() < 0) growthRate += state.getInflationRate();

        state.setDebtToGdpRatio(state.getDebt()/Math.max(state.getRealGdp(), 1e-6));
        if (state.getDebtToGdpRatio() > 0.80 && state.getDebtToGdpRatio() < 1.00) growthRate -= 0.005;
        if (state.getDebtToGdpRatio() > 1.00) growthRate -= 0.01;

        /*Calculate GDP and monthly growth rate is capped at 3.75%*/
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
        state.setMonthlySpend(baseSpend + populationBurden + state.getPolicySpend());


        /*PROFIT AND LOSS ACCOUNTING*/
        state.setMonthlyProfit(state.getMonthlyRevenue() - state.getMonthlySpend());
        double realizedProfit = state.getMonthlyProfit() * (1 - (state.getAttributes().getCorruptionFactor() * 1.25));
        state.setMonthlyProfit(realizedProfit);


        /*DEBT PAYMENT*/
        if (state.getDebt() > 1) {
            double interestPayment = state.getDebt() * federal.debtInterest;
            state.setMonthlyProfit(state.getMonthlyProfit() - Math.max(interestPayment, 0));
            state.setDebtPayment(interestPayment); // Amount of Debt Cleared (Interest only)
            if (state.getInflationRate() > 0.06) state.setDebt(state.getDebt() * 1.025);

            double principalRepayment = Math.max(state.getMonthlyProfit(), 0) * 0.15;
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
                double reserveTransfer = state.getMonthlyProfit() * 0.175; //Reserve 25% of monthly profit
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
        state.setStateReserve(stateReserve);




        /*CALCULATE NEXT MONTH'S INFLATION RATE*/
        //Calculate cash growth
        double cashGrowth = state.getCash() - lastCash;
        double deficitRatio = (-state.getMonthlyProfit() / state.getRealGdp());
        double moneyPressure = cashGrowth / state.getRealGdp();
        Random ran = new Random();
        double shock = (ran.nextDouble() - 0.5) * 0.023; // ±0.2% noise
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
        newInflation = Math.max(-0.02, Math.min(0.06, newInflation));
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