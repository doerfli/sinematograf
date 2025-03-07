package li.doerf.sinematograf.cinema.eventstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import li.doerf.sinematograf.cinema.eventstore.events.BaseEvent;

public interface IEventService {

    PanacheEntityBase persist(BaseEvent event) throws JsonProcessingException;

}