package li.doerf.sinematograf.cinema.service;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import li.doerf.sinematograf.cinema.event.CinemaCreated;

public interface ICinemaService {

    public Uni<PanacheEntityBase> createCinema(CinemaCreated event);

}
