package com.todoapp.todoapp.exception;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ExceptionResponse> handlerException(BusinessException e){

    // BusinessException에서 status를 int형으로 해서 HttpStatus에서 해당 int형 상태코드 가져온다
    HttpStatus httpStatus = HttpStatus.valueOf(e.getStatus());
    ExceptionResponse exceptionResponse = ExceptionResponse.of(httpStatus, List.of(e.getMessage()));

    return ResponseEntity.status(httpStatus).body(exceptionResponse);
  }

  @ExceptionHandler(CustomException.class)
  ResponseEntity<ExceptionResponse> handlerException(CustomException e){
    HttpStatus httpStatus = HttpStatus.valueOf(e.getStatus());
    ExceptionResponse exceptionResponse = ExceptionResponse.of(httpStatus, List.of(e.getMessage()));

    return ResponseEntity.status(httpStatus).body(exceptionResponse);
  }


  @ExceptionHandler(BindException.class)
  protected ResponseEntity<ExceptionResponse> handleBindException(BindException e) {


    BindingResult bindingResult = e.getBindingResult();

    List<String> errorMessages = bindingResult.getFieldErrors().stream()
        .map(FieldError::getDefaultMessage)
        .collect(Collectors.toList());

    ExceptionResponse errorResponse = ExceptionResponse.of(HttpStatus.BAD_REQUEST, errorMessages);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }


}
