package pl.lodz.p.cinema_management.reservation.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.lodz.p.cinema_management.BaseIT;
import pl.lodz.p.cinema_management.TestCinemaHallFactory;
import pl.lodz.p.cinema_management.TestUserFactory;
import pl.lodz.p.cinema_management.cinemahall.application.CinemaHallDto;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHallService;
import pl.lodz.p.cinema_management.reservation.command.application.BookCommand;
import pl.lodz.p.cinema_management.reservation.command.application.CreateUsingCinemaHallCommand;
import pl.lodz.p.cinema_management.reservation.command.application.ReleaseCommand;
import pl.lodz.p.cinema_management.reservation.command.application.ReservationService;
import pl.lodz.p.cinema_management.reservation.command.domain.Reservation;
import pl.lodz.p.cinema_management.user.domain.User;
import pl.lodz.p.cinema_management.user.domain.UserService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ReservationControllerTest extends BaseIT {

    @Autowired
    UserService userService;

    @Autowired
    CinemaHallService cinemaHallService;

    @Autowired
    ReservationService reservationService;

    @Test
    void admin_should_be_able_to_create_reservation_from_cinema_hall() {
        //given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        //when
        var command = new CreateUsingCinemaHallCommand("RES123", cinemaHallId);
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/reservations",
                token,
                command,
                CreateUsingCinemaHallCommand.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Reservation reservation = reservationService.findByReservationNumber("RES123");
        for (int i = 0; i < reservation.getSeats().size(); i++) {
            assertEquals(cinemaHall.getSeats().get(i).getSeatIdentifier(), reservation.getSeats().get(i).getSeatIdentifier());
        }
        assertEquals(cinemaHall.getName(), reservation.getCinemaHallName());
    }

    @Test
    void admin_should_be_able_to_delete_reservation() {

        //given
        User admin = TestUserFactory.createAdmin();
        userService.save(admin);
        String token = getAccessTokenForUser(admin);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        var command = new CreateUsingCinemaHallCommand("RES123", cinemaHallId);
        Reservation reservation = reservationService.create(command);

        //when
        var response = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/reservations/" + reservation.getReservationNumber(),
                token,
                null,
                CinemaHallDto.class
        );

        //then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }


    @Test
    void user_should_not_be_able_to_create_reservation_from_cinema_hall() {
        //given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();

        //when
        var command = new CreateUsingCinemaHallCommand("RES123", cinemaHallId);
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/reservations",
                token,
                command,
                CreateUsingCinemaHallCommand.class);

        //then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }


    @Test
    void user_should_be_able_to_view_reservation() {

        //given
        User user = TestUserFactory.createAdmin();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();
        Reservation reservation = reservationService.create(new CreateUsingCinemaHallCommand("RES123", cinemaHallId));


        //when
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/reservations",
                token,
                null,
                CinemaHallDto.class
        );


        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }




    @Test
    void user_should_be_able_to_book_up_to_two_seats() {

        //given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();
        Reservation reservation = reservationService.create(new CreateUsingCinemaHallCommand("RES123", cinemaHallId));

        //when
        var command = new BookCommand(Arrays.asList("A1", "A2"), null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/"  + reservation.getReservationNumber() + "/book",
                token,
                command,
                BookCommand.class
        );

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void user_should_not_be_able_to_book_more_than_two_seats() {

        //given
        User user = TestUserFactory.createUser();
        userService.save(user);
        String token = getAccessTokenForUser(user);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();
        Reservation reservation = reservationService.create(new CreateUsingCinemaHallCommand("RES123", cinemaHallId));

        //when
        var command = new BookCommand(Arrays.asList("A1", "A2", "A3"), null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/"  + reservation.getReservationNumber() + "/book",
                token,
                command,
                BookCommand.class
        );

        //then
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());

    }


    @Test
    void user_should_be_able_to_release_seats() {

        //given
        User user = TestUserFactory.createUser();
        Integer userId = userService.save(user).getId();
        String token = getAccessTokenForUser(user);

        CinemaHall cinemaHall = TestCinemaHallFactory.createCinemaHall();
        Integer cinemaHallId = cinemaHallService.save(cinemaHall).getId();
        Reservation reservation = reservationService.create(new CreateUsingCinemaHallCommand("RES123", cinemaHallId));

        var command = new BookCommand(Arrays.asList("A1", "A2"), null);
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/"  + reservation.getReservationNumber() + "/book",
                token,
                command,
                BookCommand.class
        );

        //when
        var command1 = new ReleaseCommand(Arrays.asList("A1", "A2"), null);
        var response1 = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/reservations/" + reservation.getReservationNumber() + "/release",
                token,
                command1,
                ReleaseCommand.class
        );

        //then
        assertEquals(HttpStatus.OK, response1.getStatusCode());

    }


}