package pl.lodz.p.cinema_management.reservation.api;

import pl.lodz.p.cinema_management.reservation.command.domain.*;
import pl.lodz.p.cinema_management.reservation.query.facade.ReservationDtoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
class ReservationCustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ReservationDtoNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleReservationDtoNotFoundException(ReservationDtoNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleReservationNotFoundException(ReservationNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SeatNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleSeatNotFoundException(SeatNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SeatAlreadyTakenException.class)
    public final ResponseEntity<ErrorResponse> handleSeatAlreadyTakenException(SeatAlreadyTakenException ex) {
        return buildResponse(ex,  HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ReservationAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleReservationAlreadyExistsException(ReservationAlreadyExistsException ex) {
        return buildResponse(ex,  HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public final ResponseEntity<ErrorResponse> handleMethodNotAllowedException(MethodNotAllowedException ex) {
        return buildResponse(ex,  HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(IOException.class)
    public final ResponseEntity<ErrorResponse> handleCommandNotSupportedException(IOException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ex.getMessage()));
    }

    private <E extends RuntimeException> ResponseEntity<ErrorResponse> buildResponse(E exception, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(exception.getMessage()));
    }

}