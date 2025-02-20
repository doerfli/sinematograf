package li.doerf.sinematograf.eventstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.smallrye.mutiny.Uni;
import li.doerf.sinematograf.eventstore.events.Event;

public interface IEventService {

    Uni persist(Event event) throws JsonProcessingException;

}