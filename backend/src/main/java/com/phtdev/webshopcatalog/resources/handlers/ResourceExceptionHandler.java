package com.phtdev.webshopcatalog.resources.handlers;

import com.phtdev.webshopcatalog.dto.BaseErrorDTO;
import com.phtdev.webshopcatalog.service.exceptions.ResourceNotRegistered;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotRegistered.class)
    public ResponseEntity<BaseErrorDTO> resourceNotRegisteredException(HttpServletRequest http, ResourceNotRegistered exc) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String path = http.getRequestURI();
        BaseErrorDTO errorDTO = new BaseErrorDTO(exc.getMessage(), status.value(), path, Instant.now());

        return ResponseEntity.status(status).body(errorDTO);
    }
}
