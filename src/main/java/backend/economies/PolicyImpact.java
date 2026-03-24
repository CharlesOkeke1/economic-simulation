package economies;

public class PolicyImpact {

    private final double gdpChange;
    private final double inflationChange;
    private final double stabilityChange;
    private final double debtChange;
    private final double cashChange;

    public PolicyImpact(double gdpChange,
                        double inflationChange,
                        double stabilityChange,
                        double debtChange,
                        double cashChange) {
        this.gdpChange = gdpChange;
        this.inflationChange = inflationChange;
        this.stabilityChange = stabilityChange;
        this.debtChange = debtChange;
        this.cashChange = cashChange;
    }

    public double getGdpChange() {
        return gdpChange;
    }

    public double getInflationChange() {
        return inflationChange;
    }

    public double getStabilityChange() {
        return stabilityChange;
    }

    public double getDebtChange() {
        return debtChange;
    }

    public double getCashChange() {
        return cashChange;
    }

    /*
    // Optional: helper for UI formatting (+/- sign)
    public static String format(double value) {
        return (value >= 0 ? "+" : "") + String.format("%.2f", value);
    }

    @Override
    public String toString() {
        return "PolicyImpact{" +
                "gdpChange=" + gdpChange +
                ", inflationChange=" + inflationChange +
                ", stabilityChange=" + stabilityChange +
                ", debtChange=" + debtChange +
                ", cashChange=" + cashChange +
                '}';
    }

     */
}