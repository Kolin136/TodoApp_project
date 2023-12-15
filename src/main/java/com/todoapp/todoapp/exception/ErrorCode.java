package com.todoapp.todoapp.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
  // 공통
  INVALID_INPUT_EXCEPTION(400,"잘못된 입력 입니다!,앱카드 완료 처리 하신다면 1 입력해주세요"),
  NOT_USER_OWNED_POST_EXCEPTION(403,"해당글은 해당 유저의 글이 아닙니다."),
  DAILY_REQUEST_LIMIT_EXCEEDED(429,"일일 사용 횟수 초과 했습니다"),

  // 앱카드
  NOT_FOUND_APPCARD_EXCEPTION(400,"해당 앱카드는 없습니다"),

  // 앱댓글
  NOT_FOUND_APPCOMMENT_EXCEPTION(400,"해당 댓글은 없습니다"),

  // 회원
  ALREADY_EXIST_USER_NAME_EXCEPTION(409, "이미 존재하는 이름입니다."),
  NOT_FOUND_USER_EXCEPTION(400,"해당 유저는 없습니다.");

  private final int status;

  private  final String message;

  ErrorCode(int status,String message){
    this.status = status;
    this.message = message;
  }

}
