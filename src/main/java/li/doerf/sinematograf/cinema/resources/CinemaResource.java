package li.doerf.sinematograf.cinema.resources;

import java.util.UUID;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import li.doerf.sinematograf.cinema.event.CinemaCreated;
import li.doerf.sinematograf.cinema.eventstore.service.IEventService;
import li.doerf.sinematograf.cinema.resources.requests.CinemaDto;

@Path("/cinema")
// @ApplicationScoped
public class CinemaResource {

    private IEventService eventService;

    public CinemaResource(IEventService eventService) {
        this.eventService = eventService;
    }

    @POST
    public Uni create(CinemaDto cinema) throws Exception {
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

}