package com.myproject.project.web.globalExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.PersistenceException;
import javax.transaction.TransactionalException;

@ControllerAdvice
public class GlobalDatabaseExceptionHandler {
    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler({TransactionalException.class, PersistenceException.class})
    public ModelAndView handleDBConnectException(){
        ModelAndView modelAndView = new ModelAndView("DB-error");
        modelAndView.addObject("exception", "DB error!");

        return modelAndView;
    }


}
