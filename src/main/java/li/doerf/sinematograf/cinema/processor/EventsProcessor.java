package li.doerf.sinematograf.cinema.processor;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.hibernate.reactive.mutiny.Mutiny;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import li.doerf.sinematograf.cinema.entity.CinemaEntity;
import li.doerf.sinematograf.cinema.event.CinemaCreated;
import li.doerf.sinematograf.eventstore.service.QueueEvent;

@ApplicationScoped
public class EventsProcessor {

    @Inject
    Mutiny.SessionFactory sessionFactory;

    @Incoming("cinema-events-processor")           
    @NonBlocking
    @WithSession
    // @WithTransaction         
    public Uni<PanacheEntityBase> process(JsonObject obj) throws InterruptedException, ClassNotFoundException {
        // Log.info(obj.toString());
        // Log.info(obj.getClass());
        var eventJsonObj = obj.getJsonObject("event");
        QueueEvent evt = obj.mapTo(QueueEvent.class);
        var realEventClass = Class.forName(evt.getCls());
        var realEvent = eventJsonObj.mapTo(realEventClass);
        Log.info(realEvent.getClass());
        return process(realEvent);
        // Log.info(event.getAggregateId());
        // Log.info(event.getAggregateType());
        // Log.info(event.getClass().getSimpleName());
    }

    private Uni<PanacheEntityBase> process(Object event) {
        Log.info("Processing event: %s".formatted(event));
        if (event instanceof CinemaCreated) {
            return process((CinemaCreated) event);
        }

        return Uni.createFrom().nullItem();
    }

    private Uni<PanacheEntityBase> process(CinemaCreated event) {
        Log.info("Processing CinemaCreated event: %s".formatted(event));
        CinemaEntity cinema = new CinemaEntity(
            null,
            event.getName(),
            event.getStreet(),
            event.getZip(),
            event.getCity()
        );

        return Panache.withTransaction(cinema::persist);
    }
    
}
