package li.doerf.sinematograf.cinema.resources;

import java.util.List;
import java.util.UUID;

import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import li.doerf.sinematograf.cinema.event.CinemaCreated;
import li.doerf.sinematograf.cinema.event.CinemaUpdated;
import li.doerf.sinematograf.cinema.eventstore.service.IEventService;
import li.doerf.sinematograf.cinema.resources.dtos.CinemaDto;
import li.doerf.sinematograf.cinema.resources.dtos.CinemaOutDto;
import li.doerf.sinematograf.cinema.resources.dtos.ErrorOccuredDto;
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
    @Transactional
    public PanacheEntityBase create(CinemaDto cinema) throws Exception {
        // persist cinema
        Log.debug("Creating cinema: %s".formatted(cinema));

            if (cinemaService.exists(cinema)) {
                Log.debug("Cinema already exists: %s".formatted(cinema));
                throw new EntityExistsException("Cinema already exists");
            }

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

    @PUT
    @Path("/{id}")
    @Transactional
    public PanacheEntityBase update(@RestPath String id, CinemaDto cinema) throws Exception {
        // persist cinema
        Log.debug("Updating cinema: %s".formatted(cinema));

        if (!cinemaService.exists(id)) {
            Log.debug("Cinema does not exist: %s".formatted(id));
            throw new EntityExistsException("Cinema does not exist");
        }

        var event = new CinemaUpdated(
            id,
            "Cinema",
            cinema.name(),
            cinema.street(),
            cinema.zip(),
            cinema.city()
        );

        return eventService.persist(event);
    }

    @GET
    public List<CinemaOutDto> listAll() {
        return cinemaService.getAll().stream().map(e -> CinemaOutDto.from(e)).toList();
    }

    @ServerExceptionMapper(EntityExistsException.class)
    public RestResponse<ErrorOccuredDto> mapException(EntityExistsException x) {
        return RestResponse.status(Response.Status.CONFLICT, new ErrorOccuredDto(x.getMessage()));
    }

}