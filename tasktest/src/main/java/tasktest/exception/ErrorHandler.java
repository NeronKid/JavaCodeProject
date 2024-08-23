package tasktest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class ErrorHandler extends Exception{
	private int codeError;
	
	public ErrorHandler (int code, String message){
		super(message);
		this.codeError = code;
	}
}
