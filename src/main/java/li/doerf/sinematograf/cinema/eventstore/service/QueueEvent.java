package li.doerf.sinematograf.cinema.eventstore.service;

import li.doerf.sinematograf.cinema.eventstore.events.BaseEvent;

public class QueueEvent {

    private String cls;
    private BaseEvent event;

    public QueueEvent(String cls, BaseEvent event) {
        this.cls = cls;
        this.event = event;
    }

    public BaseEvent getEvent() {
        return event;
    }

    public String getCls() {
        return cls;
    }

}
