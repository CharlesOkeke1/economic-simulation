package game.engine;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import java.util.*;

public class FederalAccountingEngine {
    public static void federalAccounting(Map<String, StateEconomy> states, FederalEconomy federal) {
        /*National Statistics - (Number of states, Total GDP, Total Population)*/
        double natGdp = 0;
        long natPop = 0;
        int noOfStates = 0;
        Random rand = new Random();
        for (StateEconomy s : states.values()) {
            natGdp = natGdp + s.realGdp;
            natPop = natPop + s.population;
            noOfStates++;
        }

        federal.nationalGDP = natGdp;
        federal.nationalPopulation = natPop;

        /*FEDERATION ACCOUNTING*/
        federal.oilPriceIndex = rand.nextInt(221) + 900; // Hovering aroung a hundreth of nigerias oil revenue per barrel (Price of a barrel)

        //Peg Oil Price Index between 800 and
        long baseOil = 46000; //Nigeria's average oil production at 46M barrels per month. // **IMPORTANT** This has been scaled down again for now
        double vat = 0.075; //Nigeria's VAT at 7.5%.
        
        if (federal.oilPriceIndex > 1120) federal.oilPriceIndex = 1120;   
        if (federal.oilPriceIndex < 900) federal.oilPriceIndex = 900;  
        
        //Oil Revenue
        federal.oilPool = baseOil * federal.oilPriceIndex;

        //VAT Revenue
        federal.vatPool = (vat/12) * federal.nationalGDP;
        federal.allocationPool = federal.oilPool + federal.vatPool;

        federal.federalReserve += federal.allocationPool * 0.2; //Reserve 20% for state bail out
        federal.allocationPool = federal.allocationPool * 0.8; //Update allocation pool

        /*INDIVIDUAL STATE ALLOCATION ACCOUNTING*/
        // 50% - Equal share among states
        // 30% - Population weighted
        // 20% - GDP Weighted

        for (StateEconomy s : states.values()) {
            //Apply allocation to state 
            s.federalAllocation = ((0.5 * (federal.allocationPool/noOfStates)) +
            (0.3 * federal.allocationPool * (s.population/federal.nationalPopulation)) + 
            (0.2 * federal.allocationPool * (s.gdp/Math.max(federal.nationalGDP, 1e-6))));

            s.cash += s.federalAllocation;


        }
    }
}
