package game.ui;
import game.economies.StateEconomy;
import game.economies.FederalEconomy;
import utils.MyUtils;
import utils.EnumToString;
import java.util.*;


public class PrintReports {
    public static void printStateReport(Map<String, StateEconomy> s, FederalEconomy fed, String countyName, int count, String type) {
        StateEconomy county = s.get(countyName);

        switch(type) {
            case "Initial":
                MyUtils.SteppedPrinting("===== Here is " + countyName + " State's Current Economic Situation =====", 30);
                break;
            
            case "Monthly":
                MyUtils.SteppedPrinting("===== " + countyName + " Month " + (count + 1) + " Report =====", 30);
                break;

            case "Final":
                MyUtils.SteppedPrinting("===== Final State of " + countyName + "'s Economic Situation =====", 30);
                break;
        }
        MyUtils.SteppedPrinting("GOVERNMENT TYPE: " + EnumToString.govtType(county.governmentType), 20);   
        MyUtils.SteppedPrinting("REAL GDP: " + MyUtils.formatNumber(county.realGdp)  + " Naira", 20);
        MyUtils.SteppedPrinting("NOMINAL GDP: " + MyUtils.formatNumber(county.gdp)  + " Naira", 20);
        MyUtils.SteppedPrinting("POPULATION: " + MyUtils.formatNumber(county.population)  + " People", 20);
        MyUtils.SteppedPrinting("POSITION: " + MyUtils.Ordinalize(county.position) + " position out of 37 the states (Including Abuja)", 20);
        MyUtils.SteppedPrinting("PERFORMANCE SCORE: " + String.format("%.2f", county.rankingScore), 20);
        MyUtils.SteppedPrinting("STABILITY: " + MyUtils.formatNumber(county.stability), 20);
        MyUtils.SteppedPrinting("DEBT: " + MyUtils.formatNumber(county.debt) + " Naira", 20);
        MyUtils.SteppedPrinting("FEDERAL ALLOCATION: " + MyUtils.formatNumber(county.federalAllocation) + " Naira", 20);
        MyUtils.SteppedPrinting("STATE RESERVE: " + MyUtils.formatNumber(county.stateReserve) + " Naira", 20);
        MyUtils.SteppedPrinting("STATE INFRASTRUCTURE: " + MyUtils.formatNumber(county.infrastructure), 20);
        MyUtils.SteppedPrinting("STATE PROFIT: " + MyUtils.formatNumber(county.monthlyProfit) + " Naira", 20);
        MyUtils.SteppedPrinting("STATE MONTHLY SPEND: " + MyUtils.formatNumber(county.monthlySpend) + " Naira", 20);
        MyUtils.SteppedPrinting("STATE MONTHLY REVENUE: " + MyUtils.formatNumber(county.monthlyRevenue) + " Naira", 20);
        MyUtils.SteppedPrinting("STATE POLICY SPEND: " + MyUtils.formatNumber(county.policySpend) + " Naira", 20);
        MyUtils.SteppedPrinting("BAIL OUT COUNT: " + county.crashCount, 20);
        MyUtils.SteppedPrinting("PRICE OF OIL PER BARREL: " + MyUtils.formatNumber(fed.oilPriceIndex)  + " Naira", 20);  
        MyUtils.SteppedPrinting("OPERATING CASH: " + MyUtils.formatNumber(county.cash)  + " Naira", 20);  
        MyUtils.SteppedPrinting("INFLATION RATE: " + String.format("%.2f",county.inflationRate * 100)  + "%", 20);  
        System.out.println(" ");  
    }

    public static void printFederationReport(FederalEconomy fed, int count, String type) {  
        switch(type) {
            case "Initial":
                MyUtils.SteppedPrinting("===== Here is Nigeria's Current Economic Situation =====", 30);
                break;
            
            case "Monthly":
                MyUtils.SteppedPrinting("===== NIGERIA'S MONTH " + (count + 1) + " PEFORMANCE REPORT =====", 30);
                break;

            case "Final":
                MyUtils.SteppedPrinting("===== NIGERIA'S FINAL ECONOMIC PEFORMANCE REPORT AFTER " + (count) + " MONTHS =====", 30);
                break;
        } 

        MyUtils.SteppedPrinting("NATIONAL REAL GDP: " + MyUtils.formatNumber(fed.nationalGDP) + " Naira", 25);
        MyUtils.SteppedPrinting("NATIONAL POPULATION: " + MyUtils.formatNumber(fed.nationalPopulation) + " People", 25);
        MyUtils.SteppedPrinting("FEDERAL RESERVE VALUE " + MyUtils.formatNumber(fed.federalReserve) + " Naira", 25);
        MyUtils.SteppedPrinting("FEDERATION ALLOCATION THIS MONTH: " + MyUtils.formatNumber(fed.allocationPool) + " Naira", 25);  
        MyUtils.SteppedPrinting("PRICE OF OIL PER BARREL: " + MyUtils.formatNumber(fed.oilPriceIndex) + " Naira", 25);  
        System.out.println(" ");  
    }
}