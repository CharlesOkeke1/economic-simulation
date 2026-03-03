package game.events;


/*
These are the economic events i have - add 5 more .
Also im thinking, my current design for events are an 
enum and then a trigger class will trigger events at 
the end of each month. How Im thinking is each month, 
based on changing values, each events risk of happening
to a state will either increase or decrease. When a
threshold is reached, the event happens and risk resets
to 0 so that its not random. Although for spic i would 
like to introduce randomness but not too much. So we have 
a trigger file that operates just like policy engine that 
shows what each event's implication and computes risks of
each happening has and returns type event. So from my
current model, the risks will be their own object class 
that is attached to each state economy and then the after
each month, the event trigger or rather calculation class
runs, and then risks add up then once each risk culminates, 
event triggers. Only one event per month too, so maybe if 
more than one events risk is above the threshold, the highest 
one is executed and then the others above threshold are now 
halved or a permutation occurs to reduce them a bit below 
threshold so that they have a higher chance if happening 
subsequently
*/

import java.util.EnumMap;
import java.util.Map;

public class EventRisk {

    private Map<Events, Double> risks;

    public EventRisk() {
        risks = new EnumMap<>(Events.class);

        // initialize all risks to 0
        for (Events type : Events.values()) {
            risks.put(type, 0.0);
        }
    }

    // GET
    public double getRisk(Events type) {
        return risks.get(type);
    }

    // SET
    public void setRisk(Events type, double value) {
        risks.put(type, value);
    }

    // ADD
    public void increaseRisk(Events type, double value) {
        risks.put(type, risks.get(type) + value);
    }

    // RESET ONE
    public void resetRisk(Events type) {
        risks.put(type, 0.0);
    }

    // RESET ALL
    public void resetAll() {
        for (Events type : Events.values()) {
            risks.put(type, 0.0);
        }
    }

    //GET COOLDOWN
    public int getCooldown(Events event) {
        return risks.
    }

    // Get highest risk event
    public Events getHighestRiskEvent() {
        Events highest = null;
        double max = -1;

        for (Map.Entry<Events, Double> entry : risks.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                highest = entry.getKey();
            }
        }

        return highest;
    }
}