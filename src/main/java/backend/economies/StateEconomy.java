package economies;

import events.*;
import metrics.AIMemory;

public class StateEconomy {

    private String name;
    private double gdp;
    private double realGdp;
    private long population;
    private double stability;
    private double taxRate;
    private double debt;
    private double debtPayment;
    private double cash;
    private int crashCount;
    private int infrastructure;
    private double monthlyRevenue;
    private double monthlySpend;
    private double monthlyProfit;
    private double policySpend;
    private double stateReserve;
    private double debtToGdpRatio;
    private double rankingScore;
    private int position;
    private double federalAllocation;
    private double gdpGrowth;
    private String policy;
    private GovType governmentType;
    private double inflationRate;
    private AIMemory memory = new AIMemory();
    private StateAttributes attributes;
    private EventRisk eventRisk;
    private String eventName;

    public StateEconomy(String name, double gdp, double realGdp, long population, double stability, double taxRate, double debt,
                        double debtPayment, double cash, int crashCount, int infrastructure, double monthlyRevenue,
                        double monthlySpend, double monthlyProfit, double policySpend, double stateReserve,
                        double debtToGdpRatio, double rankingScore, int position, double federalAllocation,
                        double gdpGrowth, GovType governmentType, double inflationRate, StateAttributes attributes,
                        EventRisk eventRisk, String policy) {

        this.name = name;
        this.gdp = gdp;
        this.realGdp = realGdp;
        this.population = population;
        this.stability = stability;
        this.taxRate = taxRate;
        this.debt = debt;
        this.debtPayment = debtPayment;
        this.cash = cash;
        this.crashCount = crashCount;
        this.infrastructure = infrastructure;
        this.monthlyRevenue = monthlyRevenue;
        this.monthlySpend = monthlySpend;
        this.monthlyProfit = monthlyProfit;
        this.policySpend = policySpend;
        this.stateReserve = stateReserve;
        this.debtToGdpRatio = debtToGdpRatio;
        this.rankingScore = rankingScore;
        this.position = position;
        this.federalAllocation = federalAllocation;
        this.gdpGrowth = gdpGrowth;
        this.governmentType = governmentType;
        this.inflationRate = inflationRate;
        this.attributes = attributes;
        this.eventRisk = eventRisk;
        this.policy = policy;
    }

    public StateEconomy(StateEconomy other) {
        this.name = other.name;
        this.gdp = other.gdp;
        this.realGdp = other.realGdp;
        this.population = other.population;
        this.stability = other.stability;
        this.taxRate = other.taxRate;
        this.debt = other.debt;
        this.debtPayment = other.debtPayment;
        this.cash = other.cash;
        this.crashCount = other.crashCount;
        this.infrastructure = other.infrastructure;
        this.monthlyRevenue = other.monthlyRevenue;
        this.monthlySpend = other.monthlySpend;
        this.monthlyProfit = other.monthlyProfit;
        this.policySpend = other.policySpend;
        this.stateReserve = other.stateReserve;
        this.debtToGdpRatio = other.debtToGdpRatio;
        this.rankingScore = other.rankingScore;
        this.position = other.position;
        this.federalAllocation = other.federalAllocation;
        this.gdpGrowth = other.gdpGrowth;
        this.governmentType = other.governmentType;
        this.inflationRate = other.inflationRate;
        this.attributes = other.attributes;
        this.eventRisk = other.eventRisk;
        this.policy = other.policy;
    }

    // --- Getters & Setters ---

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getGdp() { return gdp; }
    public void setGdp(double gdp) { this.gdp = gdp; }

    public double getRealGdp() { return realGdp; }
    public void setRealGdp(double realGdp) { this.realGdp = realGdp; }

    public long getPopulation() { return population; }
    public void setPopulation(long population) { this.population = population; }

    public double getStability() { return stability; }
    public void setStability(double stability) { this.stability = stability; }

    public double getTaxRate() { return taxRate; }
    public void setTaxRate(double taxRate) { this.taxRate = taxRate; }

    public double getDebt() { return debt; }
    public void setDebt(double debt) { this.debt = debt; }

    public double getDebtPayment() { return debtPayment; }
    public void setDebtPayment(double debtPayment) { this.debtPayment = debtPayment; }

    public double getCash() { return cash; }
    public void setCash(double cash) { this.cash = cash; }

    public int getCrashCount() { return crashCount; }
    public void setCrashCount(int crashCount) { this.crashCount = crashCount; }

    public int getInfrastructure() { return infrastructure; }
    public void setInfrastructure(int infrastructure) { this.infrastructure = infrastructure; }

    public double getMonthlyRevenue() { return monthlyRevenue; }
    public void setMonthlyRevenue(double monthlyRevenue) { this.monthlyRevenue = monthlyRevenue; }

    public double getMonthlySpend() { return monthlySpend; }
    public void setMonthlySpend(double monthlySpend) { this.monthlySpend = monthlySpend; }

    public double getMonthlyProfit() { return monthlyProfit; }
    public void setMonthlyProfit(double monthlyProfit) { this.monthlyProfit = monthlyProfit; }

    public double getPolicySpend() { return policySpend; }
    public void setPolicySpend(double policySpend) { this.policySpend = policySpend; }

    public double getStateReserve() { return stateReserve; }
    public void setStateReserve(double stateReserve) { this.stateReserve = stateReserve; }

    public double getDebtToGdpRatio() { return debtToGdpRatio; }
    public void setDebtToGdpRatio(double debtToGdpRatio) { this.debtToGdpRatio = debtToGdpRatio; }

    public double getRankingScore() { return rankingScore; }
    public void setRankingScore(double rankingScore) { this.rankingScore = rankingScore; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }

    public double getFederalAllocation() { return federalAllocation; }
    public void setFederalAllocation(double federalAllocation) { this.federalAllocation = federalAllocation; }

    public double getGdpGrowth() { return gdpGrowth; }
    public void setGdpGrowth(double gdpGrowth) { this.gdpGrowth = gdpGrowth; }

    public GovType getGovernmentType() { return governmentType; }
    public void setGovernmentType(GovType governmentType) { this.governmentType = governmentType; }

    public double getInflationRate() { return inflationRate; }
    public void setInflationRate(double inflationRate) { this.inflationRate = inflationRate; }

    public AIMemory getMemory() { return memory; }
    public void setMemory(AIMemory memory) { this.memory = memory; }

    public StateAttributes getAttributes() { return attributes; }
    public void setAttributes(StateAttributes attributes) { this.attributes = attributes; }

    public EventRisk getEventRisk() { return eventRisk; }
    public void setEventRisk(EventRisk eventRisk) { this.eventRisk = eventRisk; }

    public String getPolicy() { return policy; }
    public void setPolicy(String policy) { this.policy = policy; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
}