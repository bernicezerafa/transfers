package transactions.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import transactions.exceptions.BadRequestException;
import transactions.models.errors.ErrorModel;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { BadRequestException.class })
    protected ResponseEntity<ErrorModel> handleBadRequest(final BadRequestException ex) {
        final HttpStatus statusCode = ex.getStatusCode();
        return new ResponseEntity<>(new ErrorModel(statusCode.name(), ex.getMessage()), statusCode);
    }
}

