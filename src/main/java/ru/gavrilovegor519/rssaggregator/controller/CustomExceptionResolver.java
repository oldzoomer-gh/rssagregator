package ru.gavrilovegor519.rssaggregator.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gavrilovegor519.rssaggregator.dto.output.Response;
import ru.gavrilovegor519.rssaggregator.exception.*;

@ControllerAdvice
@Log4j2
public class CustomExceptionResolver {

    @ExceptionHandler(IncorrectInputDataException.class)
    public ResponseEntity<Response> badRequestHandler(Throwable e) {
        log.error(e.getMessage());
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GetFeedException.class)
    public ResponseEntity<String> getFeedError(Throwable e) {
        return ResponseEntity.ok(e.getMessage());
    }

    @ExceptionHandler({DuplicateUserException.class, DuplicateFeedException.class})
    public ResponseEntity<Response> conflictHandler(Throwable e) {
        log.error(e.getMessage());
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({IncorrectPasswordException.class, UserNotFoundException.class})
    public ResponseEntity<Response> forbiddenHandler(Throwable e) {
        log.error(e.getMessage());
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Response> otherHandler(Throwable e) {
        log.error(e.getMessage());
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
