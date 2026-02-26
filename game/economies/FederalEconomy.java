package game.economies;

public  class FederalEconomy {
    public double federalReserve;
    public int oilPriceIndex;
    public double oilPool;
    public double vatPool;
    public double allocationPool;
    public double nationalGDP;
    public long nationalPopulation;
    public double operatingCash;

    public FederalEconomy(double federalReserve, int oilPriceIndex, double oilPool, 
                        double vatPool, double allocationPool, double nationalGDP,
                        long nationalPopulation, double operatingCash) {
    
        this.federalReserve = federalReserve;
        this.oilPriceIndex = oilPriceIndex;
        this.oilPool = oilPool;
        this.vatPool = vatPool;
        this.allocationPool = allocationPool;
        this.nationalGDP = nationalGDP;
        this.nationalPopulation = nationalPopulation;
        this.operatingCash = operatingCash;
    }
}