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
    public double nationalGdpGrowth;
    public int nationalCrashes;
    public double avgInflation;
    public double debtInterest;

    public FederalEconomy(double federalReserve, int oilPriceIndex, double oilPool, 
                        double vatPool, double allocationPool, double nationalGDP,
                        long nationalPopulation, double operatingCash, 
                        double nationalGdpGrowth, int nationalCrashes, double avgInflation,
                        double debtInterest) {
    
        this.federalReserve = federalReserve;
        this.oilPriceIndex = oilPriceIndex;
        this.oilPool = oilPool;
        this.vatPool = vatPool;
        this.allocationPool = allocationPool;
        this.nationalGDP = nationalGDP;
        this.nationalPopulation = nationalPopulation;
        this.operatingCash = operatingCash;
        this.nationalGdpGrowth = nationalGdpGrowth;
        this.nationalCrashes = nationalCrashes;
        this.avgInflation = avgInflation;
        this.debtInterest = debtInterest;
    }
}