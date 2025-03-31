package li.doerf.sinematograf.cinema.eventstore.events;

import java.time.Instant;

public class BaseEvent {
    
    private String aggregateId;
    private String aggregateType;
    private Instant eventTimestamp;

    public BaseEvent(String aggregateId, String aggregateType) {
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.eventTimestamp = Instant.now();
    }

    public BaseEvent(String aggregateId, String aggregateType, Instant eventTimestamp) {
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.eventTimestamp = eventTimestamp;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public Instant getEventTimestamp() {
        return eventTimestamp;
    }

}
