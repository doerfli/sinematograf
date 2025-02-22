package li.doerf.sinematograf.cinema.eventstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import li.doerf.sinematograf.cinema.eventstore.events.Event;

public interface IEventService {

    Uni<PanacheEntityBase> persist(Event event) throws JsonProcessingException;

}