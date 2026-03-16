package game.events;

public class EventObject {
    private int coolDown;
    private Events eventName;

    public EventObject(int coolDown, Events eventName) {
        this.coolDown = coolDown;
        this.eventName = eventName;
    }

    public void setCoolDown(int cool) {
        this.coolDown = cool;
    }

    public int getCoolDown() {
        return this.coolDown;
    }

    public Events getEvent() {
        return this.eventName;
    }
}
