package li.doerf.sinematograf.cinema.service;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import li.doerf.sinematograf.cinema.entity.CinemaEntity;
import li.doerf.sinematograf.cinema.event.CinemaCreated;
import li.doerf.sinematograf.cinema.event.CinemaUpdated;
import li.doerf.sinematograf.cinema.resources.dtos.CinemaDto;

public interface ICinemaService {

    public List<CinemaEntity> getAll();

    public PanacheEntityBase createCinema(CinemaCreated event);

    public PanacheEntityBase updateCinema(CinemaUpdated event);

    public Boolean exists(CinemaDto cinema);

    public Boolean exists(String id);

}
