package li.doerf.sinematograf.cinema.resources;

import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import li.doerf.sinematograf.cinema.entity.CinemaEntity;
import li.doerf.sinematograf.cinema.event.CinemaCreated;
import li.doerf.sinematograf.cinema.eventstore.service.IEventService;
import li.doerf.sinematograf.cinema.resources.dtos.CinemaDto;
import li.doerf.sinematograf.cinema.resources.dtos.CinemaOutDto;

@Path("/cinema")
// @ApplicationScoped
public class CinemaResource {

    private IEventService eventService;

    public CinemaResource(IEventService eventService) {
        this.eventService = eventService;
    }

    @POST
    public Uni<PanacheEntityBase> create(CinemaDto cinema) throws Exception {
        // persist cinema
        Log.debug("Creating cinema: {}".formatted(cinema));

        var event = new CinemaCreated(
            UUID.randomUUID().toString(),
            "Cinema",
            cinema.name(),
            cinema.street(),
            cinema.zip(),
            cinema.city()
        );

        return eventService.persist(event);
    }

    @GET
    public Uni<List<CinemaOutDto>> listAll() {
        return CinemaEntity.<CinemaEntity>listAll().onItem().transform(entities -> {
            return entities.stream().map(e -> 
            new CinemaOutDto(
                e.getId(),
                e.getName(),
                e.getStreet(),
                e.getZip(),
                e.getCity())).toList();
        });
    }

}