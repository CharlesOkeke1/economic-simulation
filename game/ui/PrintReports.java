package game.ui;
import game.economies.StateEconomy;
import game.economies.FederalEconomy;
import game.data.Constants;
import utils.MyUtils;
import utils.EnumToString;
import java.util.*;

public class PrintReports {
     /*PRINT STATE REPORT BASED ON THE TYPE(MONTHLY, INITIAL OR FINAL) */
    public static void printStateReport(Map<String, StateEconomy> s, FederalEconomy fed, String countyName, int count, String type) {
        StateEconomy county = s.get(countyName);
        switch(type) {
            case "Initial":
                MyUtils.SteppedPrinting("===== Here is " + countyName + " State's Current Economic Situation =====", Constants.REPORT_DELAY_TIME);
                break;
            
            case "Monthly":
                MyUtils.SteppedPrinting("===== " + countyName + " Month " + (count - 1) + " Report =====", Constants.REPORT_DELAY_TIME);
                break;

            case "Final":
                MyUtils.SteppedPrinting("===== Final State of " + countyName + "'s Economic Situation =====", Constants.REPORT_DELAY_TIME);
                break;
        }
        MyUtils.SteppedPrinting("GOVERNMENT TYPE: " + EnumToString.govtType(county.governmentType), Constants.REPORT_DELAY_TIME);   
        MyUtils.SteppedPrinting("REAL GDP: " + MyUtils.formatNumber(county.realGdp)  + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("NOMINAL GDP: " + MyUtils.formatNumber(county.gdp)  + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("POPULATION: " + MyUtils.formatNumber(county.population)  + " People", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("POSITION: " + MyUtils.Ordinalize(county.position) + " position out of 37 the states (Including Abuja)", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("PERFORMANCE SCORE: " + String.format("%.2f", county.rankingScore), Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STABILITY: " + MyUtils.formatNumber(county.stability), Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("DEBT: " + MyUtils.formatNumber(county.debt) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("FEDERAL ALLOCATION: " + MyUtils.formatNumber(county.federalAllocation) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STATE RESERVE: " + MyUtils.formatNumber(county.stateReserve) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STATE INFRASTRUCTURE: " + MyUtils.formatNumber(county.infrastructure), Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STATE PROFIT: " + MyUtils.formatNumber(county.monthlyProfit) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STATE MONTHLY SPEND: " + MyUtils.formatNumber(county.monthlySpend) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STATE MONTHLY REVENUE: " + MyUtils.formatNumber(county.monthlyRevenue) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STATE POLICY SPEND: " + MyUtils.formatNumber(county.policySpend) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("BAIL OUT COUNT: " + county.crashCount, Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("PRICE OF OIL PER BARREL: " + MyUtils.formatNumber(fed.oilPriceIndex)  + " Naira", Constants.REPORT_DELAY_TIME);  
        MyUtils.SteppedPrinting("OPERATING CASH: " + MyUtils.formatNumber(county.cash)  + " Naira", Constants.REPORT_DELAY_TIME);  
        MyUtils.SteppedPrinting("INFLATION RATE: " + String.format("%.2f",county.inflationRate * 100)  + "%", Constants.REPORT_DELAY_TIME);  
        System.out.println(" ");  
    }

    /*PRINT FEDERAL REPORT BASED ON THE TYPE OF REPORT (MONTHLY, INITIAL OR FINAL) */
    public static void printFederationReport(FederalEconomy fed, int count, String type) {  
        switch(type) {
            case "Initial":
                MyUtils.SteppedPrinting("===== Here is Nigeria's Current Economic Situation =====", Constants.REPORT_DELAY_TIME);
                break;
            
            case "Monthly":
                MyUtils.SteppedPrinting("===== NIGERIA'S MONTH " + count + " PEFORMANCE REPORT =====", Constants.REPORT_DELAY_TIME);
                break;

            case "Final":
                MyUtils.SteppedPrinting("===== NIGERIA'S FINAL ECONOMIC PEFORMANCE REPORT AFTER " + (count) + " MONTHS =====", Constants.REPORT_DELAY_TIME);
                break;
        } 

        MyUtils.SteppedPrinting("NATIONAL REAL GDP: " + MyUtils.formatNumber(fed.nationalGDP) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("NATIONAL POPULATION: " + MyUtils.formatNumber(fed.nationalPopulation) + " People", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("FEDERAL RESERVE VALUE " + MyUtils.formatNumber(fed.federalReserve) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("FEDERATION ACCOUNT BALANCE: " + MyUtils.formatNumber(fed.operatingCash) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("FEDERATION ALLOCATION THIS MONTH: " + MyUtils.formatNumber(fed.allocationPool) + " Naira", Constants.REPORT_DELAY_TIME);  
        MyUtils.SteppedPrinting("PRICE OF OIL PER BARREL: " + MyUtils.formatNumber(fed.oilPriceIndex) + " Naira", Constants.REPORT_DELAY_TIME);  
        System.out.println(" ");  
    }
}