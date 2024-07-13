package pl.lodz.p.cinema_management.external.storage.cinemahall;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCinemaHallRepository extends JpaRepository<CinemaHallEntity, Integer> {
}
