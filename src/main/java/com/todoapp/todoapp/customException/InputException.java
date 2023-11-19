package com.todoapp.todoapp.customException;

public class InputException extends Exception {
    public InputException() {
        super("잘못된 입력입니다!,앱카드 완료 처리 하신다면 1 입력해주세요");
    }
}
