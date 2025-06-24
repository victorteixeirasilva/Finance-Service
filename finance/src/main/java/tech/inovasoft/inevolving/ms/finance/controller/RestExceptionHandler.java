package tech.inovasoft.inevolving.ms.finance.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ExceptionResponse;
import tech.inovasoft.inevolving.ms.finance.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundFinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundTransactionException;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ExceptionResponse> handleDataBaseException(DataBaseException exception) {
        log.error("ERROR: {} - {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(
                        exception.getClass().getSimpleName(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(NotFoundTransactionException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundObjectivesByUser(
            NotFoundTransactionException exception
    ) {
        log.error("ERROR: {} - {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(
                        exception.getClass().getSimpleName(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(NotFoundFinancePlanning.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundObjectivesByUser(
            NotFoundFinancePlanning exception
    ) {
        log.error("ERROR: {} - {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(
                        exception.getClass().getSimpleName(),
                        exception.getMessage()
                ));
    }

}
