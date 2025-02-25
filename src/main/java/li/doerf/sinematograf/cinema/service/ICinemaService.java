package li.doerf.sinematograf.cinema.service;

import java.util.List;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import li.doerf.sinematograf.cinema.entity.CinemaEntity;
import li.doerf.sinematograf.cinema.event.CinemaCreated;
import li.doerf.sinematograf.cinema.event.CinemaUpdated;
import li.doerf.sinematograf.cinema.resources.dtos.CinemaDto;

public interface ICinemaService {

    public Uni<List<CinemaEntity>> getAll();

    public Uni<PanacheEntityBase> createCinema(CinemaCreated event);

    public Uni<PanacheEntityBase> updateCinema(CinemaUpdated event);

    public Uni<Boolean> exists(CinemaDto cinema);

    public Uni<Boolean> exists(String id);

}
