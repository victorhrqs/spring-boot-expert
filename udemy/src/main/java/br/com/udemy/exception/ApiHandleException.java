package br.com.udemy.exception;

import br.com.udemy.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiHandleException {

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound (NotFoundException ex ) {
        return ErrorDTO.builder().erros(Arrays.asList(ex.getMessage())).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleMethodNotValidException (MethodArgumentNotValidException ex) {
        List<String> errosList = ex.getBindingResult().getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
        return ErrorDTO.builder().erros(errosList).build();
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleUserOrPasswordInvalidException (ResponseStatusException ex) {

        List<String> errorList = new ArrayList<>();

        if (ex != null) errorList.add(ex.getMessage());

        return ErrorDTO.builder().erros(errorList).build();
    }
}
