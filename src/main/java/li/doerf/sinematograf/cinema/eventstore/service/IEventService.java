package li.doerf.sinematograf.cinema.eventstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import li.doerf.sinematograf.cinema.eventstore.events.BaseEvent;

public interface IEventService {

    void emit(BaseEvent event) throws JsonProcessingException;

}