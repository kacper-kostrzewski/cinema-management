package pl.lodz.p.cinema_management.ticket.api;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import pl.lodz.p.cinema_management.filmshow.command.application.FilmShowService;
import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShow;
import pl.lodz.p.cinema_management.ticket.command.application.CreateCommand;
import pl.lodz.p.cinema_management.ticket.command.application.TicketService;
import pl.lodz.p.cinema_management.ticket.command.domain.Ticket;
import pl.lodz.p.cinema_management.ticket.command.domain.TicketStatus;
import pl.lodz.p.cinema_management.ticket.query.facade.PageTicketDto;
import pl.lodz.p.cinema_management.ticket.query.facade.TicketDto;
import pl.lodz.p.cinema_management.ticket.query.facade.TicketFacade;
import pl.lodz.p.cinema_management.user.domain.User;
import pl.lodz.p.cinema_management.user.domain.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TicketControllerTest extends BaseIT {

    @Autowired
    TicketService ticketService;

    @Autowired
    FilmService filmService;

    @Autowired
    CinemaHallService cinemaHallService;

    @Autowired
    FilmShowService filmShowService;

    @Autowired
    UserService userService;

    @Autowired
    TicketFacade ticketFacade;


    // Helper Methods

    private User createAndSaveAdmin() {
        User admin = TestUserFactory.createAdmin();
        return userService.save(admin);
    }

    private User createAndSaveUser() {
        User user = TestUserFactory.createUser();
        return userService.save(user);
    }

    private Film createAndSaveFilm() {
        Film film = TestFilmFactory.createFilm();
        return filmService.save(film);
    }

    private CinemaHall createAndSaveCinemaHall() {
        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        return cinemaHallService.save(cinemaHall);
    }

    private FilmShow createAndSaveFilmShow(Film film, CinemaHall cinemaHall) {
        String filmShowNumber = "SHOW-123";
        pl.lodz.p.cinema_management.filmshow.command.application.CreateCommand filmShowCreateCommand =
                new pl.lodz.p.cinema_management.filmshow.command.application.CreateCommand(
                        filmShowNumber, cinemaHall.getId(), film.getId(), LocalDateTime.now()
                );
        return filmShowService.create(filmShowCreateCommand);
    }


    @Test
    void admin_should_mark_ticket_as_used_successfully() {
        // Given
        User admin = createAndSaveAdmin();
        String token = getAccessTokenForUser(admin);

        User user = createAndSaveUser();
        Film film = createAndSaveFilm();
        CinemaHall cinemaHall = createAndSaveCinemaHall();
        FilmShow filmShow = createAndSaveFilmShow(film, cinemaHall);

        String ticketNumber = "TICKET-123";
        String seatId = "A1";
        BigDecimal price = new BigDecimal("10.00");

        CreateCommand ticketCreateCommand = new pl.lodz.p.cinema_management.ticket.command.application.CreateCommand(ticketNumber, filmShow.getId(), user.getId(), seatId, price);
        ticketService.create(ticketCreateCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/ticket/" + ticketNumber + "/use",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Ticket ticket = ticketService.findByTicketNumber(ticketNumber);
        assertEquals(TicketStatus.USED, ticket.getTicketStatus(), "Ticket status should be USED");
    }


    @Test
    void admin_should_cancel_ticket_successfully() {
        // Given
        User admin = createAndSaveAdmin();
        String token = getAccessTokenForUser(admin);

        User user = createAndSaveUser();
        Film film = createAndSaveFilm();
        CinemaHall cinemaHall = createAndSaveCinemaHall();
        FilmShow filmShow = createAndSaveFilmShow(film, cinemaHall);

        String ticketNumber = "TICKET-123";
        String seatId = "A1";
        BigDecimal price = new BigDecimal("10.00");

        CreateCommand ticketCreateCommand = new pl.lodz.p.cinema_management.ticket.command.application.CreateCommand(ticketNumber, filmShow.getId(), user.getId(), seatId, price);
        ticketService.create(ticketCreateCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/ticket/" + ticketNumber + "/cancel",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Ticket ticket = ticketService.findByTicketNumber(ticketNumber);
        assertEquals(TicketStatus.INVALID, ticket.getTicketStatus(), "Ticket status should be USED");
    }

    @Test
    void user_should_get_ticket_information_successfully() {
        // Given
        User user = createAndSaveUser();
        String token = getAccessTokenForUser(user);

        Film film = createAndSaveFilm();
        CinemaHall cinemaHall = createAndSaveCinemaHall();
        FilmShow filmShow = createAndSaveFilmShow(film, cinemaHall);

        String ticketNumber = "TICKET-123";
        String seatId = "A1";
        BigDecimal price = new BigDecimal("10.00");

        CreateCommand createCommand = new CreateCommand(ticketNumber, filmShow.getId(), user.getId(), seatId, price);
        ticketService.create(createCommand);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/ticket/" + ticketNumber,
                token,
                null,
                TicketDto.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        TicketDto ticket = response.getBody();
        assertNotNull(ticket, "Ticket should not be null");
        assertEquals(ticketNumber, ticket.ticketNumber(), "Ticket number should match");
    }

    @Test
    void user_should_get_pageable_list_of_user_tickets() {
        // Given
        User user = createAndSaveUser();
        String token = getAccessTokenForUser(user);

        Film film = createAndSaveFilm();
        CinemaHall cinemaHall = createAndSaveCinemaHall();
        FilmShow filmShow = createAndSaveFilmShow(film, cinemaHall);

        String ticketNumber1 = "TICKET-123";
        String seatId1 = "A1";
        BigDecimal price1 = new BigDecimal("10.00");

        CreateCommand createCommand1 = new CreateCommand(ticketNumber1, filmShow.getId(), user.getId(), seatId1, price1);
        ticketService.create(createCommand1);

        String ticketNumber2 = "TICKET-124";
        String seatId2 = "A2";
        BigDecimal price2 = new BigDecimal("12.00");

        CreateCommand createCommand2 = new CreateCommand(ticketNumber2, filmShow.getId(), user.getId(), seatId2, price2);
        ticketService.create(createCommand2);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/ticket/my-tickets?page=0&size=3",
                token,
                null,
                PageTicketDto.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Additional Assertions
        PageTicketDto pageTicketDto = response.getBody();
        assertNotNull(pageTicketDto, "The response body should not be null");

        List<TicketDto> tickets = pageTicketDto.tickets();
        assertNotNull(tickets, "The list of tickets should not be null");
        assertEquals(2, tickets.size(), "The list should contain exactly 2 tickets");

        // Assert details of the first ticket
        TicketDto ticket1 = tickets.getFirst();
        assertEquals(ticketNumber1, ticket1.ticketNumber(), "The first ticket number should match");
        assertEquals(seatId1, ticket1.seatId(), "The seat ID of the first ticket should match");
        assertEquals(price1, ticket1.price(), "The price of the first ticket should match");

        // Assert details of the second ticket
        TicketDto ticket2 = tickets.get(1);
        assertEquals(ticketNumber2, ticket2.ticketNumber(), "The second ticket number should match");
        assertEquals(seatId2, ticket2.seatId(), "The seat ID of the second ticket should match");
        assertEquals(price2, ticket2.price(), "The price of the second ticket should match");

        // Additional assertions on the pagination details
        assertEquals(1, pageTicketDto.currentPage(), "The current page should be 0");
        assertEquals(1, pageTicketDto.totalPages(), "The total pages should be 1 since there are only 2 tickets and page size is 3");
        assertEquals(2, pageTicketDto.totalElements(), "The total elements should be 2");

    }

    @Test
    void admin_should_get_all_tickets_successfully() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/ticket",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void user_should_not_be_able_to_get_all_tickets() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/ticket",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void admin_should_get_paginated_tickets_successfully() {
        // Given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/ticket?page=0&size=3",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void user_should_not_be_able_to_get_paginated_tickets() {
        // Given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        // When
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/ticket?page=0&size=3",
                token,
                null,
                Void.class
        );

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }




}

