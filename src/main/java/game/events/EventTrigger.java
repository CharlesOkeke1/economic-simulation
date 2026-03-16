package game.events;

import java.util.*;

import game.economies.*;
import utils.EnumToString;

public class EventTrigger {

    public static String eventDisp = "";
    public static String eventType = "";

    // event fires when risk crosses this
    public static double eventThreshold = 0.5;

    public static void trigger(Map<String, StateEconomy> states,
                               FederalEconomy federal,
                               String stateName,
                               int count) {

        StateEconomy state = states.get(stateName);

        /* ===============================
           PHASE 1 — BASE PRESSURE BUILDUP
           =============================== */

        // negative pressure (~4–5 months)
        state.getEventRisk().increaseRisk(Events.DEBT_CRISIS, 0.04);
        state.getEventRisk().increaseRisk(Events.OIL_CRASH, 0.04);
        state.getEventRisk().increaseRisk(Events.INSURGENCY, 0.035);

        // positive pressure (~6–7 months)
        state.getEventRisk().increaseRisk(Events.OIL_BOOM, 0.025);
        state.getEventRisk().increaseRisk(Events.FOREX_BOOM, 0.025);

        /* ===============================
           PHASE 2 — ECONOMIC MODIFIERS
           =============================== */

        if (count > 1) {

            // Debt crisis
            if (state.debtToGdpRatio > 0.35) {
                state.getEventRisk().increaseRisk(Events.DEBT_CRISIS, 0.12);
            }

            // Oil boom
            if (state.gdpGrowth > 0.04) {
                state.getEventRisk().increaseRisk(Events.OIL_BOOM, 0.08);
            }

            // Oil crash
            if (state.gdpGrowth < -0.03) {
                state.getEventRisk().increaseRisk(Events.OIL_CRASH, 0.10);
            }

            // Forex boom
            if (state.stability > 0.75 && state.gdpGrowth > 0.03) {
                state.getEventRisk().increaseRisk(Events.FOREX_BOOM, 0.09);
            }

            // Insurgency
            if (state.infrastructure < 40 && state.stability < 0.45) {
                state.getEventRisk().increaseRisk(Events.INSURGENCY, 0.10);
            }

            // War (rare)
            if (state.stability < 0.35 && Math.random() > 0.8) {
                state.getEventRisk().increaseRisk(Events.WAR, 0.12);
            }

            // Pandemic (rare)
            if (state.stability < 0.5 && Math.random() > 0.9) {
                state.getEventRisk().increaseRisk(Events.PANDEMIC, 0.10);
            }
        }

        /* ===============================
           PHASE 3 — RANDOM DIVERSIFICATION
           =============================== */

        for (Events event : Events.values()) {
            state.getEventRisk().increaseRisk(event, Math.random() * 0.015);
        }

        /* ===============================
           PHASE 4 — FIND HIGHEST EVENT
           =============================== */

        Events chosenEvent = null;
        double highestRisk = 0;

        for (Events event : Events.values()) {

            double risk = state.getEventRisk().getRisk(event);

            if (risk > eventThreshold && risk > highestRisk) {
                highestRisk = risk;
                chosenEvent = event;
            }
        }

        /* ===============================
           PHASE 5 — APPLY EVENT
           =============================== */

        if (chosenEvent != null) {

            ApplyEvent.apply(states, federal, chosenEvent, stateName);

            eventType = EnumToString.convert(chosenEvent, "_");
            eventDisp = stateName + " is experiencing a "
                    + EnumToString.convert(chosenEvent, "_");

            // cooldown for triggered event
            state.getEventRisk().setRisk(chosenEvent, -0.25);

            // soften other risks slightly
            for (Events event : Events.values()) {

                if (event != chosenEvent) {

                    double risk = state.getEventRisk().getRisk(event);

                    if (risk > eventThreshold) {
                        state.getEventRisk().setRisk(event, risk * 0.75);
                    }
                }
            }
        }

        /* ===============================
           PHASE 6 — NATURAL DECAY
           =============================== */

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
}