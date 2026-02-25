package game.economies;
import java.util.*;
import game.economies.GovType;

/*State Economy object contains all fields each state has*/
public class StateEconomy {
        public String name;
        public double gdp;
        public double realGdp;
        public long population;
        public double stability;
        public double taxRate;
        public double debt;
        public double debtPayment;
        public double cash;
        public int crashCount;
        public int infrastructure;
        public double monthlyRevenue;
        public double monthlySpend;
        public double monthlyProfit;
        public double policySpend;
        public double stateReserve;
        public double debtToGdpRatio;
        public double rankingScore;
        public int position;
        public double federalAllocation;
        public double gdpGrowth;
        public GovType governmentType;
        public double inflationRate;

        public StateEconomy(String name, double gdp, double realGdp, long population, double stability, double taxRate, double debt, 
                            double debtPayment, double cash, int crashCount, int infrastructure, double monthlyRevenue, 
                            double monthlySpend, double monthlyProfit, double policySpend, double stateReserve, 
                            double debtToGdpRatio, double rankingScore, int position, double federalAllocation, 
                            double gdpGrowth, GovType governmentType, double inflationRate) {
            
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
            this.monthlySpend =  monthlySpend;
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
        }
    }