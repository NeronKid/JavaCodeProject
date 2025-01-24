package tasktest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = WebServiceException.class)
	public ResponseEntity<String> handleWebServiceException(WebServiceException ex) {
		 return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getMessage());
	}
}
