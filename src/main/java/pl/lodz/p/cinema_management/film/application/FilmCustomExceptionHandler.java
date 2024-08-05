package pl.lodz.p.cinema_management.film.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.lodz.p.cinema_management.film.domain.FilmAlreadyExistsException;
import pl.lodz.p.cinema_management.film.domain.FilmNotFoundException;

import java.io.IOException;

@ControllerAdvice
class FilmCustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FilmNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleFilmNotFoundException(FilmNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FilmAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleFilmAlreadyExistsException(FilmAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
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