package com.todoapp.todoapp.customException;

public class uqualsException extends Exception {
    public uqualsException(){
        super("해당글은 해당유저의 글이 아닙니다.");
    }
}
