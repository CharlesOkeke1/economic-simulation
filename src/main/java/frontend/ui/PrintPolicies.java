package ui;

import utils.MyUtils;
import data.Constants;

public class PrintPolicies {
    public static void policies() {
        String[] policies = {"Raise Taxes", "Cut Taxes", "Invest Infrastructure", "Invest Education", "Boost Security", "Borrow", "Austerity", "Market Trader Support", "Subsidise Transport & Food"};
        StringBuilder sb = new StringBuilder("");
        int lastIdx = policies.length - 1;

        sb.append("{");
        for (int i = 0; i < policies.length; i++) {
            if (policies[i].equals(policies[lastIdx])) sb.append(policies[i]);
            else sb.append(policies[i] + ", ");
        }
        sb.append("}");

        MyUtils.SteppedPrinting("Your available policies are - " + sb.toString(), Constants.REPORT_DELAY_TIME);

    }
}
