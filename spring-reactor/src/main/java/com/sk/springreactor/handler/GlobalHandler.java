package com.sk.springreactor.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalHandler {


	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ErrorResponse> genericCustomException(WebRequest request, Exception ex) {
		return ResponseEntity.badRequest().body(new ErrorResponse("400", "Error"));
	}
	
	@InitBinder 
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
	
	/*
	@ModelAttribute
	public void addAccount(@RequestParam String number) {
	    Mono<Account> accountMono = accountRepository.findAccount(number);
	    model.addAttribute("account", accountMono);
	}	
	*/
	
	public static class ErrorResponse {
		private String code;
		private String message;

		public ErrorResponse() {
			super();
		}
		
		public ErrorResponse(String code, String message) {
			super();
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}

		       	
}
