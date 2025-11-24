package br.com.banksecure.app.exception;

import br.com.banksecure.app.enums.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginInvalidoException.class)
    public ResponseEntity<String> handleLoginInvalidoException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorMessage.LOGIN_INVALIDO.getMessage());
    }


    @ExceptionHandler(IdadeInvalidaException.class)
    public ResponseEntity<String> handleIdadeInvalidaException(IdadeInvalidaException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(SeguroNaoEncontradoException.class)
    public ResponseEntity<String> handleSeguroNaoEncontradoException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage.SEGURO_NAO_ENCONTRADO.getMessage());
    }

    @ExceptionHandler(UsernameInvalidoException.class)
    public ResponseEntity<String> handleUsernameInvalidoException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorMessage.USERNAME_INVALIDO.getMessage());
    }

    @ExceptionHandler(CpfExistenteException.class)
    public ResponseEntity<String> handleCpfExistenteException(){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessage.CPF_JA_EXISTE.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarErrosDeValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(erro -> {
            String campo = ((FieldError) erro).getField();
            String mensagem = erro.getDefaultMessage();
            erros.put(campo, mensagem);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }
}
