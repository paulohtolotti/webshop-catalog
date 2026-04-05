package com.phtdev.webshopcatalog.resources.handlers;

import com.phtdev.webshopcatalog.dto.BaseErrorDTO;
import com.phtdev.webshopcatalog.dto.FieldErrorDTO;
import com.phtdev.webshopcatalog.dto.ValidationErrorDTO;
import com.phtdev.webshopcatalog.service.exceptions.ResourceDuplicatedException;
import com.phtdev.webshopcatalog.service.exceptions.ResourceNotRegistered;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotRegistered.class)
    public ResponseEntity<BaseErrorDTO> resourceNotRegisteredException(HttpServletRequest http,
                                                                       ResourceNotRegistered exc) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String path = http.getRequestURI();
        BaseErrorDTO errorDTO = new BaseErrorDTO(exc.getMessage(), status.value(), path, Instant.now());

        return ResponseEntity.status(status).body(errorDTO);
    }

    @ExceptionHandler(ResourceDuplicatedException.class)
    public ResponseEntity<BaseErrorDTO> resourceDuplicatedException(HttpServletRequest http,
                                                                    ResourceDuplicatedException exc) {
        HttpStatus status = HttpStatus.CONFLICT;
        String path = http.getRequestURI();
        BaseErrorDTO errorDTO = new BaseErrorDTO(exc.getMessage(), status.value(), path, Instant.now());
        return ResponseEntity.status(status).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<ValidationErrorDTO> dtoValidationExceptionHandler(HttpServletRequest http,
                                                                        MethodArgumentNotValidException exc) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;

        ValidationErrorDTO error = new ValidationErrorDTO("Validation error", status.value(), http.getRequestURI(),
                Instant.now());

        exc.getBindingResult().getFieldErrors().forEach(e -> error.addFieldError(e.getField(),
                e.getDefaultMessage()));

        return ResponseEntity.status(status).body(error);
    }
}
