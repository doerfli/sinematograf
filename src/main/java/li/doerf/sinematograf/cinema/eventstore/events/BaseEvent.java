package li.doerf.sinematograf.cinema.eventstore.events;

public class BaseEvent {
    
    private String aggregateId;
    private String aggregateType;

    public BaseEvent(String aggregateId, String aggregateType) {
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
