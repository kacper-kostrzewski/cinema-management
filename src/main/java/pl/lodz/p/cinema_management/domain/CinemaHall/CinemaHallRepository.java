package pl.lodz.p.cinema_management.domain.CinemaHall;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CinemaHallRepository {
    CinemaHall save(CinemaHall cinemaHall);
    Optional<CinemaHall> findById(Integer id);
    List<CinemaHall> findAll();
    CinemaHall update(Integer id, CinemaHall cinemaHall);
    void delete(Integer id);
}
