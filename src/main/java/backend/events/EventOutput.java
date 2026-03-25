package events;
import java.util.*;

public class EventOutput {
    public static Map.Entry<Events, ArrayList<String>> result(Events type, String affectedState) {
        HashMap<Events, ArrayList<String>> outputMap = new HashMap<>();
        ArrayList<String> array = new ArrayList<>();
        Map.Entry<Events, ArrayList<String>> result = Map.entry(type, array);

        switch (type) {
            case WAR -> {
                array.add(affectedState + " is at war");
                array.add("BREAKING NEWS: It has just been announced that " + affectedState + " is at war");
                array.add("CONFLICT ESCALATION: Military operations have begun in " + affectedState);
                array.add("CRISIS: War tensions in " + affectedState + " are disrupting daily life");
                array.add("GLOBAL ATTENTION: " + affectedState + " has entered a state of armed conflict");
                outputMap.put(type, array);
            }

            case OIL_BOOM -> {
                array.add(affectedState + " is experiencing an oil boom");
                array.add("ECONOMIC SURGE: Oil revenues in " + affectedState + " have skyrocketed");
                array.add("EXPORT GROWTH: " + affectedState + " is benefiting from high oil demand");
                array.add("ENERGY WINDFALL: Oil sector profits in " + affectedState + " are at record levels");
                array.add("MARKET EXPANSION: Oil production in " + affectedState + " has surged");
                outputMap.put(type, array);
            }

            case OIL_CRASH -> {
                array.add(affectedState + " is facing an oil market crash");
                array.add("MARKET SHOCK: Oil prices linked to " + affectedState + " have collapsed");
                array.add("REVENUE DROP: Oil income in " + affectedState + " has sharply declined");
                array.add("ECONOMIC STRAIN: Falling oil demand is hurting " + affectedState);
                array.add("ENERGY CRISIS: " + affectedState + " is struggling with low oil prices");
                outputMap.put(type, array);
            }

            case PANDEMIC -> {
                array.add(affectedState + " is dealing with a pandemic outbreak");
                array.add("HEALTH ALERT: A pandemic has spread rapidly across " + affectedState);
                array.add("LOCKDOWN: Authorities in " + affectedState + " are enforcing restrictions");
                array.add("CRISIS RESPONSE: Healthcare systems in " + affectedState + " are under pressure");
                array.add("GLOBAL CONCERN: The pandemic in " + affectedState + " is raising alarms");
                outputMap.put(type, array);
            }

            case DEBT_CRISIS -> {
                array.add(affectedState + " is in a debt crisis");
                array.add("FINANCIAL WARNING: " + affectedState + " is struggling to service its debt");
                array.add("DEFAULT RISK: Investors fear " + affectedState + " may default");
                array.add("BUDGET STRAIN: Debt obligations are overwhelming " + affectedState);
                array.add("ECONOMIC INSTABILITY: Rising debt is destabilizing " + affectedState);
                outputMap.put(type, array);
            }

            case FOREX_BOOM -> {
                array.add(affectedState + " is experiencing a forex boom");
                array.add("CURRENCY SURGE: Foreign exchange inflows into " + affectedState + " have increased sharply");
                array.add("STRONG RESERVES: Forex reserves in " + affectedState + " are growing rapidly");
                array.add("INVESTMENT INFLOW: Foreign investors are pouring capital into " + affectedState);
                array.add("ECONOMIC BOOST: Strong forex performance is benefiting " + affectedState);
                outputMap.put(type, array);
            }

            case INSURGENCY -> {
                array.add(affectedState + " is facing an insurgency");
                array.add("SECURITY ALERT: Armed insurgent activity has intensified in " + affectedState);
                array.add("REGIONAL UNREST: Violence is spreading across parts of " + affectedState);
                array.add("GOVERNMENT RESPONSE: Authorities in " + affectedState + " are deploying forces");
                array.add("CRISIS: Ongoing insurgency is disrupting stability in " + affectedState);
                outputMap.put(type, array);
            }
        }

        for (Map.Entry<Events, ArrayList<String>> ev: outputMap.entrySet()) {
            if (ev.getKey().equals(type)) {
                result = ev;
                break;
            }
        }

        return result;
    }
}
