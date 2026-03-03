package game.events;
import java.util.*;

import game.data.Constants;
import game.economies.*;
import utils.EnumToString;
import utils.MyUtils;

public class EventTrigger {
    public static double eventThreshold = 0.5;
    //IF EVENT RISK IS ABOVE THRESHOLD, APPLY ITS CONSEQUENCES
    /*This model iterates through each event for every state and carries out the computation based on 3 cases*/
    //Case 1: If only one is above the threshold, carry out that.
    //Case 2: If more than one is above the threshold, take the highest and apply a computation that drops the rest below threshold but still keeps their momentum
    //Case 3: If no events risk is above threshold then only adjust risks and trigger nothing.

    public static void trigger(Map<String, StateEconomy> states, FederalEconomy federal, String stateName, int count) {
        StateEconomy affectedState = states.get(stateName);
        /*===== PHASE 1 - COMPUTE RISKS =====*/
        if (count > 1) {
            /*DEBT CRISIS*/
            //EDITABLE BECAUSE PLAYER DOESNT HAVE MEMORY YET.
            if ((affectedState.debtToGdpRatio > 0.3) && (affectedState.debt > affectedState.getMemory().getLastDebt())) {
                affectedState.getEventRisk().increaseRisk(
                    Events.DEBT_CRISIS,
                    0.2 * affectedState.getAttributes().getTaxSensitivity()
                );
            }

            /*OIL BOOM*/
            if ((affectedState.gdpGrowth > 0.03)  && (affectedState.realGdp > affectedState.getMemory().getLastRealGdp())) {
                affectedState.getEventRisk().increaseRisk(
                    Events.OIL_BOOM,
                    0.18 * affectedState.getAttributes().getInfraEfficiency()
                );
            }

            /*OIL CRASH*/      
            if ((affectedState.gdpGrowth < -0.03)  && (affectedState.realGdp < affectedState.getMemory().getLastRealGdp())) {
                affectedState.getEventRisk().increaseRisk(
                    Events.OIL_CRASH,
                    0.24 * affectedState.getAttributes().getPopulationElasticity()
                );
            }

            /*WAR*/
            if ((Math.random() > 0.5) && (affectedState.stability < affectedState.getMemory().getLastStability())) {
                affectedState.getEventRisk().increaseRisk(
                    Events.WAR,
                    0.27 * (1 - affectedState.getAttributes().getSecuritySensitivity())
                );
            }

            /*PANDEMIC*/
            if ((Math.random() > 0.5) && (affectedState.stability < 0.4)) { 
                affectedState.getEventRisk().increaseRisk(
                    Events.PANDEMIC,
                    0.2 * affectedState.getAttributes().getPopulationElasticity()
                );
            }

            /*FOREX BOOM*/
            if (affectedState.stability > 0.7 && affectedState.gdpGrowth > 0.05) {
                affectedState.getEventRisk().increaseRisk(
                    Events.FOREX_BOOM,
                    0.24 * affectedState.getAttributes().getInfraEfficiency()
                );
            }

            /*INSURGENCY*/
            if ((Math.random() > 0.85) && (((affectedState.stability
            < affectedState.getMemory().getLastStability())) && 
            (affectedState.infrastructure < 40))) {
                affectedState.getEventRisk().increaseRisk(
                    Events.INSURGENCY,
                    0.2 * affectedState.getAttributes().getCorruptionFactor() * 4
                );
            }
        }
    
        /* ===== PHASE 2 - DECAY + COOLDOWN TICK ===== */
        for (Events event : Events.values()) {

            // Apply mild decay
            double risk = affectedState.getEventRisk().getRisk(event);
            risk -= 0.02;
            if (risk < 0) risk = 0;
            affectedState.getEventRisk().setRisk(event, risk);

            // Reduce cooldown if active
            int cd = affectedState.getEventRisk().getCooldown(event);
            if (cd > 0) {
                affectedState.getEventRisk().setCooldown(event, cd - 1);
            }
        }

        /* ===== PHASE 3 - PROBABILITY ROLL ===== */
        Events triggeredEvent = null;
        double highestRisk = 0;

        for (Events event : Events.values()) {

            // Skip if on cooldown
            if (affectedState.getEventRisk().getCooldown(event) > 0)
                continue;

            double risk = affectedState.getEventRisk().getRisk(event);

            // Convert risk to monthly probability
            double monthlyChance = risk * 0.25;  // tune this multiplier

            if (Math.random() < monthlyChance) {

                // If multiple succeed, pick highest risk
                if (risk > highestRisk) {
                    highestRisk = risk;
                    triggeredEvent = event;
                }
            }
        }

        /* ===== PHASE 4 - APPLY EVENT ===== */
        if (triggeredEvent != null) {

            ApplyEvent.apply(states, federal, triggeredEvent, stateName);

            MyUtils.SteppedPrinting(
                "===== " + stateName + " is experiencing a "
                + EnumToString.convert(triggeredEvent, "_") + ". =====",
                Constants.REPORT_DELAY_TIME
            );

            // Reset risk
            affectedState.getEventRisk().setRisk(triggeredEvent, 0);

            // Apply cooldown (6 months example)
            affectedState.getEventRisk().setCooldown(triggeredEvent, 6);
        }

        /*===== PHASE 4 - NATURAL DECAY =====*/
        for (Events event : Events.values()) {
            affectedState.getEventRisk().increaseRisk(event,  -0.03);
        }
    }
}


public static void trigger(Map<String, StateEconomy> states,
                           FederalEconomy federal,
                           String stateName,
                           int count) {

    StateEconomy affectedState = states.get(stateName);

    /* ===== PHASE 1 - ACCUMULATE RISK (KEEP YOUR EXISTING LOGIC HERE) ===== */
    if (count > 1) {
        // --- YOUR CURRENT RISK INCREMENTS STAY HERE ---
    }

    
}