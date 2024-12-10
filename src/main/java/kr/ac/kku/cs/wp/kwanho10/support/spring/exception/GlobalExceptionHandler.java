package kr.ac.kku.cs.wp.kwanho10.support.spring.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.ac.kku.cs.wp.kwanho10.tools.message.MessageException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // MessageException 처리
    @ExceptionHandler(MessageException.class)
    public ResponseEntity<String> handleMessageException(MessageException e) {
        //logger.debug(e.getMessage());
        System.out.println(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    }

    // MissingServletRequestParameterException 처리
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
    	//logger.error(e.getMessage(), e);
        System.out.println(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 기타 예외 처리
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleThrowable(Throwable e) {
    	//logger.error(e.getMessage(), e);
        System.out.println(e.getMessage());
        return new ResponseEntity<>("An error has occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
            "result", "error",
            "message", ex.getMessage()
        ));
    }

    // 유효성 검사 실패 (MethodArgumentNotValidException) 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    // 일반적인 Exception 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        ex.printStackTrace(); // 디버깅용으로 스택 트레이스 출력
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "result", "error",
            "message", "서버 내부 오류가 발생했습니다: " + ex.getMessage()
        ));
    }
    
    
    
    
    
    
    
    
    
}
