package events;

public class EventDispObject {

    private String eventDisplay;
    private String eventType;

    // Constructor
    public EventDispObject(String eventType, String eventDisplay) {
        this.eventDisplay = eventDisplay;
        this.eventType = eventType;
    }

    // Getters
    public String getEventDisplay() {
        return eventDisplay;
    }

    public String getEventType() {
        return eventType;
    }

    // Setters
    public void setEventDisplay(String eventDisplay) {
        this.eventDisplay = eventDisplay;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}