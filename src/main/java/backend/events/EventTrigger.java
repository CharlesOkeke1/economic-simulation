package events;

import java.util.*;

import economies.StateEconomy;
import economies.FederalEconomy;
import gui.AppMain;
import utils.EnumToString;

public class EventTrigger {

    private static String eventDisp = "";
    private static String eventType = "";
    private static String playerEvent = "";
    private static String playerEventType = "";

    // event fires when risk crosses this
    public static double eventThreshold = 0.5;
    public static boolean isEvent = false;

    public static void trigger(String stateName,int count) {
        HashMap<String, StateEconomy> states = AppMain.getStateMap();
        FederalEconomy federal = AppMain.getFederal();
        StateEconomy state = states.get(stateName);
        Random ran = new Random();
        /* BASE PRESSURE BUILDUP */

        // negative pressure (~4–5 months)
        state.getEventRisk().increaseRisk(Events.DEBT_CRISIS, 0.04);
        state.getEventRisk().increaseRisk(Events.OIL_CRASH, 0.04);
        state.getEventRisk().increaseRisk(Events.INSURGENCY, 0.035);

        // positive pressure (~6–7 months)
        state.getEventRisk().increaseRisk(Events.OIL_BOOM, 0.025);
        state.getEventRisk().increaseRisk(Events.FOREX_BOOM, 0.025);

        /* ECONOMIC MODIFIERS */
        if (count > 1) {

            // Debt crisis
            if (state.getDebtToGdpRatio() > 0.35 || state.getGdpGrowth() < -0.02) {
                state.getEventRisk().increaseRisk(Events.DEBT_CRISIS, 0.12);
            }

            // Oil boom
            if (state.getGdpGrowth() > 0.04) {
                state.getEventRisk().increaseRisk(Events.OIL_BOOM, 0.08);
            }

            // Oil crash
            if (state.getGdpGrowth() < -0.025) {
                state.getEventRisk().increaseRisk(Events.OIL_CRASH, 0.10);
            }

            // Forex boom
            if (state.getStability() > 70 && state.getGdpGrowth() > 0.03) {
                state.getEventRisk().increaseRisk(Events.FOREX_BOOM, 0.09);
            }

            // Insurgency
            if (state.getInfrastructure() < 40 && state.getStability() < 45) {
                state.getEventRisk().increaseRisk(Events.INSURGENCY, 0.10);
            }

            // War (rare)
            if (Math.random() > 0.925) {
                state.getEventRisk().increaseRisk(Events.WAR, 0.12);
            }

            // Pandemic (rare)
            if (state.getStability() < 45 && Math.random() > 0.825) {
                state.getEventRisk().increaseRisk(Events.PANDEMIC, 0.10);
            }
        }

        /* RANDOM DIVERSIFICATION */
        for (Events event : Events.values()) {
            state.getEventRisk().increaseRisk(event, Math.random() * 0.015);
        }

        /* FIND HIGHEST EVENT */
        Events chosenEvent = null;
        double highestRisk = 0;

        for (Events event : Events.values()) {

            double risk = state.getEventRisk().getRisk(event);

            if (risk > eventThreshold && risk > highestRisk) {
                highestRisk = risk;
                chosenEvent = event;
            }
        }

        /* APPLY EVENT */
        if (chosenEvent != null) {

            ApplyEvent.apply(chosenEvent, stateName);

            Map.Entry<Events, ArrayList<String>> result = EventOutput.result(chosenEvent, stateName);
            ArrayList<String> eventList = result.getValue();


            eventType = EnumToString.convert(chosenEvent, "_");
            eventDisp = eventList.get(ran.nextInt(5));

            // cooldown for triggered event
            state.getEventRisk().setRisk(chosenEvent, -0.25);
            state.setEventName(eventType);

            String pState = AppMain.getConfig().getChosenState();
            if (state.getName().equals(pState)) {
                Map.Entry<Events, ArrayList<String>> resultPlayer = EventOutput.result(chosenEvent, pState);
                ArrayList<String> eventListPlayer = result.getValue();
                playerEventType = EnumToString.convert(chosenEvent, "_");
                playerEvent = eventListPlayer.get(ran.nextInt(5));
            }

            // soften other risks slightly
            for (Events event : Events.values()) {
                if (event != chosenEvent) {
                    double risk = state.getEventRisk().getRisk(event);
                    if (risk > eventThreshold) {
                        state.getEventRisk().setRisk(event, risk * 0.75);
                    }
                }
            }
        } else {
            eventType = "No Event (Others)";
            eventDisp = "There's no event currently happening";

            playerEventType = "No Event (Yours)";
            playerEvent = "There's no event currently happening";
        }

        /* NATURAL DECAY */
        for (Events event : Events.values()) {
            state.getEventRisk().increaseRisk(event, -0.02);
        }
    }

    public static String getEventDisp() {
        return eventDisp;
    }

    public static String getEventType() {
        return eventType;
    }

    public static String getPlayerEventDisp() {
        return playerEvent;
    }

    public static String getPlayerEventType() {
        return playerEventType;
    }
}