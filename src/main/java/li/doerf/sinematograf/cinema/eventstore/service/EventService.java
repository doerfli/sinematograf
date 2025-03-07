package li.doerf.sinematograf.cinema.eventstore.service;

import java.time.Instant;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import li.doerf.sinematograf.cinema.eventstore.entity.EventEntity;
import li.doerf.sinematograf.cinema.eventstore.events.BaseEvent;

@ApplicationScoped
public class EventService implements IEventService {

    private Emitter<QueueEvent> eventEmitter; 
    private ObjectMapper objectMapper;

    public EventService(@Channel("cinema-events") Emitter<QueueEvent> eventEmitter) {
        this.eventEmitter = eventEmitter;
        objectMapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        objectMapper.registerModule(module);
    }
    
    @Override
    public PanacheEntityBase persist(BaseEvent event) throws JsonProcessingException {
        try {
            var entity = storeEvent(new EventEntity(
                null,
                event.getAggregateId(),
                event.getAggregateType(),
                event.getClass().getSimpleName(),
                objectMapper.writeValueAsString(event),
                null,
                Instant.now()
            ));
            
            eventEmitter.send(new QueueEvent(event.getClass().getName(), event));
            return entity;
        } finally {
            
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public PanacheEntityBase storeEvent(EventEntity entity) {
        entity.persist();
        Log.info("EventEntity persisted: %s".formatted(entity));
        return entity;
    }

}
