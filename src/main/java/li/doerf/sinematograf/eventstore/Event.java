package li.doerf.sinematograf.eventstore;

import java.time.Instant;

public class Event {

    public String aggregateId;
    public String aggregateType;
    public String eventType;
    public String eventData;
    public String eventMetadata;
    public Instant eventTimestamp;

    public Event(
        String aggregateId,
        String aggregateType,
        String eventType,
        String eventData,
        String eventMetadata,
        Instant eventTimestamp
    ) {
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.eventType = eventType;
        this.eventData = eventData;
        this.eventMetadata = eventMetadata;
        this.eventTimestamp = eventTimestamp;
    }

    @Override
    public String toString() {
        
        return "Event={" +
            ", aggregateId='" + aggregateId + '\'' +
            ", aggregateType='" + aggregateType + '\'' +
            ", eventType='" + eventType + '\'' +
            ", eventData='" + eventData + '\'' +
            ", eventMetadata='" + eventMetadata + '\'' +
            ", eventTimestamp=" + eventTimestamp +
            '}';
    }

    public EventEntity toEntity() {
        EventEntity eventEntity = new EventEntity();
        eventEntity.aggregateId = this.aggregateId;
        eventEntity.aggregateType = this.aggregateType;
        eventEntity.eventType = this.eventType;
        eventEntity.eventData = this.eventData;
        eventEntity.eventMetadata = this.eventMetadata;
        eventEntity.eventTimestamp = this.eventTimestamp;
        return eventEntity;
    }

}
