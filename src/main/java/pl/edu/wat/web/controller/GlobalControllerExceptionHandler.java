package pl.edu.wat.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.wat.exception.NotFoundException;

@ControllerAdvice
class GlobalControllerExceptionHandler {
    //TODO obsluga 404 , na koniec odkomentować
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NotFoundException.class)
//    public String handleConflict() {
//        return "visit/notFound";
//    }
}
