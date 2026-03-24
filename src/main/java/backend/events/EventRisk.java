package events;
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