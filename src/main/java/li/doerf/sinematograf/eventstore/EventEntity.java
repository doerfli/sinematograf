package li.doerf.sinematograf.eventstore;

import java.time.Instant;

import org.hibernate.annotations.ColumnTransformer;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="event")
public class EventEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "aggregate_id")
    public String aggregateId;
    @Column(name = "aggregate_type")
    public String aggregateType;
    @Column(name = "event_type")
    public String eventType;
    @Column(name = "event_data", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    public String eventData;
    @Column(name = "event_metadata", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    public String eventMetadata;
    @Column(name = "event_timestamp")
    public Instant eventTimestamp;

    public EventEntity() {
    }

    public EventEntity(
        Long id,
        String aggregateId,
        String aggregateType,
        String eventType,
        String eventData,
        String eventMetadata,
        Instant eventTimestamp
    ) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.eventType = eventType;
        this.eventData = eventData;
        this.eventMetadata = eventMetadata;
        this.eventTimestamp = eventTimestamp;
    }

    @Override
    public String toString() {
        
        return "EventEntity{" +
            "id=" + id +
            ", aggregateId='" + aggregateId + '\'' +
            ", aggregateType='" + aggregateType + '\'' +
            ", eventType='" + eventType + '\'' +
            ", eventData='" + eventData + '\'' +
            ", eventMetadata='" + eventMetadata + '\'' +
            ", eventTimestamp=" + eventTimestamp +
            '}';
    }

}
