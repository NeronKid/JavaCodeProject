package tasktest.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorHandler extends Exception{
	private int codeError;
	
	ErrorHandler (int code, String message){
		super(message);
		this.codeError = code;
	}
}
