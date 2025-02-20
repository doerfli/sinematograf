package li.doerf.sinematograf.cinema.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity(name = "cinemas")
@Cacheable
public class CinemaEntity extends PanacheEntityBase {
    
    @Id
    @SequenceGenerator(name="pk_sequence",sequenceName="cinemas_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    public Long id;
    @Column(name = "name")
    public String name;
    @Column(name = "street")
    public String street;
    @Column(name = "zip")
    public String zip;
    @Column(name = "city")
    public String city;
    
    public CinemaEntity() {
    }

    public CinemaEntity(
        Long id,
        String name,
        String street,
        String zip,
        String city
    ) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.zip = zip;
        this.city = city;
    }


}
