package pl.lodz.p.cinema_management.cinemahall.infrastructure.storage.cinemahall;

import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall;

import java.util.List;
import java.util.Optional;

@Repository
public interface CinemaHallRepository {

    CinemaHall save(CinemaHall cinemaHall);

    void update(CinemaHall cinemaHall);

    void remove(Integer id);

    Optional<CinemaHall> findByName(String name);

    Optional<CinemaHall> findById(Integer id);

    List<CinemaHall> findAll();

}
