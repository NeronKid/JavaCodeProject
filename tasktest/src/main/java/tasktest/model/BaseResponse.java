package tasktest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class BaseResponse <T>{
	private int code = 200;
	private String message = "";
	private T data;
}
