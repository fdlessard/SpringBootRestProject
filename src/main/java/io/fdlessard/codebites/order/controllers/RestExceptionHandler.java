package io.fdlessard.codebites.order.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

/**
 * Created by fdlessard on 16-10-11.
 */
@ControllerAdvice
@RestController
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler .class);

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseBody
    public ResponseEntity<SalesOrderError> orderNotFound(OrderNotFoundException e) {

        long orderId = e.getOrderId();

        SalesOrderError error = new SalesOrderError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.value(), "Order", "Order " + orderId + " not found !", null);

        return new ResponseEntity<SalesOrderError>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public ResponseEntity<SalesOrderError> orderNotFound(NumberFormatException e) {

        SalesOrderError error = new SalesOrderError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.value(), "Order", "Order not found !", null);

        return new ResponseEntity<SalesOrderError>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<SalesOrderError> orderAmountIsInvalid(ConstraintViolationException e) {

        SalesOrderError error = new SalesOrderError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "Order", "Amount invalid !", null);

        return new ResponseEntity<SalesOrderError>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<SalesOrderError> orderAlreadyExist(Exception e) {

        SalesOrderError error = new SalesOrderError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.value(), "Order", "Order not found !", null);

        return new ResponseEntity<SalesOrderError>(error, HttpStatus.NOT_FOUND);
    }


}

