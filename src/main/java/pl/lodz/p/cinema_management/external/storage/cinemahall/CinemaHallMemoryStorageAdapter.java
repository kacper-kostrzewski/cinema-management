package pl.lodz.p.cinema_management.external.storage.cinemahall;

import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.api.cinemahall.CinemaHallDtoMapper;
import pl.lodz.p.cinema_management.domain.cinemahall.CinemaHall;
import pl.lodz.p.cinema_management.domain.cinemahall.CinemaHallRepository;

import java.util.*;

@Repository
public class CinemaHallMemoryStorageAdapter implements CinemaHallRepository {
    private final CinemaHallDtoMapper cinemaHallDtoMapper;
    private final Map<Integer, CinemaHall> cinemaHalls = new HashMap<>();
    private static Integer nextId = 0;

    public CinemaHallMemoryStorageAdapter(CinemaHallDtoMapper cinemaHallDtoMapper) {
        this.cinemaHallDtoMapper = cinemaHallDtoMapper;
    }

    @Override
    public CinemaHall save(CinemaHall cinemaHall) {
        nextId++;
        cinemaHalls.put(nextId, cinemaHall);
        cinemaHall.setId(nextId);
        return cinemaHall;
    }

    @Override
    public Optional<CinemaHall> findById(Integer id) {
        return Optional.ofNullable(cinemaHalls.get(id));
    }

    @Override
    public List<CinemaHall> findAll() {
        return new ArrayList<>(cinemaHalls.values());
    }

    @Override
    public CinemaHall update(Integer id, CinemaHall cinemaHall) {
        cinemaHall.setId(id);
        cinemaHalls.put(id, cinemaHall);
        return cinemaHall;
    }

    @Override
    public void delete(Integer id) {
        cinemaHalls.remove(id);
    }
}
