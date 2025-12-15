package br.com.banksecure.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ApoliceNaoEncontradaException.class,
            BemNaoEncontradoException.class,
            ClienteNaoEncontradoException.class,
            SeguroNaoEncontradoException.class,
    })
    public ResponseEntity<ApiErrorResponse> handleNotFound(RuntimeException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({
            BemPossuiSeguroException.class,
            ClientePossuiSegVidaException.class,
            IdadeInvalidaException.class,
            TipoIncompativelException.class
    })
    public ResponseEntity<ApiErrorResponse> handleBadRequest(RuntimeException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({
            CpfExistenteException.class,
            SeguroExistenteException.class,
            RegraApoliceException.class
    })
    public ResponseEntity<ApiErrorResponse> handleConflict(RuntimeException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler({
            LoginInvalidoException.class,
            UsernameInvalidoException.class
    })
    public ResponseEntity<ApiErrorResponse> handleAuthentication(RuntimeException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(AcessoNegadoException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDenied(AcessoNegadoException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarErrosDeValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(erro -> {
            String campo = ((FieldError) erro).getField();
            String mensagem = erro.getDefaultMessage();
            errors.put(campo, mensagem);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Erro de Validação");
        response.put("messages", errors);
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(Exception ex) {
        ex.printStackTrace();

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde."
        );
    }
}