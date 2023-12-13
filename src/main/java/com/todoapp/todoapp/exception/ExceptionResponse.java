package com.todoapp.todoapp.exception;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@Builder
public class ExceptionResponse {

  private int status;

  private List<String> errorMessages;

  public static ExceptionResponse of(HttpStatus status, List<String> errorMessages) {
    return ExceptionResponse.builder()
        .status(status.value())
        .errorMessages(errorMessages)
        .build();
  }

  public static ExceptionResponse of(HttpStatus status, String errorMessage) {
    List<String> errorMessages = List.of(errorMessage);

    return ExceptionResponse.builder()
        .status(status.value())
        .errorMessages(errorMessages)
        .build();
  }

}
