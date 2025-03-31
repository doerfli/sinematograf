package li.doerf.sinematograf.cinema.receiver;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import li.doerf.sinematograf.cinema.event.CinemaCreated;
import li.doerf.sinematograf.cinema.event.CinemaUpdated;
import li.doerf.sinematograf.cinema.service.ICinemaService;
import li.doerf.sinematograf.eventstore.Event;

@ApplicationScoped
public class EventsReceiver {

    private ICinemaService cinemaService;
    private ObjectMapper objectMapper;

    public EventsReceiver(ICinemaService cinemaService) {
        this.cinemaService = cinemaService;
        this.objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
    }

    @Incoming("cinema-events-rcv")
    @Blocking
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void process(JsonObject obj) throws InterruptedException, ClassNotFoundException, JsonProcessingException {
        Log.info(obj.toString());
        try {
            var eventStr = obj.getString("eventData");
            Event evt = obj.mapTo(Event.class);
            var realEventClass = Class.forName(evt.eventType);
            Log.info(eventStr);
            var realEvent = objectMapper.readValue(eventStr, realEventClass);
            Log.info(realEvent);
            process(realEvent);
        } catch (Exception e) {
            Log.error("caught unexpected exception while processing incoming event", e);
        }
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
