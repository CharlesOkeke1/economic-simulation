package game.engine;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import java.util.*;

public class PolicyEngine {
    public static final double minimumCash = 300000.0;
    public static final long minimumPop = 10000;
    public static final double minimumGdp = 750000.0;
    public static final long inflationConstant = 275000;

    public static void applyPolicy(Map<String, StateEconomy> states, 
                                    FederalEconomy federal, String stat, String policy) {
        StateEconomy state = states.get(stat);

        double serviceCostFactor = 50; // A hundreth of the ratio of average Nigerian state spending to population
        double baseGrowth = 0.00275; // 0.35% per month
        double growthRate;
        double infraFactor;
        double stabFactor;
        double taxDistortion;
        double debtPenalty;   
        state.policySpend = 0;  
        state.monthlySpend = 0;  
        state.monthlyRevenue = 0;
        state.monthlyProfit = 0;
        


        switch(policy) {
            case "Raise Taxes":
                state.taxRate += 0.005; //Increase taxes by 0.5% percent
                state.stability -= 0.8; //Mild Populace disapproval
                state.population -= 700;
                state.gdp *= 0.996; 
                break;
            
            case "Cut Taxes":
                state.taxRate -= 0.005; //Reduce taxes by 0.5% percent
                state.stability += 0.6; //Mild Populace approval
                state.policySpend += state.gdp * 0.03; // small fiscal pressure
                state.population += 750;
                break;

            case "Invest Infrastructure":
                double infraSpend = state.gdp * 0.026; //Each infrastructure expenditure costs 0.15% of state gdp
                state.infrastructure += 3.5;
                // Spread cost over a year
                state.policySpend += infraSpend; 
                state.gdp *= 1.0175;  // +1.75% supply-side boost
                break;

            case "Invest Education":
                //When educational investment occurs, education increases, then policy spend increases and cash decreases by policyspend
                double eduSpend = state.gdp * 0.022;  // 0.1% of gdp
                state.policySpend += eduSpend;
                state.stability += 1.2;  // gradual social effect
                // long-term productivity bump
                state.gdp *= 1.0125;  // +1.25%
                break;
            
            case "Boost Security":
                state.stability += 2;
                state.policySpend += (state.gdp * 0.023); //Increase policy spend by 0.6% of GDP
                state.population += 600;
                break;

            case "Subsidise Transport & Food":
                double subsidyCost = state.gdp * 0.0265;  // 0.35%
                // finance logic
                state.policySpend += subsidyCost;
                state.stability += 1;
                state.gdp *= 1.0275;  // +2.75% slight boost
                break;

            case "Market Trader Support":
                double grantCost = state.gdp * 0.02;  // 2%
                state.policySpend += grantCost; // finance logic
                state.stability += 1;
                state.gdp *= 1.0125; // productivity micro-boost
                state.infrastructure += 1;  // soft boost
                break;

            case "Borrow":
                //When borrowing, increase state cash, state debt and reduce allocation pool by 2% of gdp
                double borrowAmount = state.gdp * 0.03;  // 3%
                state.cash += borrowAmount;
                state.debt += borrowAmount;
                state.stability -= 0.5;
                // interest risk rises slightly
                break;
            
            case "Austerity":
                //Austerity is harsh economic times. Cut govt. spending and raise tax aggresively.
                state.policySpend = 0.93 * state.policySpend;
                state.taxRate += 0.005;  // smaller increase
                state.stability -= 2;
                state.infrastructure -= 1;  // small infrastructure sell off
                state.population -= 900;  // short-term contraction
                break;

            default:
                System.out.println("ERROR - Invalid command");
                break;
        }


        /*GDP GROWTH RATE MODEL*/
        infraFactor = 1 + ((state.infrastructure - 50) * 0.002);
        infraFactor = Math.max(0.8, Math.min(1.2, infraFactor)); //clamp the Infrastructure factor between 0.8 and 1.2
        
        stabFactor = 1 + ((state.stability - 50) * 0.0015);
        stabFactor = Math.max(0.75, Math.min(1.15, stabFactor)); //clamp the Satbility factor between 0.75 and 1.15

        taxDistortion = Math.max(0, (state.taxRate - 0.008) * 0.5);
        if (state.taxRate > 0.67) {
            taxDistortion = Math.pow(state.taxRate - 0.20, 2) * 3.36; //states should only have a tax distortion if their taxes are too high
        }
        
        double debtRatio = state.debt / Math.max(state.gdp, 1e-6);
        debtPenalty = 1 - Math.min(0.4, debtRatio * 0.25); //debt penalty should not be less than 0.4

        growthRate = (baseGrowth * infraFactor * stabFactor * debtPenalty);
        double infraEffect = 0.002 * Math.log(1 + state.infrastructure);
        growthRate += (infraEffect - taxDistortion);

        state.debtToGdpRatio = (state.debt/Math.max(state.gdp, 1e-6));
        if ((state.debtToGdpRatio > 0.80) && (state.debtToGdpRatio < 1.00)) growthRate -= 0.005;
        if (state.debtToGdpRatio < 1.00) growthRate -= 0.01;

        /*Calculate GDP and monthly growth rate is capped at  */
        growthRate = Math.max(-0.0375, Math.min(0.0375, growthRate));
        state.gdpGrowth = growthRate + 0.04;
        double lastGdp = state.gdp; //Set last gdp
        state.gdp = state.gdp * (1 + state.gdpGrowth);
        double gdpGrowth = state.gdp - lastGdp; //calculate gdpgrowth.
        state.realGdp = state.gdp/(1 + state.inflationRate); // Calculate real gdp

        
        state.stability -= state.inflationRate * 60; //Inflation effect

        /*GOVERNMENT REVENUE GROWTH MODEL*/
        double taxRevenue;
        double efficiency;
        double nonTaxRevenue = state.gdp * 0.1;

        //Efficiency = taxRate * scaled stability factor
        efficiency = state.taxRate + (state.stability / 75); 
        taxRevenue = state.gdp * (efficiency / 6);
        
        state.monthlyRevenue = taxRevenue + nonTaxRevenue;

        /*GOVERNMENT SPENDING GROWTH MODDEL */
        double baseSpend = state.gdp * 0.05; // 6.5% monthly
        baseSpend *= (1 + state.inflationRate);

        double populationBurden = state.population * 3;
        
        state.monthlySpend = baseSpend + populationBurden + state.policySpend;   
        
        /*DEBT AND CASH MODEL */
        double federalDebtInterest = 0.008 + (state.debt / Math.max(state.gdp, 1e-6)) * 0.01;
        state.monthlyProfit = state.monthlyRevenue - state.monthlySpend;
        state.cash += state.monthlyProfit;

        /*State's Contribute 28% of their operating cash at the end of the month*/
        double stateRemittance = 0.28 * state.cash;
        state.cash -= stateRemittance;
        federal.federalReserve += stateRemittance;

        //Calculate cash growth
        double cashGrowth = state.monthlyProfit - stateRemittance + state.federalAllocation;

        if (state.monthlyProfit > 0) {//Only pay debt if there's a surplus     
            if (state.debt > 0) {
                double interestPayment = state.debt * federalDebtInterest;
                state.cash -= interestPayment;
                state.debtPayment = interestPayment; // Amount of Debt Cleared (Interest only)
                if (state.inflationRate > 0.07) state.debtPayment *= 1.075;

                if (state.monthlyProfit > state.gdp * 0.02) {
                    double principalRepayment = state.monthlyProfit * 0.35;
                    state.debt -= principalRepayment;
                    state.cash -= principalRepayment;
                    state.debtPayment += principalRepayment; // If there's profit, monthly debt payment should also payoff principal
                    if (state.inflationRate > 0.07) state.debtPayment *= 1.075;
                }
            } else {
                state.cash += Math.abs(state.debt);
                state.debt = 0;
            } 
        }
        
        /*STATE RESERVE ACCUMULATION*/
        double withdraw;
        if (state.cash > 0) {
            //Save 30% of the months profit
            if (state.monthlyProfit > 0) {
                double reserveTransfer = state.cash * 0.0925; //Reserve 9.25% of monthly Operational Cash
                state.stateReserve += reserveTransfer;
                state.cash -= reserveTransfer;
            }
        } else {
            //If cash is less than 0, withdraw from reserves
            if (state.cash < 0) {
                double needed = Math.abs(state.cash);
                double withdrawn = Math.min(needed, state.stateReserve);
                state.stateReserve -= withdrawn;
                state.cash += withdrawn;

                if (state.cash < 0) BailOutCalculator.stateBailOut(states, federal, state.name);
            } 

        }  
 

        /*POPULATION GROWTH METRIC*/
        double popGrowth = 0.005; // 0.5% monthly
 
        if (state.stability < 40) popGrowth -= 0.008;
        if (state.taxRate > 0.22) popGrowth -= 0.006;
 
        state.population *= (1 + popGrowth);        
        state.population = Math.max(state.population, 5000); //Cap minimum population at 5000

        if (Double.isNaN(state.cash)) state.cash = minimumCash; //Reset if strange values appear
        if (Double.isNaN(state.gdp)) state.gdp = minimumGdp; //Reset if strange values appear
        if (Double.isNaN(state.population)) state.population = minimumPop; //Reset if strange values appear

        /*CALCULATE NEXT MONTH'S INFLATION RATE AND ADJUST BY ITS CONSTANT*/
        double deficitRatio = (-state.monthlyProfit/state.gdp);
        state.inflationRate = ((cashGrowth - gdpGrowth) * 0.03) + (deficitRatio * 0.0125);
        state.inflationRate /= inflationConstant;

        state.inflationRate = Math.max(-0.05, Math.min(state.inflationRate, 0.075));//Cap inflation rate

        
        
    }
}