package game.metrics;

import game.data.Constants;

public class AIMemory {

    /* POLICY MEMORY */
    private String lastPolicy;
    private int samePolicyCount;

    /* LAST SNAPSHOT VALUES */
    private double lastNominalGdp;
    private double lastRealGdp;
    private double lastDebt;
    private double lastCash;
    private double lastStability;

    /* ROLLING METRICS YEARLY*/
    private double rollingGrowth12M;
    private double rollingInflation12M;
    private double rollingProfit12M;
    private double rollingDebtRatio12M;

    /* STREAK TRACKING */
    private int deficitStreak;
    private int highInflationStreak;

    /* POLICY TIMERS */
    private int monthsSinceInfra;
    private int monthsSinceEducation;

    /* STABILITY TREND */
    private double stabilityChange;

    public AIMemory() {

        this.lastPolicy = Constants.NO_POLICY;
        this.samePolicyCount = 0;

        this.lastNominalGdp = 0;
        this.lastRealGdp = 0;
        this.lastDebt = 0;
        this.lastCash = 0;
        this.lastStability = 0;

        this.rollingGrowth12M = 0;
        this.rollingInflation12M = 0;
        this.rollingProfit12M = 0;
        this.rollingDebtRatio12M = 0;

        this.deficitStreak = 0;
        this.highInflationStreak = 0;

        this.monthsSinceInfra = 0;
        this.monthsSinceEducation = 0;

        this.stabilityChange = 0;
    }

    /* ================= GETTERS ================= */

    public String getLastPolicy() { return lastPolicy; }
    public int getSamePolicyCount() { return samePolicyCount; }

    public double getLastNominalGdp() { return lastNominalGdp; }
    public double getLastRealGdp() { return lastRealGdp; }
    public double getLastDebt() { return lastDebt; }
    public double getLastCash() { return lastCash; }
    public double getLastStability() { return lastStability; }

    public double getRollingGrowth12M() { return rollingGrowth12M; }
    public double getRollingInflation12M() { return rollingInflation12M; }
    public double getRollingProfit12M() { return rollingProfit12M; }
    public double getRollingDebtRatio12M() { return rollingDebtRatio12M; }

    public int getDeficitStreak() { return deficitStreak; }
    public int getHighInflationStreak() { return highInflationStreak; }

    public int getMonthsSinceInfra() { return monthsSinceInfra; }
    public int getMonthsSinceEducation() { return monthsSinceEducation; }

    public double getStabilityChange() { return stabilityChange; }

    /* ================= SETTERS ================= */

    public void setLastNominalGdp(double value) { this.lastNominalGdp = value; }
    public void setLastRealGdp(double value) { this.lastRealGdp = value; }
    public void setLastDebt(double value) { this.lastDebt = value; }
    public void setLastCash(double value) { this.lastCash = value; }
    public void setLastStability(double value) { this.lastStability = value; }
    public void setMonthsSinceInfra(int monthsSinceInfra) { this.monthsSinceInfra = monthsSinceInfra; }
    public void setMonthsSinceEducation(int monthsSinceEducation) { this.monthsSinceEducation = monthsSinceEducation; }

    /* ================= CORE UPDATE METHODS ================= */

    public void updatePolicyTracking(String currentPolicy) {
        if (currentPolicy == null || currentPolicy.isBlank())
            currentPolicy = Constants.NO_POLICY;

        if (currentPolicy.equals(this.lastPolicy)) {
            samePolicyCount++;
        } else {
            samePolicyCount = 0;
        }

        this.lastPolicy = currentPolicy;
    }

    public void updateRollingGrowth(double monthlyGrowth) {
        rollingGrowth12M = (rollingGrowth12M * 11 + monthlyGrowth) / 12;
    }

    public void updateRollingInflation(double monthlyInflation) {
        rollingInflation12M = (rollingInflation12M * 11 + monthlyInflation) / 12;
    }

    public void updateRollingProfit(double monthlyProfit) {
        rollingProfit12M = (rollingProfit12M * 11 + monthlyProfit) / 12;

        if (monthlyProfit < 0) deficitStreak++;
        else deficitStreak = 0;
    }

    public void updateRollingDebtRatio(double debtRatio) {
        rollingDebtRatio12M = (rollingDebtRatio12M * 11 + debtRatio) / 12;
    }

    public void updateInflationStreak(double inflationRate) {
        if (inflationRate > 0.05) highInflationStreak++;
        else highInflationStreak = 0;
    }

    public void updateStabilityChange(double currentStability) {
        stabilityChange = currentStability - lastStability;
        lastStability = currentStability;
    }

    /* ================= POLICY TIMERS ================= */

    public void incrementInfraCount() { monthsSinceInfra++; }
    public void resetInfraCount() { monthsSinceInfra = 0; }

    public void incrementEducationCount() { monthsSinceEducation++; }
    public void resetEducationCount() { monthsSinceEducation = 0; }


}