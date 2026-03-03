package game.engine;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import game.data.Constants;
import java.util.*;
import game.economies.StateAttributes;

public class PolicyEngine {
    public static void applyPolicy(Map<String, StateEconomy> states, 
                                    FederalEconomy federal, String stat, String policy) {

        StateEconomy state = states.get(stat);
        double lastGdp = state.gdp; //Set last nominal gdp
        double lastRealGdp = state.realGdp; //Set last real gdp
        double lastCash = state.cash;

        double baseGrowth = 0.0005; // 0.25% per month
        double growthRate;
        double infraFactor;
        double stabFactor;
        double taxDistortion = 0;
        double debtPenalty;   
        state.policySpend = 0;  
        state.monthlySpend = 0;  
        state.monthlyRevenue = 0;
        state.monthlyProfit = 0;
        

        switch(policy) {
            case "Raise Taxes":
                state.taxRate += 0.015; //Increase taxes by 0.5% percent
                state.taxRate *= (1 - Math.log(state.getAttributes().getTaxSensitivity()));
                state.stability -= 1.3; //Mild Populace disapproval
                state.population -= 700;
                state.inflationRate *= 0.95;
                break;
            
            case "Cut Taxes":
                state.taxRate -= 0.015; //Reduce taxes by 0.5% percent
                state.taxRate *= (1 + Math.log(state.getAttributes().getTaxSensitivity()));
                state.stability += 1.1; //Mild Populace approval
                state.policySpend += state.gdp * 0.01; // small fiscal pressure
                state.population += 750;
                break;

            case "Invest Infrastructure":
                double infraSpend = state.gdp * 0.0325; //Each infrastructure expenditure costs 0.15% of state gdp
                state.infrastructure += 1.75;
                // Spread cost over a year
                state.policySpend += infraSpend; 
                double infraReturn = Math.log(1 + state.infrastructure) * 0.01;
                state.gdp *= 1 + (infraReturn * state.getAttributes().getInfraEfficiency()); //ATTRIBUTE ADJUSTMENT                
                break;

            case "Invest Education":
                //When educational investment occurs, education increases, then policy spend increases and cash decreases by policyspend
                double eduSpend = state.gdp * 0.02;  // 0.1% of gdp
                state.policySpend += eduSpend;
                state.stability += 1.2;  // gradual social effect
                // long-term productivity bump
                state.gdp *= 1 + (state.getAttributes().getEducationEfficiency() * 0.012);
                break;
            
            case "Boost Security":
                state.stability += 2;
                state.policySpend += (state.gdp * 0.006); //Increase policy spend by 0.6% of GDP
                state.population += 600;
                state.gdp *= 1.007;  // +0.4% slight boost
                break;

            case "Subsidise Transport & Food":
                double subsidyCost = state.gdp * 0.012;  // 0.8%
                // finance logic
                state.policySpend += subsidyCost;
                state.stability += 2.0;
                state.gdp *= 1.006;  // +0.6% slight boost
                break;

            case "Market Trader Support":
                double grantCost = state.gdp * 0.0105;  // 2%
                state.policySpend += grantCost; // finance logic
                state.stability += 1.8;
                state.gdp *= 1.0075; //+0.75% productivity micro-boost 
                state.infrastructure += 1;  // soft boost
                break;

            case "Borrow":
                //When borrowing, increase state cash, state debt and reduce allocation pool by 2% of gdp
                double borrowAmount = state.gdp * 0.03;  // 3%
                state.cash += borrowAmount;
                state.debt += borrowAmount;
                state.stability -= 1;
                state.taxRate *= 1.05;
                // interest risk rises slightly
                break;
            
            case "Austerity":
                //Austerity is harsh economic times. Cut govt. spending and raise tax aggresively.
                state.policySpend = 0.93 * state.policySpend;
                state.taxRate += 0.005;  // smaller increase
                state.stability -= 2;
                state.infrastructure -= 1;  // small infrastructure sell off
                state.population -= 900;  // short-term contraction
                state.inflationRate *= 0.87;
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
        
        double debtRatio = state.debt / Math.max(state.gdp, 1e-6);
        debtPenalty = 1 - Math.min(0.4, debtRatio * 0.25); //debt penalty should not be less than 0.4

        growthRate = (baseGrowth * infraFactor * stabFactor * debtPenalty);
        double infraEffect = 0.003 * Math.log(1 + state.infrastructure);
        growthRate += (infraEffect - taxDistortion);

        state.debtToGdpRatio = (state.debt/Math.max(state.gdp, 1e-6));
        if (state.debtToGdpRatio > 0.80 && state.debtToGdpRatio < 1.00) growthRate -= 0.005;
        if (state.debtToGdpRatio > 1.00) growthRate -= 0.01;





        /*Calculate GDP and monthly growth rate is capped at 3.75%*/
        growthRate = Math.max(-0.0375, Math.min(0.0375, growthRate));
        state.gdp = state.gdp * (1 + growthRate);
        double gdpGrowth = state.gdp - lastGdp; //calculate gdpgrowth.
        state.realGdp = state.gdp/(1 + state.inflationRate); // Calculate real gdp
        state.gdpGrowth = (state.realGdp - lastRealGdp)/lastRealGdp;

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
        collection = 0.65 + 0.35 * (state.stability / 100); 
        taxRevenue = state.gdp * state.taxRate * collection * 0.24;   
        state.monthlyRevenue = taxRevenue + nonTaxRevenue + state.federalAllocation;




        /*GOVERNMENT SPENDING GROWTH MODDEL */
        double baseSpend = state.gdp * 0.015; // 1.5% monthly
        baseSpend *= (1 + state.inflationRate);
        double populationBurden = state.population * Math.log(state.population) * 0.4;    
        state.monthlySpend = baseSpend + populationBurden + state.policySpend;   



        
        /*DEBT AND CASH MODEL */
        federal.debtInterest = 0.05 + (state.debt / Math.max(state.gdp, 1e-6)) * 0.01;
        state.monthlyProfit = state.monthlyRevenue - state.monthlySpend;
        double realizedProfit = state.monthlyProfit * (1 - (state.getAttributes().getCorruptionFactor() * 1.15));
        state.monthlyProfit = realizedProfit;
        state.cash += state.monthlyProfit;

        /*State's Contribute 28% of their operating cash at the end of the month*/
        double stateRemittance = Constants.REMITTANCE_RATE * state.monthlyProfit;
        double disasterContr = Constants.DISASTER_MGMT_RATE * state.monthlyProfit;
        state.cash -= stateRemittance;
        state.cash -= disasterContr;
        federal.federalReserve += stateRemittance;
        federal.operatingCash += disasterContr;

        //Calculate cash growth
        double cashGrowth = state.cash - lastCash;
        if (state.monthlyProfit > 0) {//Only pay debt if there's a surplus     
            if (state.debt > 0) {
                double interestPayment = state.debt * federal.debtInterest;
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
        if (state.cash > 0) {
            //Save 30% of the months profit
            if (state.monthlyProfit > 0) {
                double reserveTransfer = state.monthlyProfit * 0.125; //Reserve 9.25% of monthly Operational Cash
                state.stateReserve += reserveTransfer;
                state.cash -= reserveTransfer;
            }
        } else {
            //If cash is less than 0, withdraw from reserves
            BailOutCalculator.stateBailOut(states, federal, state.name);
        }  
 



        /*POPULATION GROWTH METRIC*/ 
        double popGrowth = 0.005; // 0.5% monthly
        if (state.stability < 40) popGrowth -= 0.0025;
        if (state.taxRate > 0.2) popGrowth -= 0.00175;
 
        state.population *= (1 + popGrowth);
        state.population = Math.max(state.population, 5000); //Cap minimum population at 5000


       
       
       
        /*CALCULATE NEXT MONTH'S INFLATION RATE*/
        double deficitRatio = (-state.monthlyProfit / state.gdp);

        double moneyGrowth = cashGrowth / state.gdp;
        double realGrowth  = (gdpGrowth)/ state.gdp; //ATTRIBUTE ADJUSTMENT

        //For now inflation is gonna be a rounded next double
        Random ran = new Random();
        state.inflationRate = ran.nextDouble() * 0.10;
        /*ATTRIBUTE EVALUATIONS */

        /*Reset if NaN or infinite values emerge*/
        if ((Double.isNaN(state.cash)) || (Double.isInfinite(state.cash))) state.cash = Constants.MINIMUM_CASH; 
        if ((Double.isNaN(state.gdp)) || (Double.isInfinite(state.gdp))) state.gdp = Constants.MINIMUM_GDP; 
        if ((Double.isNaN(state.population)) || (Double.isInfinite(state.population))) state.population = Constants.MINIMUM_POPULATION; 
        
        /*
        if (state.cash == 0) {
            throw new ArithmeticException(state.name + " has no money");
        }
        */

        
    }
}