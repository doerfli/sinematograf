package li.doerf.sinematograf.cinema.service;

import java.time.Instant;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import li.doerf.sinematograf.cinema.entity.CinemaEntity;
import li.doerf.sinematograf.cinema.event.CinemaCreated;

@ApplicationScoped
public class CinemaService implements ICinemaService {
    
    public Uni<PanacheEntityBase> createCinema(CinemaCreated event) {
        CinemaEntity cinema = new CinemaEntity(
            event.getAggregateId(),
            Instant.now(),
            Instant.now(),
            event.getName(),
            event.getStreet(),
            event.getZip(),
            event.getCity()
        );

        return Panache.withTransaction(cinema::persist).onItem().invoke(() -> {
            Log.info("Cinema entity persisted: %s".formatted(cinema));
        });
    }

}
