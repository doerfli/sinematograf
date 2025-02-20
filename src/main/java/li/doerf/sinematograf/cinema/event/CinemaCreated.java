package li.doerf.sinematograf.cinema.event;

import li.doerf.sinematograf.eventstore.events.Event;

public class CinemaCreated extends Event {

    private String name;
    private String street;
    private String zip;
    private String city;

    public CinemaCreated(
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
