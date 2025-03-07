package li.doerf.sinematograf.cinema.service;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import li.doerf.sinematograf.cinema.entity.CinemaEntity;
import li.doerf.sinematograf.cinema.event.CinemaCreated;
import li.doerf.sinematograf.cinema.event.CinemaUpdated;
import li.doerf.sinematograf.cinema.resources.dtos.CinemaDto;

@ApplicationScoped
public class CinemaService implements ICinemaService {
    
    public PanacheEntityBase createCinema(CinemaCreated event) {
        CinemaEntity cinema = new CinemaEntity(
            event.getAggregateId(),
            event.getEventTimestamp(),
            event.getEventTimestamp(),
            event.getName(),
            event.getStreet(),
            event.getZip(),
            event.getCity()
        );

        cinema.persist();
        Log.info("Cinema entity persisted: %s".formatted(cinema));
        return cinema;
    }

    public PanacheEntityBase updateCinema(CinemaUpdated event) {
        CinemaEntity entity =  CinemaEntity.findById(event.getAggregateId());
        if (entity == null) {
            Log.error("Cinema entity not found: %s".formatted(event.getAggregateId()));
            return null;
        }
        if (!( entity instanceof CinemaEntity)) {
            Log.error("Entity is not a CinemaEntity: %s".formatted(entity));
            return null;
        }
        CinemaEntity cinema = (CinemaEntity) entity;
        cinema.setName(event.getName());
        cinema.setStreet(event.getStreet());
        cinema.setZip(event.getZip());
        cinema.setCity(event.getCity());
        cinema.setUpdatedAt(event.getEventTimestamp());
        cinema.persist();
        Log.info("Cinema entity updated: %s".formatted(cinema));
        return cinema;
    }

    public List<CinemaEntity> getAll() {
        return CinemaEntity.listAll();
    }

    public Boolean exists(CinemaDto cinema) {
        return CinemaEntity.count("name = ?1 and street = ?2 and zip = ?3 and city = ?4",
            cinema.name(), cinema.street(), cinema.zip(), cinema.city()) > 0;
    }

    public Boolean exists(String id) {
        return CinemaEntity.count("id", id) > 0;
    }

}
