package com.starwars.planetaswiki.exception.handler;

import com.starwars.planetaswiki.exception.ServiceException;
import com.starwars.planetaswiki.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ServiceException.class)
    protected ResponseEntity<Response> handlerServiceException(ServiceException serviceException, WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.comErro(serviceException.getMessage()));
    }

}
