package li.doerf.sinematograf.cinema.entity;

import java.time.Instant;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "cinemas")
@Table(schema = "cinema")
@Cacheable
public class CinemaEntity extends BaseEntity {
    
    @Column(name = "name")
    private String name;
    @Column(name = "street")
    private String street;
    @Column(name = "zip")
    private String zip;
    @Column(name = "city")
    private String city;
    
    public CinemaEntity() {
    }

    public CinemaEntity(
        String id,
        Instant createdAt,
        Instant updatedAt,
        String name,
        String street,
        String zip,
        String city
    ) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.street = street;
        this.zip = zip;
        this.city = city;
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

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
