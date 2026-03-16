package game.engine;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import game.data.Constants;
import game.gui.AppMain;

import java.util.*;

public class PolicyEngine {
    public static void applyPolicy(Map<String, StateEconomy> states, 
                                    FederalEconomy federal, String stat, String policy) {

        StateEconomy state = states.get(stat);
        double lastGdp = state.gdp; //Set last nominal gdp
        double lastRealGdp = state.realGdp; //Set last real gdp
        double lastCash = state.cash;
        double lastInflation = state.inflationRate;
        //double decay = 1 - (Math.log(AppMain.getSim().getCurrentMonth() + 1) * 0.02);
        //if ((Double.isNaN(decay)) || (Double.isInfinite(decay))) decay = 0.95;

        double baseGrowth = 0.0005; // 0.25% per month
        double growthRate;
        double infraFactor;
        double stabFactor;
        double taxDistortion = 0;
        double debtPenalty;
        double policyGrowth = 0;
        state.policySpend = 0;  
        state.monthlySpend = 0;  
        state.monthlyRevenue = 0;
        state.monthlyProfit = 0;
        

        switch(state.getPolicy()) {
            case "Raise Taxes":
                state.taxRate += 0.015; //Increase taxes by 0.5% percent
                state.taxRate *= (1 - Math.log(state.getAttributes().getTaxSensitivity()));
                state.stability -= 1.3; //Mild Populace disapproval
                state.population -= 200;
                state.inflationRate *= 0.95;
                break;
            
            case "Cut Taxes":
                state.taxRate -= 0.015; //Reduce taxes by 0.5% percent
                state.taxRate *= (1 + Math.log(state.getAttributes().getTaxSensitivity()));
                state.stability += 1.1; //Mild Populace approval
                state.policySpend += state.realGdp * 0.01; // small fiscal pressure
                state.population += 250;
                break;

            case "Invest Infrastructure":
                double infraSpend = state.realGdp * 0.0325; //Each infrastructure expenditure costs 0.15% of state gdp
                state.infrastructure += 3;
                // Spread cost over a year
                state.policySpend += infraSpend; 
                double infraReturn = Math.log(1 + state.infrastructure) * 0.005;
                policyGrowth = (infraReturn * state.getAttributes().getInfraEfficiency()); //ATTRIBUTE ADJUSTMENT
                break;

            case "Invest Education":
                //When educational investment occurs, education increases, then policy spend increases and cash decreases by policyspend
                double eduSpend = state.realGdp * 0.02;  // 0.1% of gdp
                state.policySpend += eduSpend;
                state.stability += 1.2;  // gradual social effect
                // long-term productivity bump
                policyGrowth = (state.getAttributes().getEducationEfficiency() * 0.012);
                break;
            
            case "Boost Security":
                state.stability += 2;
                state.policySpend += (state.realGdp * 0.006); //Increase policy spend by 0.6% of GDP
                state.population += 400;
                policyGrowth = 0.004;  // +0.4% slight boost
                break;

            case "Subsidise Transport & Food":
                double subsidyCost = state.realGdp * 0.012;  // 0.8%
                // finance logic
                state.policySpend += subsidyCost;
                state.stability += 2.0;
                policyGrowth = 0.006;  // +0.6% slight boost
                break;

            case "Market Trader Support":
                double grantCost = state.realGdp * 0.0105;  // 2%
                state.policySpend += grantCost; // finance logic
                state.stability += 1.8;
                policyGrowth = 0.0075; //+0.75% productivity micro-boost
                state.infrastructure += 1;  // soft boost
                break;

            case "Borrow":
                //When borrowing, increase state cash, state debt and reduce allocation pool by 2% of gdp
                double borrowAmount = state.realGdp * 0.05;  // 3%
                state.cash += borrowAmount;
                state.debt += borrowAmount;
                state.stability -= 1;
                state.taxRate *= 1.02;
                // interest risk rises slightly
                break;
            
            case "Austerity":
                //Austerity is harsh economic times. Cut govt. spending and raise tax aggresively.
                state.policySpend *= 0.93;
                //state.taxRate += 0.005;  // smaller increase
                state.stability -= 2;
                state.population -= 250;  // short-term contraction
                state.inflationRate *= 1.1;
                break;

            default:
                System.out.println("ERROR - Invalid command");
                break;
        }


        /*GDP GROWTH RATE MODEL*/
        infraFactor = 1 + ((state.infrastructure - 50) * 0.002);
        infraFactor = Math.max(0.8, Math.min(1.2, infraFactor)); //clamp the Infrastructure factor between 0.8 and 1.2
        
        stabFactor = 1 + ((state.stability - 40) * 0.0015);
        stabFactor = Math.max(0.75, Math.min(1.15, stabFactor)); //clamp the Satbility factor between 0.75 and 1.15

        double t = state.taxRate;
        if (t > 0.18) taxDistortion = (t - 0.18) * 0.08;
        if (t > 0.30) taxDistortion += Math.pow(t - 0.30, 2) * 2.0;
        //taxDistortion *= state.getAttributes().getTaxSensitivity(); //ATTRIBUTE ADJUSTMENT
        taxDistortion = Math.min(0.02, taxDistortion);
        
        double debtRatio = state.debt / Math.max(state.realGdp, 1e-6);
        debtPenalty = 1 - Math.min(0.4, debtRatio * 0.25); //debt penalty should not be less than 0.4

        growthRate = (baseGrowth * infraFactor * stabFactor * debtPenalty);
        double infraEffect = 0.003 * Math.log(1 + state.infrastructure);
        growthRate += (infraEffect - taxDistortion);

        if (state.inflationRate < 0) growthRate += state.inflationRate;
        if (state.monthlyProfit < 0) { growthRate *= 0.95; }
        //growthRate *= 0.95;

        state.debtToGdpRatio = (state.debt/Math.max(state.realGdp, 1e-6));
        if (state.debtToGdpRatio > 0.80 && state.debtToGdpRatio < 1.00) growthRate -= 0.005;
        if (state.debtToGdpRatio > 1.00) growthRate -= 0.01;



        /*Calculate GDP and monthly growth rate is capped at 3.75%*/
        growthRate = Math.max(-0.035, Math.min(0.03, growthRate));
        state.gdpGrowth = growthRate + policyGrowth;
        state.realGdp *= (1 + state.gdpGrowth);
        double inflationAdj = Math.max(-0.01, state.inflationRate);
        state.gdp = state.realGdp * (1 + inflationAdj);


        if (state.gdpGrowth < 0) state.stability += (state.gdpGrowth * 50);
        double effectiveStability = state.stability/(state.stability + 36) * 100;
        
        state.stability = effectiveStability;
        state.stability -= Math.max(0, state.inflationRate) * 10; //Inflation effect
        state.stability *= state.getAttributes().getSecuritySensitivity(); //ATTRIBUTE ADJUSTMENT





        /*GOVERNMENT REVENUE GROWTH MODEL*/
        double taxRevenue;
        double collection;
        double nonTaxRevenue = state.gdp * 0.01;

        //collection = taxRate * scaled stability factor
        collection = 0.5 + 0.35 * (state.stability / 100);
        taxRevenue = state.gdp * state.taxRate * collection * 0.2;
        state.monthlyRevenue = taxRevenue + nonTaxRevenue + state.federalAllocation;




        /*GOVERNMENT SPENDING GROWTH MODDEL */
        double baseSpend = state.gdp * 0.015; // 1.5% monthly
        baseSpend *= (1 + state.inflationRate);
        double populationBurden = state.population * Math.log(state.population) * 0.5;
        state.monthlySpend = baseSpend + populationBurden + state.policySpend;


        /*State's Contribute 28% of their operating cash at the end of the month*/
        double stateRemittance = Constants.REMITTANCE_RATE * state.monthlyProfit;
        double disasterContr = Constants.DISASTER_MGMT_RATE * state.monthlyProfit;
        state.cash -= stateRemittance;
        state.cash -= disasterContr;
        federal.federalReserve += stateRemittance;
        federal.operatingCash += disasterContr;

        //Calculate cash growth
        double cashGrowth = state.cash - lastCash;


        
        /*DEBT AND CASH MODEL */
        federal.debtInterest = 0.02 + (state.debt / Math.max(state.gdp, 1e-6)) * 0.05;
        //state.debt *= federal.debtInterest + 1;
        state.monthlyProfit = state.monthlyRevenue - state.monthlySpend;
        double realizedProfit = state.monthlyProfit * (1 - (state.getAttributes().getCorruptionFactor() * 1.25));
        state.monthlyProfit = realizedProfit;
        state.cash += state.monthlyProfit;



        if (state.debt > 1) {
            double interestPayment = state.debt * federal.debtInterest;
            state.cash -= interestPayment;
            state.debtPayment = interestPayment; // Amount of Debt Cleared (Interest only)
            if (state.inflationRate > 0.07) state.debt *= 1.025;

            double principalRepayment = state.monthlyProfit * 0.15;
            state.debt -= principalRepayment;
            state.cash -= principalRepayment;
            state.debtPayment += principalRepayment; // If there's profit, monthly debt payment should also pay off principal
        } else {
            state.cash += Math.abs(state.debt);
            state.debt = 0;
            state.debtPayment = 0;
        }

        //state.debt *= 1 + state.inflationRate;
        
        /*STATE RESERVE ACCUMULATION*/
        if (state.cash > 0) {
            //Save 30% of the months profit
            if (state.monthlyProfit > 0) {
                double reserveTransfer = state.monthlyProfit * 0.25; //Reserve 25% of monthly profit
                state.stateReserve += reserveTransfer;
                state.cash -= reserveTransfer;
            }
        } else {
            if (state.stateReserve > 0) state.stateReserve -= Math.abs(state.cash * 0.5);
            else state.stateReserve = 0;
        }

        /*ECONOMIC CRASH*/
        if ((state.cash < 1) && (state.stateReserve < 1)) EconomicCrash.apply(states, federal, AppMain.getSim().getCurrentMonth());
        state.stateReserve /= 1 + (state.inflationRate/2);


        /*POPULATION GROWTH METRIC*/ 
        double popGrowth = 0.005; // 0.5% monthly
        if (state.stability < 40) popGrowth -= 0.0025;
        if (state.taxRate > 0.2) popGrowth -= 0.00175;
 
        state.population *= (1 + popGrowth);
        state.population = Math.max(state.population, 5000); //Cap minimum population at 5000

        /*CALCULATE NEXT MONTH'S INFLATION RATE*/
        double deficitRatio = (-state.monthlyProfit / state.realGdp);
        double moneyPressure = cashGrowth / state.realGdp;
        Random ran = new Random();
        double shock = (ran.nextDouble() - 0.5) * 0.013; // ±0.2% noise
        double target = 0.025; // 2.5% monthly
        double newInflation =
                (0.85 * lastInflation)            // strong persistence
                        + (0.08 * moneyPressure)          // monetary pressure
                        + (0.04 * deficitRatio)           // fiscal pressure
                        - (0.05 * state.gdpGrowth)        // growth dampens inflation
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
        state.inflationRate = newInflation + (ran.nextDouble() * 0.005);


        //if (state.getPolicy().equals("Borrow"))

        /*Reset if NaN or infinite values emerge*/
        if ((Double.isNaN(state.cash)) || (Double.isInfinite(state.cash))) state.cash = Constants.MINIMUM_CASH;
        if ((Double.isNaN(state.inflationRate)) || (Double.isInfinite(state.inflationRate))) state.inflationRate = 0.04;
        if ((Double.isNaN(state.gdp)) || (Double.isInfinite(state.gdp))) state.gdp = Constants.MINIMUM_GDP; 
        //if ((Double.isNaN(state.population)) || (Double.isInfinite(state.population))) state.population = Constants.MINIMUM_POPULATION;
        if (state.taxRate > 0.4) state.taxRate *= (0.85 - (Math.random()* 0.2));

        if (AppMain.getSim().getCurrentMonth() % 10 == 0) state.infrastructure -= 1;
    }
}