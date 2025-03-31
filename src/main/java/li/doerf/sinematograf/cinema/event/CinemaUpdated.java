package li.doerf.sinematograf.cinema.event;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import li.doerf.sinematograf.cinema.eventstore.events.BaseEvent;

public class CinemaUpdated extends BaseEvent {

    private String name;
    private String street;
    private String zip;
    private String city;

    public CinemaUpdated(
        String aggregateId, 
        String aggregateType,
        String name,
        String street,
        String zip,
        String city
    ) {
        super(aggregateId, aggregateType);
        this.name = name;
        this.street = street;
        this.zip = zip;
        this.city = city;
    }

    @JsonCreator
    public CinemaUpdated(
        @JsonProperty("aggregateId") String aggregateId, 
        @JsonProperty("aggregateType") String aggregateType,
        @JsonProperty("eventTimestamp") Instant eventTimestamp,
        @JsonProperty("name") String name,
        @JsonProperty("street") String street,
        @JsonProperty("zip") String zip,
        @JsonProperty("city") String city
    ) {
        super(aggregateId, aggregateType, eventTimestamp);
        this.name = name;
        this.street = street;
        this.zip = zip;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }
    
}
