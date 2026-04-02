package events;

import java.util.*;

import data.Constants;
import economies.StateEconomy;
import economies.FederalEconomy;
import gui.AppMain;
import utils.EnumToString;

public class EventTrigger {
    private static HashMap<String, EventDispObject> eventDispMap = new HashMap<>();

    // event fires when risk crosses this
    public static double eventThreshold = 0.5;
    public static boolean isEvent = false;

    public static void trigger() {
        HashMap<String, StateEconomy> states = AppMain.getStateMap();
        Random ran = new Random();
        /* BASE PRESSURE BUILDUP */

        for (StateEconomy s : states.values()) {

            // negative pressure (~4–5 months)
            s.getEventRisk().increaseRisk(Events.DEBT_CRISIS, 0.04);
            s.getEventRisk().increaseRisk(Events.OIL_CRASH, 0.04);
            s.getEventRisk().increaseRisk(Events.INSURGENCY, 0.035);

            // positive pressure (~6–7 months)
            s.getEventRisk().increaseRisk(Events.OIL_BOOM, 0.025);
            s.getEventRisk().increaseRisk(Events.FOREX_BOOM, 0.025);

            /* ECONOMIC MODIFIERS */
            if (AppMain.getSim().getCurrentMonth() > 1) {

                // Debt crisis
                if (s.getDebtToGdpRatio() > 0.35 && s.getGdpGrowth() < -0.015) {
                    s.getEventRisk().increaseRisk(Events.DEBT_CRISIS, 0.12);
                }

                // Oil boom
                if (s.getGdpGrowth() > 0.04) {
                    s.getEventRisk().increaseRisk(Events.OIL_BOOM, 0.08);
                }

                // Oil crash
                if (s.getGdpGrowth() < -0.025) {
                    s.getEventRisk().increaseRisk(Events.OIL_CRASH, 0.10);
                }

                // Forex boom
                if (s.getStability() > 70 && s.getGdpGrowth() > 0.03) {
                    s.getEventRisk().increaseRisk(Events.FOREX_BOOM, 0.09);
                }

                // Insurgency
                if (s.getInfrastructure() < 40 && s.getStability() < 45) {
                    s.getEventRisk().increaseRisk(Events.INSURGENCY, 0.10);
                }

                // War (rare)
                if (Math.random() > 0.925) {
                    s.getEventRisk().increaseRisk(Events.WAR, 0.15);
                }

                // Pandemic (rare)
                if (s.getStability() < 45 && Math.random() > 0.825) {
                    s.getEventRisk().increaseRisk(Events.PANDEMIC, 0.10);
                }
            }

            /* RANDOM DIVERSIFICATION */
            for (Events event : Events.values()) {
                s.getEventRisk().increaseRisk(event, Math.random() * 0.015);
            }

            /* FIND HIGHEST EVENT */
            Events chosenEvent = null;
            double highestRisk = 0;

            for (Events event : Events.values()) {

                double risk = s.getEventRisk().getRisk(event);

                if (risk > eventThreshold && risk > highestRisk) {
                    highestRisk = risk;
                    chosenEvent = event;
                }
            }

            /* APPLY EVENT */
            if (chosenEvent != null) {

                ApplyEvent.apply(chosenEvent, s.getName());
                Map.Entry<Events, ArrayList<String>> result = EventOutput.result(chosenEvent, s.getName());
                ArrayList<String> eventList = result.getValue();

                EventDispObject obj = new EventDispObject(EnumToString.convert(chosenEvent, "_"), eventList.get(ran.nextInt(eventList.size())));
                if (obj == null) {
                    obj = new EventDispObject("No Event", "No event currently");
                }
                eventDispMap.put(s.getName(), obj);

                if (!s.getName().equals(AppMain.getConfig().getChosenState())) System.out.println("PRINT -----:  " + EnumToString.convert(chosenEvent, "_") + ":  " + eventList.get(ran.nextInt(eventList.size())));
                else System.out.println("(YOUR STATE) PRINT -----:  " + EnumToString.convert(chosenEvent, "_") + ":  " + eventList.get(ran.nextInt(eventList.size())));



                // cooldown for triggered event
                s.getEventRisk().setRisk(chosenEvent, -0.25);
                s.setEventName(EnumToString.convert(chosenEvent, "_"));

                // soften other risks slightly
                for (Events event : Events.values()) {
                    if (event != chosenEvent) {
                        double risk = s.getEventRisk().getRisk(event);
                        if (risk > eventThreshold) {
                            s.getEventRisk().setRisk(event, risk * 0.75);
                        }
                    }
                }
            }
            /* NATURAL DECAY */
            for (Events event : Events.values()) {
                s.getEventRisk().increaseRisk(event, -0.02);
            }
        }

    }

    public static HashMap<String, EventDispObject> getEventDispMap() {
        return eventDispMap;
    }


}