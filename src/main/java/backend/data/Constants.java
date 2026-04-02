package data;

public class Constants {

    public Constants() {
    }

    public static final int NO_OF_STATES = 37;
    public static final double VAT = 0.075;
    public static final double MINIMUM_CASH = 600000.0;
    public static final long MINIMUM_POPULATION = 10000;
    public static final double MINIMUM_INFLATION = 0.0375;
    public static final double MINIMUM_GDP = 4000000.0;
    public static final long BASE_OIL = 38000; //Nigeria's average oil production at 46M barrels per month. // **IMPORTANT** This has been scaled down
    public static final int REPORT_DELAY_TIME = 3;
    public static final String NO_POLICY = "NO_POLICY";
    public static final double TARGET_GDP_PER_CAP = 450.0;
    public static final double REMITTANCE_RATE = 0.25;
    public static final double DISASTER_MGMT_RATE = 0.2;
    public static final int CHART_WINDOW_SIZE = 30;
    public static final int ELECTION_SPACING = 18;
    public static double FEDERAL_DEBT_INTEREST = 0.025;
    public static final int TOP_STATES_COUNT = 5;
    public static final String[] POLICY_LIST = {
            "Raise Taxes",
            "Cut Taxes",
            "Invest Infrastructure",
            "Invest Education",
            "Boost Security",
            "Borrow",
            "Austerity",
            "Market Trader Support",
            "Subsidise Transport & Food",

            // New targeted policies
            "Close Tax Loopholes",
            "Expand VAT Base",
            "Invest Agriculture",
            "Invest Industry",
            "Defend Currency",
            "Debt Restructuring"
    };
}