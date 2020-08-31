package ru.maxim.spring.mvc.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = NotfoundException.class)
    public String exception(NotfoundException exception) {
        return "error.jsp";
    }
}