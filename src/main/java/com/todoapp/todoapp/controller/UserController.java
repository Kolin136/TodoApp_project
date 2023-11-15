package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.dto.user.SignupRequestDto;
import com.todoapp.todoapp.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/todo")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //지금은 리턴쪽 보면 서로 반환이 달라서 ? 맞지만 정해져있을땐 명확하게 무엇을 리턴할건지 ResponseEntity<> 괄호안에 적는게 좋다
    @PostMapping("/signup")
    public ResponseEntity<?>  signup( @RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult) {


        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors());
        }

        userService.signup(requestDto);

        String message = "가입 성공";
        //return ResponseEntity.status(HttpStatus.OK).body(message);  정답은 없지만 new 방식 아닐경우
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
