package engine;
import economies.FederalEconomy;
import economies.StateEconomy;
import data.Constants;
import gui.AppMain;

import java.util.*;

public class FederalAccountingEngine {
    public static void federalAccounting() {
        /*National Statistics - (Number of states, Total GDP, Total Population)*/
        Map<String, StateEconomy> states = AppMain.getStateMap();
        FederalEconomy federal = AppMain.getFederal();

        double natGdp = 0;
        long natPop = 0;
        double natgdpgrowth = 0.0;
        double natInflation = 0.0;
        int crashes = 0;
        Random rand = new Random();

        for (StateEconomy s : states.values()) {
            natGdp += s.getRealGdp();
            natPop += s.getPopulation();
            natgdpgrowth += s.getGdpGrowth();
            crashes += s.getCrashCount();
            natInflation += s.getInflationRate();
        }

        federal.nationalCrashes = crashes;
        federal.avgInflation = natInflation/Constants.NO_OF_STATES;
        federal.nationalGDP = natGdp;
        federal.nationalPopulation = natPop;
        federal.nationalGdpGrowth = natgdpgrowth/Constants.NO_OF_STATES;

        /*FEDERATION ACCOUNTING*/
        federal.oilPriceIndex = rand.nextInt(221) + 830; // Hovering around a hundredth of Nigeria's oil revenue per barrel (Price of a barrel) - 950 - 1150
        
        //Oil Revenue
        federal.oilPool = Constants.BASE_OIL * federal.oilPriceIndex;

        //VAT Revenue
        federal.vatPool = (Constants.VAT/12) * federal.nationalGDP;
        federal.allocationPool = federal.oilPool + federal.vatPool;

        federal.federalReserve += federal.allocationPool * 0.10; //Reserve 10% for state bail out
        federal.operatingCash += federal.allocationPool * 0.25; //Reserve 25% for state bail out
        federal.allocationPool *= 0.65; //Update allocation pool

        /*INDIVIDUAL STATE ALLOCATION ACCOUNTING*/
        // 30% - Equal share among states
        // 50% - Population weighted
        // 20% - GDP Weighted

        for (StateEconomy s : states.values()) {
            //Apply allocation to state
            double fedAlloc;
            fedAlloc = ((0.35 * (federal.allocationPool/Constants.NO_OF_STATES)) +
            (0.4 * federal.allocationPool * ((double) s.getPopulation() /federal.nationalPopulation)) +
            (0.25 * federal.allocationPool * (s.getRealGdp()/Math.max(federal.nationalGDP, 1e-6))));
            s.setFederalAllocation(fedAlloc);

            if ((Double.isNaN(fedAlloc)) || (Double.isInfinite(fedAlloc))) fedAlloc = 0.0;
            s.setFederalAllocation(fedAlloc);
        }
    }
}
