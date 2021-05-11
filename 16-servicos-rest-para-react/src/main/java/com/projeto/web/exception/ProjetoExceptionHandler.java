package com.projeto.web.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.projeto.models.service.exception.EmailCadastradoException;

@ControllerAdvice
public class ProjetoExceptionHandler extends ResponseEntityExceptionHandler {
		
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handlerException(Exception ex, WebRequest request){
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.SERVICO_EMAIL;
		String detail = ex.getMessage();
		
		Problem problem = createBuilderProblem(status, problemType, detail)
				.addUserMessage("Erro na digitação do email ")
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		
	}
	
	
	
	@ExceptionHandler(EmailCadastradoException.class)
	public ResponseEntity<?> handlerEmailException(EmailCadastradoException ex, WebRequest request){
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.SERVICO_EMAIL;
		String detail = ex.getMessage();
		
		Problem problem = createBuilderProblem(status, problemType, detail)
				.addUserMessage("Erro na digitação do email ")
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		
	}

	private ResponseEntity<Object> handleExceptionInternal(EmailCadastradoException ex, Object body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		if (body == null ) {
			
			body = Problem.builder()
					      .addTimeStamp(LocalDateTime.now())
					      .addStatus(status.value())
					      .addTitle(status.getReasonPhrase())
					      .addUserMessage("Ocorreu um erro interno inesperado "
					      		+ "no sistema. Tente novamente mais tarde e se o problema persistir, "
					      		+ "entre em contato com o administrador").build();
		} else if ( body instanceof String) {
			body = Problem.builder()
				      .addTimeStamp(LocalDateTime.now())
				      .addStatus(status.value())
				      .addTitle(status.getReasonPhrase())
				      .addUserMessage("Ocorreu um erro interno inesperado "
				      		+ "no sistema. Tente novamente mais tarde e se o problema persistir, "
				      		+ "entre em contato com o administrador").build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.Builder createBuilderProblem(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder()
					  .addTimeStamp(LocalDateTime.now())
					  .addStatus(status.value())
					  .addType(problemType.getUri())
					  .addTitle(problemType.getTitle())
					  .addDetail(detail);
	}
	
	
	

}
