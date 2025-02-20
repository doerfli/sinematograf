package li.doerf.sinematograf.eventstore.service;

import java.time.Instant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import li.doerf.sinematograf.eventstore.entity.EventEntity;
import li.doerf.sinematograf.eventstore.events.Event;

@ApplicationScoped
public class EventService implements IEventService {

    @Override
    public Uni persist(Event event) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            var entity = new EventEntity(
                null,
                event.getAggregateId(),
                event.getAggregateType(),
                event.getClass().getSimpleName(),
                objectMapper.writeValueAsString(event),
                null,
                Instant.now()
            );
            // 
            return Panache.withTransaction(entity::persist);
        } finally {
            Log.info("Event persisted: %s".formatted(event));
        }
    }

}
