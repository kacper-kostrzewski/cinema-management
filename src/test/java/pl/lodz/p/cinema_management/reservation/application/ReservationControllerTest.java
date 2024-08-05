package pl.lodz.p.cinema_management.reservation.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.lodz.p.cinema_management.BaseIT;
import pl.lodz.p.cinema_management.TestCinemaHallFactory;
import pl.lodz.p.cinema_management.TestFilmFactory;
import pl.lodz.p.cinema_management.TestUserFactory;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHallService;
import pl.lodz.p.cinema_management.film.domain.Film;
import pl.lodz.p.cinema_management.film.domain.FilmService;
import pl.lodz.p.cinema_management.reservation.command.application.BookCommand;
import pl.lodz.p.cinema_management.reservation.command.application.CreateCommand;
import pl.lodz.p.cinema_management.reservation.command.application.ReleaseCommand;
import pl.lodz.p.cinema_management.reservation.command.application.ReservationService;
import pl.lodz.p.cinema_management.reservation.command.domain.Reservation;
import pl.lodz.p.cinema_management.reservation.command.domain.ReservationNotFoundException;
import pl.lodz.p.cinema_management.reservation.command.domain.Seat;
import pl.lodz.p.cinema_management.reservation.query.facade.PageReservationDto;
import pl.lodz.p.cinema_management.reservation.query.facade.ReservationDto;
import pl.lodz.p.cinema_management.reservation.query.facade.SeatDto;
import pl.lodz.p.cinema_management.user.domain.User;
import pl.lodz.p.cinema_management.user.domain.UserService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ReservationControllerTest extends BaseIT {

    @Autowired
    UserService userService;

    @Autowired
    CinemaHallService cinemaHallService;

    @Autowired
    FilmService filmService;

    @Autowired
    ReservationService reservationService;


    @Test
    void admin_should_create_reservation_successfully() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12,0);

        // When
        CreateCommand command = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime);
        var postResponse = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations",
                token,
                command,
                void.class
        );

        // Then
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        // Fetch the reservation from the service to verify
        Reservation reservation = reservationService.findByReservationNumber("res1");

        // Verify reservation details
        assertNotNull(reservation, "Reservation should not be null");
        assertEquals(cinemaHall.getName(), reservation.getCinemaHallName(), "Cinema hall name should match");
        assertEquals(film.getName(), reservation.getFilmName(), "Film name should match");
        assertEquals(reservationDateTime, reservation.getReservationDateTime(), "Reservation date and time should match");

        // Verify seats
        assertNotNull(reservation.getSeats(), "Seats should not be null");
        assertEquals(cinemaHall.getSeats().size(), reservation.getSeats().size(), "Number of seats should match");
        for (int i = 0; i < reservation.getSeats().size(); i++) {
            assertEquals(cinemaHall.getSeats().get(i).getSeatIdentifier(), reservation.getSeats().get(i).getSeatIdentifier(), "Seat identifier should match");
        }

    }


    @Test
    void admin_should_return_not_found_when_trying_to_create_reservation_for_non_existent_cinema_hall() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12,0);

        CreateCommand createCommand = new CreateCommand("res1", 5, filmId, reservationDateTime);

        // When
        var postResponse = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations",
                token,
                createCommand,
                void.class
        );

        // Then
        assertEquals(HttpStatus.NOT_FOUND, postResponse.getStatusCode());
    }


    @Test
    void admin_should_return_conflict_when_trying_to_create_reservation_overlapping_with_existing_reservation() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime1 = LocalDateTime.of(2024, Month.JANUARY, 1, 12,0);
        LocalDateTime reservationDateTime2 = LocalDateTime.of(2024, Month.JANUARY, 1, 12,30);

        CreateCommand createCommand1 = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime1);
        CreateCommand createCommand2 = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime2);

        callHttpMethod(HttpMethod.POST, "/api/v1/reservations", token, createCommand1, Void.class);

        // When
        var postResponse = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations",
                token,
                createCommand2,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.CONFLICT, postResponse.getStatusCode());
    }


    @Test
    void admin_should_remove_reservation_successfully() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);

        CreateCommand createCommand = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime);
        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations",
                token,
                createCommand,
                Void.class
        );

        // When
        var deleteResponse = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/reservations/res1",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode(), "Admin should be able to remove a reservation");

        // Verify that the reservation has been removed
        assertThrows(ReservationNotFoundException.class,
                () -> reservationService.findByReservationNumber("res1"),
                "Reservation should be removed and not found");
    }


    @Test
    void admin_should_return_not_found_when_trying_to_remove_non_existent_reservation() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        // When
        var deleteResponse = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/reservations/nonexistent",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
    }


    @Test
    void admin_should_book_seats_successfully() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        CreateCommand createCommand = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime);
        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations",
                token,
                createCommand,
                Void.class
        );

        // When
        BookCommand bookCommand = new BookCommand(Arrays.asList("A1", "A2"), null);
        var bookResponse = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/res1/book",
                token,
                bookCommand,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, bookResponse.getStatusCode());
        // Fetch reservation and verify seats
        Reservation reservation = reservationService.findByReservationNumber("res1");
        for (String seatIdentifier : Arrays.asList("A1", "A2")) {
            Seat seat = reservation.getSeats().stream()
                    .filter(s -> s.getSeatIdentifier().equals(seatIdentifier))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Seat not found: " + seatIdentifier));

            assertTrue(seat.isTaken(), "Seat " + seatIdentifier + " should be taken");

            // Verify the takenBy field matches the admin name
            User seatTakenByUser = userService.findById(seat.getTakenBy());
            String expectedName = admin.getName();
            String actualName = seatTakenByUser.getName();

            assertEquals(expectedName, actualName, "Seat " + seatIdentifier + " should be taken by admin");
        }
    }

    @Test
    void admin_should_release_seats_successfully() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        CreateCommand createCommand = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime);
        reservationService.create(createCommand);

        BookCommand bookCommand = new BookCommand(Arrays.asList("A1", "A2"), null);
        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/res1/book",
                token,
                bookCommand,
                Void.class
        );

        // When
        ReleaseCommand releaseCommand = new ReleaseCommand(Arrays.asList("A1", "A2"), null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/res1/release",
                token,
                releaseCommand,
                Void.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Fetch reservation and verify seats
        Reservation reservation = reservationService.findByReservationNumber("res1");
        for (String seatIdentifier : Arrays.asList("A1", "A2")) {
            Seat seat = reservation.getSeats().stream()
                    .filter(s -> s.getSeatIdentifier().equals(seatIdentifier))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Seat not found: " + seatIdentifier));

            assertFalse(seat.isTaken(), "Seat " + seatIdentifier + " should be released");

            // Verify the takenBy field is null
            assertNull(seat.getTakenBy(), "Seat " + seatIdentifier + " should not be taken by anyone");
        }
    }

    @Test
    void admin_should_get_reservation_details_successfully() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        CreateCommand createCommand = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime);
        reservationService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/reservations/res1",
                token,
                null,
                ReservationDto.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReservationDto reservationDto = response.getBody();
        assert reservationDto != null;
        assertEquals("res1", reservationDto.reservationNumber());
        assertEquals(cinemaHall.getName(), reservationDto.cinemaHallName());
        assertEquals(film.getName(), reservationDto.filmName());
        assertEquals(reservationDateTime, reservationDto.reservationDateTime());
    }

    @Test
    void admin_should_get_reservations_list_successfully() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime1 = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        CreateCommand createCommand1 = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime1);
        reservationService.create(createCommand1);

        LocalDateTime reservationDateTime2 = LocalDateTime.of(2024, Month.JANUARY, 2, 12, 0);
        CreateCommand createCommand2 = new CreateCommand("res2", cinemaHallId, filmId, reservationDateTime2);
        reservationService.create(createCommand2);

        // When
        var getResponse = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/reservations",
                token,
                null,
                PageReservationDto.class);

        // Then
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        PageReservationDto reservationListDto = getResponse.getBody();
        assertNotNull(reservationListDto);
        assertEquals(2, reservationListDto.reservations().size());
        assertTrue(reservationListDto.reservations().stream().anyMatch(r -> r.reservationNumber().equals("res1")));
        assertTrue(reservationListDto.reservations().stream().anyMatch(r -> r.reservationNumber().equals("res2")));
    }

    @Test
    void user_should_return_forbidden_when_trying_to_create_reservation() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        CreateCommand createCommand = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime);

        // When
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations",
                token,
                createCommand,
                void.class
        );

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void user_should_return_forbidden_when_trying_to_remove_reservation() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        CreateCommand createCommand = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime);
        reservationService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/reservations/res1",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }



    @Test
    void user_should_book_seats_successfully() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        CreateCommand createCommand = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime);
        reservationService.create(createCommand);

        // When
        BookCommand bookCommand = new BookCommand(Arrays.asList("A1", "A2"), null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/res1/book",
                token,
                bookCommand,
                void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Reservation reservation = reservationService.findByReservationNumber("res1");
        for (String seatIdentifier : Arrays.asList("A1", "A2")) {
            Seat seat = reservation.getSeats().stream()
                    .filter(s -> s.getSeatIdentifier().equals(seatIdentifier))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Seat not found: " + seatIdentifier));

            assertTrue(seat.isTaken(), "Seat " + seatIdentifier + " should be taken");

            // Verify the takenBy field matches the user's name
            User seatTakenByUser = userService.findById(seat.getTakenBy());
            String expectedName = user.getName();
            String actualName = seatTakenByUser.getName();

            assertEquals(expectedName, actualName, "Seat " + seatIdentifier + " should be taken by admin");
        }
    }

    @Test
    void user_should_return_conflict_when_trying_to_book_already_booked_seats() {
        // Given
        User user = TestUserFactory.createUser();
        User otherUser = TestUserFactory.createUser();
        userService.save(user);
        userService.save(otherUser);
        String token = getAccessTokenForUser(user);
        String otherToken = getAccessTokenForUser(otherUser);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        CreateCommand createCommand = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime);
        reservationService.create(createCommand);

        BookCommand bookCommand1 = new BookCommand(Arrays.asList("A1", "A2"), null);
        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/res1/book",
                token,
                bookCommand1,
                Void.class
        );

        // When
        BookCommand bookCommand2 = new BookCommand(Arrays.asList("A1", "A2"), null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/res1/book",
                otherToken,
                bookCommand2,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void user_should_release_seats_successfully() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        CreateCommand createCommand = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime);
        reservationService.create(createCommand);

        BookCommand bookCommand = new BookCommand(Arrays.asList("A1", "A2"), null);
        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/res1/book",
                token,
                bookCommand,
                Void.class
        );

        // When
        ReleaseCommand releaseCommand = new ReleaseCommand(Arrays.asList("A1", "A2"),null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/res1/release",
                token,
                releaseCommand,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify seats are released
        var reservationResponse = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/reservations/res1",
                token,
                null,
                ReservationDto.class
        );

        ReservationDto reservationDto = reservationResponse.getBody();
        assertNotNull(reservationDto);
        for (SeatDto seatDto : reservationDto.seats()) {
            if (seatDto.seatIdentifier().equals("A1") || seatDto.seatIdentifier().equals("A2")) {
                assertNull(seatDto.takenBy(), "Seat " + seatDto.seatIdentifier() + " should be available");
            }
        }
    }


    @Test
    void user_should_return_method_not_allowed_when_trying_to_release_seats_for_another_user() {
        // Given
        User user = TestUserFactory.createUser();
        User otherUser = TestUserFactory.createUser();
        userService.save(user);
        userService.save(otherUser);
        String token = getAccessTokenForUser(user);
        String otherToken = getAccessTokenForUser(otherUser);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        CreateCommand createCommand = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime);
        reservationService.create(createCommand);

        BookCommand bookCommand = new BookCommand(Arrays.asList("A1", "A2"), null);
        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/res1/book",
                token,
                bookCommand,
                void.class
        );

        // When
        ReleaseCommand releaseCommand = new ReleaseCommand(Arrays.asList("A1", "A2"), null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/res1/release",
                otherToken,
                releaseCommand,
                void.class
        );

        // Then
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }


    @Test
    void user_should_return_method_not_allowed_when_trying_to_release_seats_not_booked_by_them() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        User user2 = TestUserFactory.createUser();
        userService.save(user2);
        String token2 = getAccessTokenForUser(user2);


        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        CreateCommand createCommand = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime);
        reservationService.create(createCommand);

        BookCommand bookCommand = new BookCommand(Arrays.asList("A1", "A2"), null);
        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/res1/book",
                token,
                bookCommand,
                Void.class
        );

        // When
        ReleaseCommand releaseCommand = new ReleaseCommand(Arrays.asList("A1", "A2"), null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/res1/release",
                token2,
                releaseCommand,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    void user_should_get_reservations_list_successfully() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        CreateCommand createCommand = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime);
        reservationService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/reservations",
                token,
                null,
                PageReservationDto.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PageReservationDto reservationsListDto = response.getBody();
        assertNotNull(reservationsListDto);
        assertTrue(reservationsListDto.reservations().stream()
                .anyMatch(reservation -> reservation.reservationNumber().equals("res1")));
    }



    void admin_should_create_reservation_only_with_available_cinema_hall() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12,0);

        // When
        CreateCommand command = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime);
        var postResponse = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations",
                token,
                command,
                void.class
        );

        // Then
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        // Fetch the reservation from the service to verify
        Reservation reservation = reservationService.findByReservationNumber("res1");

        // Verify reservation details
        assertNotNull(reservation, "Reservation should not be null");
        assertEquals(cinemaHall.getName(), reservation.getCinemaHallName(), "Cinema hall name should match");
        assertEquals(film.getName(), reservation.getFilmName(), "Film name should match");
        assertEquals(reservationDateTime, reservation.getReservationDateTime(), "Reservation date and time should match");

        // Verify seats
        assertNotNull(reservation.getSeats(), "Seats should not be null");
        assertEquals(cinemaHall.getSeats().size(), reservation.getSeats().size(), "Number of seats should match");
        for (int i = 0; i < reservation.getSeats().size(); i++) {
            assertEquals(cinemaHall.getSeats().get(i).getSeatIdentifier(), reservation.getSeats().get(i).getSeatIdentifier(), "Seat identifier should match");
        }

    }

}