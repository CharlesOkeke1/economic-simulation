package ui;
import economies.StateEconomy;
import economies.FederalEconomy;
import data.Constants;
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
        MyUtils.SteppedPrinting("GOVERNMENT TYPE: " + EnumToString.convert(county.getGovernmentType(), "_"), Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("REAL GDP GROWTH RATE: " + String.format("%.2f", county.getGdpGrowth() * 100) + "%", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STATE TAX RATE: " + String.format("%.2f", county.getTaxRate() * 100) + "%", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("AEVRAGE NATIONAL REAL GDP GROWTH RATE: " + String.format("%.2f", fed.nationalGdpGrowth * 100) + "%", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("REAL GDP: " + MyUtils.formatNumber(county.getRealGdp())  + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("NOMINAL GDP: " + MyUtils.formatNumber(county.getGdp())  + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("POPULATION: " + MyUtils.formatNumber(county.getPopulation())  + " People", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("POSITION: " + MyUtils.Ordinalize(county.getPosition()) + " position out of 37 the states (Including Abuja)", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("PERFORMANCE SCORE: " + String.format("%.2f", county.getRankingScore()), Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STABILITY: " + MyUtils.formatNumber(county.getStability()), Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("DEBT: " + MyUtils.formatNumber(county.getDebt()) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("FEDERAL ALLOCATION: " + MyUtils.formatNumber(county.getFederalAllocation()) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STATE RESERVE: " + MyUtils.formatNumber(county.getStateReserve()) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STATE INFRASTRUCTURE: " + MyUtils.formatNumber(county.getInfrastructure()), Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STATE PROFIT: " + MyUtils.formatNumber(county.getMonthlyProfit()) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STATE MONTHLY SPEND: " + MyUtils.formatNumber(county.getMonthlySpend()) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STATE MONTHLY REVENUE: " + MyUtils.formatNumber(county.getMonthlyRevenue()) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("STATE POLICY SPEND: " + MyUtils.formatNumber(county.getPolicySpend()) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("BAIL OUT COUNT: " + county.getCrashCount(), Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("PRICE OF OIL PER BARREL: " + MyUtils.formatNumber(fed.oilPriceIndex) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("OPERATING CASH: " + MyUtils.formatNumber(county.getCash())  + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("INFLATION RATE: " + String.format("%.2f", county.getInflationRate() * 100)  + "%", Constants.REPORT_DELAY_TIME);
    }

    /*PRINT FEDERAL REPORT BASED ON THE TYPE OF REPORT (MONTHLY, INITIAL OR FINAL) */
    public static void printFederationReport(FederalEconomy fed, int count, String type) {  
        switch(type) {
            case "Initial":
                MyUtils.SteppedPrinting("===== Here is Nigeria's Current Economic Situation =====", Constants.REPORT_DELAY_TIME);
                break;
            
            case "Monthly":
                MyUtils.SteppedPrinting("===== NIGERIA'S MONTH " + (count - 1) + " PEFORMANCE REPORT =====", Constants.REPORT_DELAY_TIME);
                break;

            case "Final":
                MyUtils.SteppedPrinting("===== NIGERIA'S FINAL ECONOMIC PEFORMANCE REPORT AFTER " + (count) + " MONTHS =====", Constants.REPORT_DELAY_TIME);
                break;
        } 

        MyUtils.SteppedPrinting("NATIONAL REAL GDP: " + MyUtils.formatNumber(fed.nationalGDP) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("NATIONAL POPULATION: " + MyUtils.formatNumber(fed.nationalPopulation) + " People", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("AVERAGE NATIONAL INFLATION: " + String.format("%.2f", fed.avgInflation * 100) + "%", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("FEDERAL RESERVE VALUE " + MyUtils.formatNumber(fed.federalReserve) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("FEDERATION ACCOUNT BALANCE: " + MyUtils.formatNumber(fed.operatingCash) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("FEDERATION ALLOCATION THIS MONTH: " + MyUtils.formatNumber(fed.allocationPool) + " Naira", Constants.REPORT_DELAY_TIME);  
        MyUtils.SteppedPrinting("PRICE OF OIL PER BARREL: " + MyUtils.formatNumber(fed.oilPriceIndex) + " Naira", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("TOTAL NATIONAL CRASHES: " + MyUtils.formatNumber(fed.nationalCrashes), Constants.REPORT_DELAY_TIME);  

        System.out.println(" ");  
    }
}