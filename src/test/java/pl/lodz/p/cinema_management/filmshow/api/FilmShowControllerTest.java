package pl.lodz.p.cinema_management.filmshow.api;

import org.junit.jupiter.api.Assertions;
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
import pl.lodz.p.cinema_management.filmshow.command.application.FilmShowService;
import pl.lodz.p.cinema_management.ticket.command.domain.Ticket;
import pl.lodz.p.cinema_management.ticket.query.facade.TicketDto;
import pl.lodz.p.cinema_management.user.domain.User;
import pl.lodz.p.cinema_management.user.domain.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilmShowControllerTest extends BaseIT {

    @Autowired
    FilmShowService filmShowService;

    @Autowired
    UserService userService;

    @Autowired
    FilmService filmService;

    @Autowired
    CinemaHallService cinemaHallService;

    @Test
    void admin_should_create_film_show_successfully() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());

        // When
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows",
                token,
                createCommand,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void admin_should_remove_film_show_successfully() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";
        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/filmshows/" + filmShowNumber,
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


    @Test
    void admin_should_book_seats_successfully() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        BookCommand bookCommand = new BookCommand(null, List.of("A1", "A2"));

        // When
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows/SHOW-123/book",
                token,
                bookCommand,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void admin_should_book_seats_on_behalf_of_the_user_successfully() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        User user = TestUserFactory.createUser();
        userService.save(user);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        BookCommand bookCommand = new BookCommand(user.getId(), List.of("A1", "A2"));

        // When
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows/SHOW-123/book",
                token,
                bookCommand,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void admin_should_get_response_code_conflict_when_trying_to_book_taken_seats() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String adminToken = getAccessTokenForUser(admin);

        User user = TestUserFactory.createUser();
        userService.save(user);
        String userToken = getAccessTokenForUser(user);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        BookCommand bookCommand = new BookCommand(null, List.of("A1", "A2"));

        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows/SHOW-123/book",
                userToken,
                bookCommand,
                Void.class
        );

        // When
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows/SHOW-123/book",
                adminToken,
                bookCommand,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }


    @Test
    void admin_should_get_information_about_any_film_show() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/filmshows/SHOW-123",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void admin_should_get_information_about_all_film_shows() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/filmshows",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void admin_should_get_pageable_list_of_film_shows() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/filmshows?page=0&size=3",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void admin_should_get_response_code_conflict_when_trying_to_create_film_show_overlapping_with_another_film_show() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows",
                token,
                createCommand,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }


    @Test
    void user_should_not_be_able_to_create_film_show() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());

        // When
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows",
                token,
                createCommand,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }


    @Test
    void user_should_not_be_able_to_remove_film_show() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";
        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/filmshows/" + filmShowNumber,
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

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        BookCommand bookCommand = new BookCommand(null, List.of("A1", "A2"));

        // When
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows/SHOW-123/book",
                token,
                bookCommand,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void user_should_get_response_code_conflict_when_trying_to_book_taken_seats() {
        // Given
        User user1 = TestUserFactory.createUser();
        userService.save(user1);
        String token1 = getAccessTokenForUser(user1);

        User user2 = TestUserFactory.createUser();
        userService.save(user2);
        String token2 = getAccessTokenForUser(user2);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        BookCommand bookCommand1 = new BookCommand(null, List.of("A1", "A2"));

        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows/SHOW-123/book",
                token1,
                bookCommand1,
                Void.class
        );

        BookCommand bookCommand2 = new BookCommand(null, List.of("A1", "A2"));

        // When
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows/SHOW-123/book",
                token2,
                bookCommand2,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void user_should_get_information_about_any_film_show() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/filmshows/SHOW-123",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void user_should_get_information_about_all_film_shows() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/filmshows",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void user_should_get_pageable_list_of_film_shows() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/filmshows?page=0&size=3",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void user_should_create_order_successfully() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        BookCommand bookCommand = new BookCommand(null, List.of("A1", "A2"));

        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows/SHOW-123/book",
                token,
                bookCommand,
                Void.class
        );

        // When
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows/" + filmShowNumber + "/order",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void user_should_create_order_and_initiate_payment_successfully() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        BookCommand bookCommand = new BookCommand(null, List.of("A1", "A2"));

        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows/SHOW-123/book",
                token,
                bookCommand,
                Void.class
        );

        // When
        var orderResponse = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows/" + filmShowNumber + "/order",
                token,
                null,
                String.class
        );


        assertEquals(HttpStatus.OK, orderResponse.getStatusCode());

        String orderId = orderResponse.getBody();


        var paymentResponse = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/order/" + orderId + "/process-payment",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, paymentResponse.getStatusCode());
    }


    @Test
    void user_should_create_order_initiate_and_complete_payment_and_generate_tickets_successfully() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String adminToken = getAccessTokenForUser(admin);

        Film film = TestFilmFactory.createFilm();
        filmService.save(film);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        cinemaHallService.save(cinemaHall);

        String filmShowNumber = "SHOW-123";

        CreateCommand createCommand = new CreateCommand(filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now());
        filmShowService.create(createCommand);

        BookCommand bookCommand = new BookCommand(null, List.of("A1", "A2"));

        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows/SHOW-123/book",
                token,
                bookCommand,
                Void.class
        );

        // When
        var orderResponse = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/filmshows/" + filmShowNumber + "/order",
                token,
                null,
                String.class
        );


        assertEquals(HttpStatus.OK, orderResponse.getStatusCode());

        String orderId = orderResponse.getBody();


        callHttpMethod(
                HttpMethod.POST,
                "/api/v1/order/" + orderId + "/process-payment",
                token,
                null,
                Void.class
        );


        var paymentResponse = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/payment/PAYMENT-1/complete",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, paymentResponse.getStatusCode());

        // Verify first ticket
        var ticketResponse1 = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/ticket/TICKET-1",
                adminToken,
                null,
                TicketDto.class
        );

        assertEquals(HttpStatus.OK, ticketResponse1.getStatusCode());
        TicketDto ticket1 = ticketResponse1.getBody();
        assertNotNull(ticket1, "Ticket 1 should not be null");
        assertEquals("A1", ticket1.seatId(), "Seat ID for ticket 1 should be A1");

        // Verify second ticket
        var ticketResponse2 = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/ticket/TICKET-2",
                adminToken,
                null,
                TicketDto.class
        );

        assertEquals(HttpStatus.OK, ticketResponse2.getStatusCode());
        TicketDto ticket2 = ticketResponse2.getBody();
        assertNotNull(ticket2, "Ticket 2 should not be null");
        assertEquals("A2", ticket2.seatId(), "Seat ID for ticket 2 should be A2");

    }



}



