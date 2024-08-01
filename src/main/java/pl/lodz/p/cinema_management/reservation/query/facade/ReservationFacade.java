package pl.lodz.p.cinema_management.reservation.query.facade;


import pl.lodz.p.cinema_management.reservation.command.domain.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log
public
class ReservationFacade {

    private final JpaQueryReservationRepository reservationRepository;
    private final ReservationDtoMapper reservationDtoMapper;
    private final PageReservationDtoMapper pageReservationDtoMapper;

    public ReservationDto findByReservationNumber(final String reservationNumber) {
        final Optional<Reservation> maybeReservation = reservationRepository.findByReservationNumber(reservationNumber);
        return reservationDtoMapper.toDto(maybeReservation.orElseThrow(ReservationDtoNotFoundException::new));
    }

    public PageReservationDto findAll(final Pageable pageable) {
        Page<Reservation> pageOfReservationsEntity = reservationRepository.findAll(pageable);
        List<Reservation> reservationsOnCurrentPage = new ArrayList<>(pageOfReservationsEntity.getContent());

        final PageReservation pageReservation = new PageReservation(
                reservationsOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfReservationsEntity.getTotalPages(),
                pageOfReservationsEntity.getTotalElements()
        );
        return pageReservationDtoMapper.toPageDto(pageReservation);
    }
}
