package game.engine;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import game.data.Constants;
import java.util.*;

public class FederalAccountingEngine {
    public static void federalAccounting(Map<String, StateEconomy> states, FederalEconomy federal) {
        /*National Statistics - (Number of states, Total GDP, Total Population)*/
        double natGdp = 0;
        long natPop = 0;
        double natgdpgrowth = 0.0;
        double natInflation = 0.0;
        int crashes = 0;
        Random rand = new Random();
        for (StateEconomy s : states.values()) {
            natGdp += s.realGdp;
            natPop += s.population;
            natgdpgrowth += s.gdpGrowth;
            crashes += s.crashCount;
            natInflation += s.inflationRate;
        }

        federal.nationalCrashes = crashes;
        federal.avgInflation = natInflation/Constants.NO_OF_STATES;
        federal.nationalGDP = natGdp;
        federal.nationalPopulation = natPop;
        federal.nationalGdpGrowth = natgdpgrowth/Constants.NO_OF_STATES;

        

        /*FEDERATION ACCOUNTING*/
        federal.oilPriceIndex = rand.nextInt(221) + 930; // Hovering aroung a hundreth of nigerias oil revenue per barrel (Price of a barrel) - 950 - 1150       
        
        
        //Oil Revenue
        federal.oilPool = Constants.BASE_OIL * federal.oilPriceIndex;

        //VAT Revenue
        federal.vatPool = (Constants.VAT/12) * federal.nationalGDP;
        federal.allocationPool = federal.oilPool + federal.vatPool;

        federal.federalReserve += federal.allocationPool * 0.10; //Reserve 10% for state bail out
        federal.operatingCash += federal.allocationPool * 0.25; //Reserve 25% for state bail out
        federal.allocationPool *= 0.65; //Update allocation pool

        /*INDIVIDUAL STATE ALLOCATION ACCOUNTING*/
        // 50% - Equal share among states
        // 30% - Population weighted
        // 20% - GDP Weighted

        for (StateEconomy s : states.values()) {
            //Apply allocation to state 
            s.federalAllocation = ((0.3 * (federal.allocationPool/Constants.NO_OF_STATES)) +
            (0.5 * federal.allocationPool * (s.population/federal.nationalPopulation)) +
            (0.2 * federal.allocationPool * (s.realGdp/Math.max(federal.nationalGDP, 1e-6))));

            //s.federalAllocation = Math.max(s.federalAllocation, 200000.0);
            if ((Double.isNaN(s.federalAllocation)) || (Double.isInfinite(s.federalAllocation))) s.federalAllocation = 0.0;
            s.monthlyRevenue += s.federalAllocation;


        }
    }
}
