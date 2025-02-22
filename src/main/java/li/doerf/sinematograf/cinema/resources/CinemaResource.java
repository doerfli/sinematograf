package li.doerf.sinematograf.cinema.resources;

import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import li.doerf.sinematograf.cinema.event.CinemaCreated;
import li.doerf.sinematograf.cinema.eventstore.service.IEventService;
import li.doerf.sinematograf.cinema.resources.dtos.CinemaDto;
import li.doerf.sinematograf.cinema.resources.dtos.CinemaOutDto;
import li.doerf.sinematograf.cinema.service.ICinemaService;

@Path("/cinema")
// @ApplicationScoped
public class CinemaResource {

    private IEventService eventService;
    private ICinemaService cinemaService;

    public CinemaResource(IEventService eventService, ICinemaService cinemaService) {
        this.eventService = eventService;
        this.cinemaService = cinemaService;
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
        return cinemaService.getAll().onItem().transform(entities -> {
            return entities.stream().map(e -> CinemaOutDto.from(e)).toList();
        });
    }

}