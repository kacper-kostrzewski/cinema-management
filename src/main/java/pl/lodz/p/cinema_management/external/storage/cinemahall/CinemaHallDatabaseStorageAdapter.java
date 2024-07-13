package pl.lodz.p.cinema_management.external.storage.cinemahall;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.domain.cinemahall.CinemaHall;
import pl.lodz.p.cinema_management.domain.cinemahall.CinemaHallNotFoundException;
import pl.lodz.p.cinema_management.domain.cinemahall.CinemaHallRepository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CinemaHallDatabaseStorageAdapter implements CinemaHallRepository {

    private final JpaCinemaHallRepository jpaCinemaHallRepository;
    private final CinemaHallEntityMapper cinemaHallEntityMapper;

    @Override
    public CinemaHall save(CinemaHall cinemaHall) {
        CinemaHallEntity cinemaHallEntity = cinemaHallEntityMapper.toEntity(cinemaHall);
        return cinemaHallEntityMapper.toDomain(jpaCinemaHallRepository.save(cinemaHallEntity));
    }

    @Override
    public Optional<CinemaHall> findById(Integer id) {
        return jpaCinemaHallRepository.findById(id).map(cinemaHallEntityMapper::toDomain);
    }

    @Override
    public List<CinemaHall> findAll() {
        return jpaCinemaHallRepository.findAll(Sort.by(Sort.Direction.ASC,"id")).stream().map(cinemaHallEntityMapper::toDomain).toList();
    }

    @Override
    public void delete(Integer id) {
        jpaCinemaHallRepository.deleteById(id);
    }

    @Override
    public CinemaHall update(CinemaHall cinemaHall) {
        Optional<CinemaHallEntity> cinemaHallEntity = jpaCinemaHallRepository.findById(cinemaHall.getId());
        if (cinemaHallEntity.isPresent()) {
            CinemaHallEntity tempCinemaHallEntity = cinemaHallEntityMapper.toEntity(cinemaHall);
            return cinemaHallEntityMapper.toDomain(jpaCinemaHallRepository.save(tempCinemaHallEntity));
        }
        throw new CinemaHallNotFoundException();
    }
}
