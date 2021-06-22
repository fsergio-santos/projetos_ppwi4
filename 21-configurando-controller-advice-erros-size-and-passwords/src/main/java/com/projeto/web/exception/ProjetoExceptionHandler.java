package com.projeto.web.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.projeto.models.service.exception.ConfirmeSenhaNaoInformadoException;
import com.projeto.models.service.exception.EmailCadastradoException;
import com.projeto.models.service.exception.EmailUsuarioDeveSerInformadoException;
import com.projeto.models.service.exception.EntidadeNaoCadastradaException;
import com.projeto.models.service.exception.IdNaoPodeSerZeroNuloException;
import com.projeto.models.service.exception.InvalidJwtAuthenticationException;
import com.projeto.models.service.exception.SenhaDiferenteConfirmeSenhaException;
import com.projeto.models.service.exception.SenhaUsuarioDeveSerInformadaException;

@ControllerAdvice
public class ProjetoExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	

	
	@ExceptionHandler(SenhaDiferenteConfirmeSenhaException.class)
	public ResponseEntity<?> handlerSenhaDiferenteConfirmeSenhaException(
			SenhaDiferenteConfirmeSenhaException ex, 
				WebRequest request){
		
		HttpStatus status = HttpStatus.LENGTH_REQUIRED;
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = ex.getMessage();
		
		Problem problem = createBuilderProblem(status, problemType, detail)
				          .addUserMessage("Complete os dados conforme solicitado ") 
		                  .build(); 
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handlerConstraintViolationException(
			ConstraintViolationException ex, 
				WebRequest request){
		
		HttpStatus status = HttpStatus.LENGTH_REQUIRED;
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = "A quantidade de Caracteres estão inválidos!";
		
		Problem problem = createBuilderProblem(status, problemType, detail)
				          .addUserMessage("Complete os dados conforme solicitado ") 
		                  .build(); 
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(ConfirmeSenhaNaoInformadoException.class)
	public ResponseEntity<?> handlerConfirmeSenhaNaoInformadoException(
			ConfirmeSenhaNaoInformadoException ex, 
				WebRequest request){
		
		HttpStatus status = HttpStatus.LENGTH_REQUIRED;
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = ex.getMessage();
		
		Problem problem = createBuilderProblem(status, problemType, detail)
				          .addUserMessage("Complete os dados conforme solicitado ") 
		                  .build(); 
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
		
		
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handlerBadCredentialsException(
			BadCredentialsException ex, 
				WebRequest request){
		
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = ex.getMessage();
		
		Problem problem = createBuilderProblem(status, problemType, detail)
				          .addUserMessage("Senha informada está incorreta.") 
		                  .build(); 
		 return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<?> handlerUsernameNotFoundExeception(
			UsernameNotFoundException ex, 
				WebRequest request){
		
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = ex.getMessage();
		
		Problem problem = createBuilderProblem(status, problemType, detail)
				          .addUserMessage("Usuário solicitado não está cadastrado.") 
		                  .build(); 
		 return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(SenhaUsuarioDeveSerInformadaException.class)
	public ResponseEntity<?> handlerSenhaUsuarioDeveSerInformadoException(
			EmailUsuarioDeveSerInformadoException ex, 
				WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = ex.getMessage();
		
		Problem problem = createBuilderProblem(status, problemType, detail)
				          .addUserMessage("A senha deve ser informada!") 
		                  .build(); 
		 return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EmailUsuarioDeveSerInformadoException.class)
	public ResponseEntity<?> handlerEmailUsuarioDeveSerInformadoException(
			EmailUsuarioDeveSerInformadoException ex, 
				WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = ex.getMessage();
		
		Problem problem = createBuilderProblem(status, problemType, detail)
				          .addUserMessage("O e-mail deve ser informado!") 
		                  .build(); 
		 return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
		
	@ExceptionHandler(IdNaoPodeSerZeroNuloException.class)
	public ResponseEntity<?> handlerEntityNaoCadastrada(
			IdNaoPodeSerZeroNuloException ex, 
				WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		
		Problem problem = createBuilderProblem(status, problemType, detail)
				          .addUserMessage("O registro não foi localizado no sistema") 
		                  .build(); 
		 return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	
	@ExceptionHandler(EntidadeNaoCadastradaException.class)
	public ResponseEntity<?> handlerEntityNaoCadastrada(
			EntidadeNaoCadastradaException ex, 
				WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		
		Problem problem = createBuilderProblem(status, problemType, detail)
				          .addUserMessage("O registro não foi localizado no sistema") 
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
	
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {


		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		
		String detail = "Um ou mais campos estão inválidos. "
				+ " Faça o preenchimento correto e tente novamente";
		
		BindingResult bindingResult = ex.getBindingResult();
		
		List<Fields> fields = bindingResult.getFieldErrors()
									       .stream()
									       .map(fieldError -> {
												String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
												Fields field = new Fields();
												field.setNome(fieldError.getField());
												field.setUserMessage(message);
												return field;
									       	})
									       .collect(Collectors.toList());
		
		Problem problem = createBuilderProblem(status, problemType, detail)
				.addUserMessage(detail)
				.addListFields(fields)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(),  status, request);
	}
	
	

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
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
