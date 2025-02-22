package web.forum.topichub.aspect;

import lombok.*;
import org.springframework.dao.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import web.forum.topichub.config.i18n.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.exceptions.*;

import java.time.*;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionControllerAdvice {
    private final I18nUtil i18nUtil;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> exceptionEntityNotFound(EntityNotFoundException e, WebRequest request) {
        return  createResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?>  handleIllegalArgumentException(BadRequestException e, WebRequest request) {
        return  createResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                request);
    }

        @ExceptionHandler(EntityAlreadyExists.class)
    public ResponseEntity<?>  handleIllegalArgumentException(EntityAlreadyExists e, WebRequest request) {
            return  createResponse(
                    e.getMessage(),
                    HttpStatus.CONFLICT,
                    request);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
     public ResponseEntity<?>  handleIllegalArgumentException(DataIntegrityViolationException e, WebRequest request) {
        return  createResponse(
                ErrorKey.UNIQUE.name(),
                HttpStatus.BAD_REQUEST,
                request);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?>  handleIllegalArgumentException(MethodArgumentNotValidException e, WebRequest request) {
        return  createResponse(
                ErrorKey.WRONG_REQUEST_PARAM.name(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    private ResponseEntity<ErrorDto> createResponse(String errorKey, HttpStatus httpStatus,WebRequest request ){
        return new ResponseEntity<>(createErrorDto(
               " i18nUtil.getMessage(errorKey, request, null)",
                httpStatus
        ), httpStatus);
    }

    private ErrorDto createErrorDto(String message, HttpStatus httpStatus){
        return ErrorDto.builder()
                .code(httpStatus.value())
                .message(message)
                .localDate(LocalDate.now().toString())
                .build();
    }
}
