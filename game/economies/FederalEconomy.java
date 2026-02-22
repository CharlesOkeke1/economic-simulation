package game.economies;
import java.util.*;

public  class FederalEconomy {
    public double federalReserve;
    public int oilPriceIndex;
    public double oilPool;
    public double vatPool;
    public double allocationPool;
    public double nationalGDP;
    public long nationalPopulation;

    public FederalEconomy(double federalReserve, int oilPriceIndex, double oilPool, 
                        double vatPool, double allocationPool, double nationalGDP,
                        long nationalPopulation) {
    
        this.federalReserve = federalReserve; //10% of allocationpool
        this.oilPriceIndex = oilPriceIndex;
        this.oilPool = oilPool;
        this.vatPool = vatPool;
        this.allocationPool = allocationPool;
        this.nationalGDP = nationalGDP;
        this.nationalPopulation = nationalPopulation;
    }
}