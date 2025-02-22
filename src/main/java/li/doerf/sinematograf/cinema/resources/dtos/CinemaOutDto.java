package li.doerf.sinematograf.cinema.resources.dtos;

import li.doerf.sinematograf.cinema.entity.CinemaEntity;

public record CinemaOutDto( 
    String id,
    String name,
    String street,
    String zip,
    String city) { 

    public static CinemaOutDto from(CinemaEntity entity) {
        return new CinemaOutDto(
            entity.getId(),
            entity.getName(),
            entity.getStreet(),
            entity.getZip(),
            entity.getCity()
        );
    }
};
