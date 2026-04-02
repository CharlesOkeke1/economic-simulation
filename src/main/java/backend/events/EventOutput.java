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
                array.add("EMERGENCY: Government in " + affectedState + " has mobilised forces nationwide");
                array.add("SECURITY WARNING: Borders around " + affectedState + " are under heavy surveillance");
                array.add("MILITARY ACTION: Strategic zones in " + affectedState + " are under attack");
                array.add("NATIONAL ALERT: Citizens in " + affectedState + " are urged to stay cautious");
                array.add("WAR UPDATE: Conflict intensity in " + affectedState + " continues to rise");
                outputMap.put(type, array);
            }

            case OIL_BOOM -> {
                array.add(affectedState + " is experiencing an oil boom");
                array.add("ECONOMIC SURGE: Oil revenues in " + affectedState + " have skyrocketed");
                array.add("EXPORT GROWTH: " + affectedState + " is benefiting from high oil demand");
                array.add("ENERGY WINDFALL: Oil sector profits in " + affectedState + " are at record levels");
                array.add("MARKET EXPANSION: Oil production in " + affectedState + " has surged");
                array.add("REVENUE SPIKE: Government earnings in " + affectedState + " are rapidly increasing");
                array.add("GLOBAL DEMAND: " + affectedState + " is exporting oil at unprecedented levels");
                array.add("INDUSTRY BOOST: Oil infrastructure in " + affectedState + " is expanding");
                array.add("TRADE ADVANTAGE: " + affectedState + " is gaining from favourable oil prices");
                array.add("ECONOMIC MOMENTUM: Strong oil performance is driving growth in " + affectedState);
                outputMap.put(type, array);
            }

            case OIL_CRASH -> {
                array.add(affectedState + " is facing an oil market crash");
                array.add("MARKET SHOCK: Oil prices linked to " + affectedState + " have collapsed");
                array.add("REVENUE DROP: Oil income in " + affectedState + " has sharply declined");
                array.add("ECONOMIC STRAIN: Falling oil demand is hurting " + affectedState);
                array.add("ENERGY CRISIS: " + affectedState + " is struggling with low oil prices");
                array.add("FISCAL PRESSURE: Budget deficits are rising in " + affectedState);
                array.add("EXPORT LOSS: Oil exports from " + affectedState + " have weakened significantly");
                array.add("INDUSTRY DECLINE: Oil production activity in " + affectedState + " is slowing");
                array.add("MARKET INSTABILITY: Volatility is impacting oil revenues in " + affectedState);
                array.add("ECONOMIC DOWNTURN: Oil dependency is exposing weaknesses in " + affectedState);
                outputMap.put(type, array);
            }

            case PANDEMIC -> {
                array.add(affectedState + " is dealing with a pandemic outbreak");
                array.add("HEALTH ALERT: A pandemic has spread rapidly across " + affectedState);
                array.add("LOCKDOWN: Authorities in " + affectedState + " are enforcing restrictions");
                array.add("CRISIS RESPONSE: Healthcare systems in " + affectedState + " are under pressure");
                array.add("GLOBAL CONCERN: The pandemic in " + affectedState + " is raising alarms");
                array.add("EMERGENCY MEASURES: Public movement in " + affectedState + " is being restricted");
                array.add("HOSPITAL STRAIN: Medical facilities in " + affectedState + " are overwhelmed");
                array.add("PUBLIC WARNING: Citizens in " + affectedState + " are advised to stay indoors");
                array.add("ECONOMIC IMPACT: Business activity in " + affectedState + " is slowing due to the outbreak");
                array.add("RECOVERY EFFORTS: Authorities in " + affectedState + " are working to contain the spread");
                outputMap.put(type, array);
            }

            case DEBT_CRISIS -> {
                array.add(affectedState + " is in a debt crisis");
                array.add("FINANCIAL WARNING: " + affectedState + " is struggling to service its debt");
                array.add("DEFAULT RISK: Investors fear " + affectedState + " may default");
                array.add("BUDGET STRAIN: Debt obligations are overwhelming " + affectedState);
                array.add("ECONOMIC INSTABILITY: Rising debt is destabilizing " + affectedState);
                array.add("CREDIT DOWNGRADE: Financial confidence in " + affectedState + " is declining");
                array.add("FISCAL EMERGENCY: Government spending in " + affectedState + " is under pressure");
                array.add("LOAN DEPENDENCY: " + affectedState + " is increasingly relying on borrowing");
                array.add("INVESTOR CONCERN: Markets are losing confidence in " + affectedState);
                array.add("AUSTERITY RISK: Spending cuts may be imposed in " + affectedState);
                outputMap.put(type, array);
            }

            case FOREX_BOOM -> {
                array.add(affectedState + " is experiencing a forex boom");
                array.add("CURRENCY SURGE: Foreign exchange inflows into " + affectedState + " have increased sharply");
                array.add("STRONG RESERVES: Forex reserves in " + affectedState + " are growing rapidly");
                array.add("INVESTMENT INFLOW: Foreign investors are pouring capital into " + affectedState);
                array.add("ECONOMIC BOOST: Strong forex performance is benefiting " + affectedState);
                array.add("CURRENCY STRENGTH: The exchange rate in " + affectedState + " is improving");
                array.add("TRADE ADVANTAGE: Imports are becoming cheaper for " + affectedState);
                array.add("MARKET CONFIDENCE: Global investors are optimistic about " + affectedState);
                array.add("RESERVE BUILDUP: Central reserves in " + affectedState + " are expanding");
                array.add("FINANCIAL STABILITY: Forex gains are stabilizing the economy of " + affectedState);
                outputMap.put(type, array);
            }

            case INSURGENCY -> {
                array.add(affectedState + " is facing an insurgency");
                array.add("SECURITY ALERT: Armed insurgent activity has intensified in " + affectedState);
                array.add("REGIONAL UNREST: Violence is spreading across parts of " + affectedState);
                array.add("GOVERNMENT RESPONSE: Authorities in " + affectedState + " are deploying forces");
                array.add("CRISIS: Ongoing insurgency is disrupting stability in " + affectedState);
                array.add("MILITARY DEPLOYMENT: Troops have been sent to conflict zones in " + affectedState);
                array.add("CIVILIAN IMPACT: Communities in " + affectedState + " are facing displacement");
                array.add("SECURITY BREAKDOWN: Law enforcement in " + affectedState + " is under pressure");
                array.add("ESCALATION: Insurgent groups are increasing operations in " + affectedState);
                array.add("NATIONAL CONCERN: Stability risks in " + affectedState + " are rising");
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
