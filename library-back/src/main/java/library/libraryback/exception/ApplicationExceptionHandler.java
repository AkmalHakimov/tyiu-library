package library.libraryback.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpEntity<?> BookNotFoundException(BookNotFoundException e) {
        e.printStackTrace();
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND,e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public HttpEntity<?> genericException(Exception e) {
//        e.printStackTrace();
//        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
//    }



    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<?> securityError(InternalAuthenticationServiceException e) {
        return ResponseEntity.status(401).body("Login yoki parol xato");
    }
}
