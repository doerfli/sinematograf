package li.doerf.sinematograf.cinema.receiver;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import li.doerf.sinematograf.cinema.event.CinemaCreated;
import li.doerf.sinematograf.cinema.event.CinemaUpdated;
import li.doerf.sinematograf.cinema.eventstore.service.QueueEvent;
import li.doerf.sinematograf.cinema.service.ICinemaService;

@ApplicationScoped
public class EventsReceiver {

    private ICinemaService cinemaService;

    public EventsReceiver(ICinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Incoming("cinema-events-receiver")
    @Blocking
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public PanacheEntityBase process(JsonObject obj) throws InterruptedException, ClassNotFoundException {
        // Log.info(obj.toString());
        // Log.info(obj.getClass());
        var eventJsonObj = obj.getJsonObject("event");
        QueueEvent evt = obj.mapTo(QueueEvent.class);
        var realEventClass = Class.forName(evt.getCls());
        var realEvent = eventJsonObj.mapTo(realEventClass);
        // Log.info(realEvent.getClass());
        return process(realEvent);
        // Log.info(event.getAggregateId());
        // Log.info(event.getAggregateType());
        // Log.info(event.getClass().getSimpleName());
    }

    private PanacheEntityBase process(Object event) {       
        // Log.info("Processing event: %s".formatted(event));
        if (event instanceof CinemaCreated) {
            return process((CinemaCreated) event);
        } else if (event instanceof CinemaUpdated) {
            return process((CinemaUpdated) event);
        }
        return null;
    }

    private PanacheEntityBase process(CinemaCreated event) {
        Log.debug("Processing CinemaCreated event: %s".formatted(event));
        return cinemaService.createCinema(event);
    }

    private PanacheEntityBase process(CinemaUpdated event) {
        Log.debug("Processing CinemaUpdated event: %s".formatted(event));
        return cinemaService.updateCinema(event);
    }
    
}
