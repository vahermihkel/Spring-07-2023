package ee.mihkel.webshop.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.UnknownContentTypeException;

import java.util.Date;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionMessage> handleException(HttpClientErrorException e) {
        String message = "Query to another application failed. Reason: " + e.getMessage();
        return getExceptionMessageResponseEntity(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionMessage> handleException(UnknownContentTypeException e) {
        String message = "Query replied with unknown content. Reason: " + e.getMessage();
        return getExceptionMessageResponseEntity(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionMessage> handleException(NoSuchElementException e) {
        String message = e.getMessage();
        return getExceptionMessageResponseEntity(HttpStatus.NOT_FOUND, message);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionMessage> handleException(NotEnoughInStockException e) {
        String message = e.getMessage();
        return getExceptionMessageResponseEntity(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionMessage> handleException(DataIntegrityViolationException e) {
        String message = e.getMessage();
        return getExceptionMessageResponseEntity(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionMessage> handleException(RuntimeException e) {
        String message = e.getMessage();
        return getExceptionMessageResponseEntity(HttpStatus.BAD_REQUEST, message);
    }

//     KÕIGE LÕPUS, KUI ON LIVE-i EELNE (kui on ülejäänud exceptionid kaetud)
//    @ExceptionHandler
//    public ResponseEntity<ExceptionMessage> handleException(Exception e) {
//        String message = "General exception. Reason: Reason: " + e.getMessage();
//        return getExceptionMessageResponseEntity(HttpStatus.BAD_REQUEST, message);
//    }

    private static ResponseEntity<ExceptionMessage> getExceptionMessageResponseEntity(HttpStatus status, String exceptionMessage) {
        ExceptionMessage message = new ExceptionMessage();
        message.setDate(new Date());
        message.setHttpStatusCode(status.value());
        message.setMessage(exceptionMessage);
        return new ResponseEntity<>(message, status);
    }
}
