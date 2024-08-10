package pl.lodz.p.cinema_management.filmshow.application;

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
import pl.lodz.p.cinema_management.filmshow.command.application.BookCommand;
import pl.lodz.p.cinema_management.filmshow.command.application.CreateCommand;
import pl.lodz.p.cinema_management.filmshow.command.application.ReleaseCommand;
import pl.lodz.p.cinema_management.filmshow.command.application.FilmShowService;
import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShow;
import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShowNotFoundException;
import pl.lodz.p.cinema_management.filmshow.command.domain.Seat;
import pl.lodz.p.cinema_management.filmshow.query.facade.FilmShowDto;
import pl.lodz.p.cinema_management.filmshow.query.facade.PageFilmShowDto;
import pl.lodz.p.cinema_management.filmshow.query.facade.SeatDto;
import pl.lodz.p.cinema_management.user.domain.User;
import pl.lodz.p.cinema_management.user.domain.UserService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PaymentControllerTest extends BaseIT {

    @Autowired
    UserService userService;

    @Autowired
    CinemaHallService cinemaHallService;

    @Autowired
    FilmService filmService;

    @Autowired
    FilmShowService filmShowService;

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
                "/api/v1/filmShows",
                token,
                createCommand,
                void.class
        );

        // Then
        assertEquals(HttpStatus.NOT_FOUND, postResponse.getStatusCode());
    }


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
                "/api/v1/filmShows",
                token,
                command,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        // Fetch the reservation from the service to verify
        FilmShow filmShow = filmShowService.findByFilmShowNumber("res1");

        // Verify reservation details
        assertNotNull(filmShow, "Reservation should not be null");
        assertEquals(cinemaHall.getName(), filmShow.getCinemaHallName(), "Cinema hall name should match");
        assertEquals(film.getName(), filmShow.getFilmName(), "Film name should match");
        assertEquals(reservationDateTime, filmShow.getFilmShowDateTime(), "Reservation date and time should match");

        // Verify seats
        assertNotNull(filmShow.getSeats(), "Seats should not be null");
        assertEquals(cinemaHall.getSeats().size(), filmShow.getSeats().size(), "Number of seats should match");
        for (int i = 0; i < filmShow.getSeats().size(); i++) {
            assertEquals(cinemaHall.getSeats().get(i).getSeatIdentifier(), filmShow.getSeats().get(i).getSeatIdentifier(), "Seat identifier should match");
        }

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
        CreateCommand createCommand2 = new CreateCommand("res2", cinemaHallId, filmId, reservationDateTime2);

        callHttpMethod(HttpMethod.POST, "/api/v1/filmShows", token, createCommand1, Void.class);

        // When
        var postResponse = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmShows",
                token,
                createCommand2,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.CONFLICT, postResponse.getStatusCode());
    }


    @Test
    void admin_should_create_reservation_successfully_when_not_overlapping_with_existing_reservation() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        Film film = TestFilmFactory.createFilm();
        Integer filmId = filmService.save(film).getId();

        LocalDateTime reservationDateTime1 = LocalDateTime.of(2024, Month.JANUARY, 1, 12,0);
        LocalDateTime reservationDateTime2 = LocalDateTime.of(2024, Month.JANUARY, 1, 13,45);

        CreateCommand createCommand1 = new CreateCommand("res1", cinemaHallId, filmId, reservationDateTime1);
        CreateCommand createCommand2 = new CreateCommand("res2", cinemaHallId, filmId, reservationDateTime2);

        callHttpMethod(HttpMethod.POST, "/api/v1/filmShows", token, createCommand1, Void.class);

        // When
        var postResponse = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmShows",
                token,
                createCommand2,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
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
                "/api/v1/filmShows",
                token,
                createCommand,
                Void.class
        );

        // When
        var deleteResponse = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/filmShows/res1",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode(), "Admin should be able to remove a reservation");

        // Verify that the reservation has been removed
        assertThrows(FilmShowNotFoundException.class,
                () -> filmShowService.findByFilmShowNumber("res1"),
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
                "/api/v1/filmShows/nonexistent",
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
                "/api/v1/filmShows",
                token,
                createCommand,
                Void.class
        );

        // When
        BookCommand bookCommand = new BookCommand(Arrays.asList("A1", "A2"), null);
        var bookResponse = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmShows/res1/book",
                token,
                bookCommand,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, bookResponse.getStatusCode());
        // Fetch reservation and verify seats
        FilmShow filmShow = filmShowService.findByFilmShowNumber("res1");
        for (String seatIdentifier : Arrays.asList("A1", "A2")) {
            Seat seat = filmShow.getSeats().stream()
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
        filmShowService.create(createCommand);

        BookCommand bookCommand = new BookCommand(Arrays.asList("A1", "A2"), null);
        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmShows/res1/book",
                token,
                bookCommand,
                Void.class
        );

        // When
        ReleaseCommand releaseCommand = new ReleaseCommand(Arrays.asList("A1", "A2"), null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmShows/res1/release",
                token,
                releaseCommand,
                Void.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Fetch reservation and verify seats
        FilmShow filmShow = filmShowService.findByFilmShowNumber("res1");
        for (String seatIdentifier : Arrays.asList("A1", "A2")) {
            Seat seat = filmShow.getSeats().stream()
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
        filmShowService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/filmShows/res1",
                token,
                null,
                FilmShowDto.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        FilmShowDto filmShowDto = response.getBody();
        assert filmShowDto != null;
        assertEquals("res1", filmShowDto.filmShowNumber());
        assertEquals(cinemaHall.getName(), filmShowDto.cinemaHallName());
        assertEquals(film.getName(), filmShowDto.filmName());
        assertEquals(reservationDateTime, filmShowDto.filmShowDateTime());
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
        filmShowService.create(createCommand1);

        LocalDateTime reservationDateTime2 = LocalDateTime.of(2024, Month.JANUARY, 2, 12, 0);
        CreateCommand createCommand2 = new CreateCommand("res2", cinemaHallId, filmId, reservationDateTime2);
        filmShowService.create(createCommand2);

        // When
        var getResponse = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/filmShows",
                token,
                null,
                PageFilmShowDto.class);

        // Then
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        PageFilmShowDto reservationListDto = getResponse.getBody();
        assertNotNull(reservationListDto);
        assertEquals(2, reservationListDto.filmShows().size());
        assertTrue(reservationListDto.filmShows().stream().anyMatch(r -> r.filmShowNumber().equals("res1")));
        assertTrue(reservationListDto.filmShows().stream().anyMatch(r -> r.filmShowNumber().equals("res2")));
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
                "/api/v1/filmShows",
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
        filmShowService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/filmShows/res1",
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
        filmShowService.create(createCommand);

        // When
        BookCommand bookCommand = new BookCommand(Arrays.asList("A1", "A2"), null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmShows/res1/book",
                token,
                bookCommand,
                void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        FilmShow filmShow = filmShowService.findByFilmShowNumber("res1");
        for (String seatIdentifier : Arrays.asList("A1", "A2")) {
            Seat seat = filmShow.getSeats().stream()
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
        filmShowService.create(createCommand);

        BookCommand bookCommand1 = new BookCommand(Arrays.asList("A1", "A2"), null);
        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmShows/res1/book",
                token,
                bookCommand1,
                Void.class
        );

        // When
        BookCommand bookCommand2 = new BookCommand(Arrays.asList("A1", "A2"), null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmShows/res1/book",
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
        filmShowService.create(createCommand);

        BookCommand bookCommand = new BookCommand(Arrays.asList("A1", "A2"), null);
        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmShows/res1/book",
                token,
                bookCommand,
                Void.class
        );

        // When
        ReleaseCommand releaseCommand = new ReleaseCommand(Arrays.asList("A1", "A2"),null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmShows/res1/release",
                token,
                releaseCommand,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify seats are released
        var reservationResponse = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/filmShows/res1",
                token,
                null,
                FilmShowDto.class
        );

        FilmShowDto filmShowDto = reservationResponse.getBody();
        assertNotNull(filmShowDto);
        for (SeatDto seatDto : filmShowDto.seats()) {
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
        filmShowService.create(createCommand);

        BookCommand bookCommand = new BookCommand(Arrays.asList("A1", "A2"), null);
        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmShows/res1/book",
                token,
                bookCommand,
                void.class
        );

        // When
        ReleaseCommand releaseCommand = new ReleaseCommand(Arrays.asList("A1", "A2"), null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmShows/res1/release",
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
        filmShowService.create(createCommand);

        BookCommand bookCommand = new BookCommand(Arrays.asList("A1", "A2"), null);
        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmShows/res1/book",
                token,
                bookCommand,
                Void.class
        );

        // When
        ReleaseCommand releaseCommand = new ReleaseCommand(Arrays.asList("A1", "A2"), null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmShows/res1/release",
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
        filmShowService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/filmShows",
                token,
                null,
                PageFilmShowDto.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PageFilmShowDto reservationsListDto = response.getBody();
        assertNotNull(reservationsListDto);
        assertTrue(reservationsListDto.filmShows().stream()
                .anyMatch(reservation -> reservation.filmShowNumber().equals("res1")));
    }


    @Test
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
                "/api/v1/filmShows",
                token,
                command,
                void.class
        );

        // Then
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        // Fetch the reservation from the service to verify
        FilmShow filmShow = filmShowService.findByFilmShowNumber("res1");

        // Verify reservation details
        assertNotNull(filmShow, "Reservation should not be null");
        assertEquals(cinemaHall.getName(), filmShow.getCinemaHallName(), "Cinema hall name should match");
        assertEquals(film.getName(), filmShow.getFilmName(), "Film name should match");
        assertEquals(reservationDateTime, filmShow.getFilmShowDateTime(), "Reservation date and time should match");

        // Verify seats
        assertNotNull(filmShow.getSeats(), "Seats should not be null");
        assertEquals(cinemaHall.getSeats().size(), filmShow.getSeats().size(), "Number of seats should match");
        for (int i = 0; i < filmShow.getSeats().size(); i++) {
            assertEquals(cinemaHall.getSeats().get(i).getSeatIdentifier(), filmShow.getSeats().get(i).getSeatIdentifier(), "Seat identifier should match");
        }

    }

}