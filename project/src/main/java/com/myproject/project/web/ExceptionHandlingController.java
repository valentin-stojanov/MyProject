package com.myproject.project.web;

import com.myproject.project.service.exceptions.ObjectNotFoundException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlingController {
    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler({JDBCConnectionException.class})
    public ModelAndView handleDBConnectException(JDBCConnectionException e){
        ModelAndView modelAndView = new ModelAndView("controller-error");
        modelAndView.addObject("exception", "Database connection is not available.");

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ObjectNotFoundException.class})
    public ModelAndView handleObjectNotFoundException(ObjectNotFoundException onfe){
        ModelAndView modelAndView = new ModelAndView("controller-error");
        modelAndView.addObject("exception", onfe.getMessage());

        return modelAndView;
    }


}
