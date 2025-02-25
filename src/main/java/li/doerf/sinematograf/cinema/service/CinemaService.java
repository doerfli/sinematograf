package li.doerf.sinematograf.cinema.service;

import java.util.List;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import li.doerf.sinematograf.cinema.entity.CinemaEntity;
import li.doerf.sinematograf.cinema.event.CinemaCreated;
import li.doerf.sinematograf.cinema.event.CinemaUpdated;
import li.doerf.sinematograf.cinema.resources.dtos.CinemaDto;

@ApplicationScoped
public class CinemaService implements ICinemaService {
    
    public Uni<PanacheEntityBase> createCinema(CinemaCreated event) {
        CinemaEntity cinema = new CinemaEntity(
            event.getAggregateId(),
            event.getEventTimestamp(),
            event.getEventTimestamp(),
            event.getName(),
            event.getStreet(),
            event.getZip(),
            event.getCity()
        );

        return Panache.withTransaction(cinema::persist).onItem().invoke(() -> {
            Log.info("Cinema entity persisted: %s".formatted(cinema));
        });
    }

    public Uni<PanacheEntityBase> updateCinema(CinemaUpdated event) {
        return CinemaEntity.findById(event.getAggregateId())
            .onItem().ifNotNull().transformToUni(entity -> {
                if (entity == null) {
                    Log.error("Cinema entity not found: %s".formatted(event.getAggregateId()));
                    return Uni.createFrom().nullItem();
                }
                if (!( entity instanceof CinemaEntity)) {
                    Log.error("Entity is not a CinemaEntity: %s".formatted(entity));
                    return Uni.createFrom().nullItem();
                }
                CinemaEntity cinema = (CinemaEntity) entity;
                cinema.setName(event.getName());
                cinema.setStreet(event.getStreet());
                cinema.setZip(event.getZip());
                cinema.setCity(event.getCity());
                cinema.setUpdatedAt(event.getEventTimestamp());
                return Panache.withTransaction(cinema::persist).onItem().invoke(() -> {
                    Log.info("Cinema entity updated: %s".formatted(cinema));
                });
            });
    }

    public Uni<List<CinemaEntity>> getAll() {
        return CinemaEntity.<CinemaEntity>listAll();
    }

    public Uni<Boolean> exists(CinemaDto cinema) {
        return CinemaEntity.count("name = ?1 and street = ?2 and zip = ?3 and city = ?4",
            cinema.name(), cinema.street(), cinema.zip(), cinema.city())
            .onItem().transform(count -> count > 0);
    }

    public Uni<Boolean> exists(String id) {
        return CinemaEntity.count("id", id).onItem().transform(count -> count > 0);
    }

}
