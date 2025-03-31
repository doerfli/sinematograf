package li.doerf.sinematograf.cinema.eventstore.service;

import java.time.Instant;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.enterprise.context.ApplicationScoped;
import li.doerf.sinematograf.cinema.eventstore.events.BaseEvent;
import li.doerf.sinematograf.eventstore.Event;

@ApplicationScoped
public class EventService implements IEventService {

    private Emitter<Event> eventEmitter; 
    private ObjectMapper objectMapper;

    public EventService(@Channel("cinema-events-emit") Emitter<Event> eventEmitter) {
        this.eventEmitter = eventEmitter;
        objectMapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        objectMapper.registerModule(module);
    }
    
    @Override
    public void emit(BaseEvent event) throws JsonProcessingException {
        Event send = new Event(
            event.getAggregateId(),
            event.getAggregateType(),
            event.getClass().getName(),
            objectMapper.writeValueAsString(event),
            null,
            Instant.now()
        );
        
        eventEmitter.send(send);
    }

}
