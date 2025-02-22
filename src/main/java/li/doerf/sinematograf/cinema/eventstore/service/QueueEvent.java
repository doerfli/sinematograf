package li.doerf.sinematograf.cinema.eventstore.service;

import li.doerf.sinematograf.cinema.eventstore.events.Event;

public class QueueEvent {

    private String cls;
    private Event event;

    public QueueEvent(String cls, Event event) {
        this.cls = cls;
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public String getCls() {
        return cls;
    }

}
