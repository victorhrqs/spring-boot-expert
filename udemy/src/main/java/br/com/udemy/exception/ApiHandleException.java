package br.com.udemy.exception;

import br.com.udemy.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiHandleException {

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound (NotFoundException ex ) {
        return ErrorDTO.builder().error(ex.getMessage()).build();
    }
}
