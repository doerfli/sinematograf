package li.doerf.sinematograf.eventstore;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EventStoreReceiver {

    
    public EventStoreReceiver() {
    }

    @Incoming("cinema-eventstore-rcv")
    @Blocking
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void process(JsonObject obj) throws InterruptedException, ClassNotFoundException {
        var event = obj.mapTo(Event.class);
        Log.info(event);
        storeEvent(event);
    }


    //     @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void storeEvent(Event event) {
        EventEntity entity = event.toEntity();
        entity.persist();
        Log.debug("EventEntity persisted: %s".formatted(entity));
    }
    
}
