package li.doerf.sinematograf.cinema.eventstore.events;

public class Event {
    
    private String aggregateId;
    private String aggregateType;

    public Event(String aggregateId, String aggregateType) {
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public String getAggregateType() {
        return aggregateType;
    }

}
