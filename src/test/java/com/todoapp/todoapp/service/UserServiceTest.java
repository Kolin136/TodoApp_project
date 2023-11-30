package com.todoapp.todoapp.service;

import com.todoapp.todoapp.dto.user.SignupRequestDto;
import com.todoapp.todoapp.entity.User;
import com.todoapp.todoapp.repository.CardRepository;
import com.todoapp.todoapp.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;


    @Test
    @DisplayName("회원 가입")
    void signup(){
        // given
        SignupRequestDto signupRequestDto = new SignupRequestDto("kolin136","qwer136abc");

        given(userRepository.findByUsername("kolin136")).willReturn(Optional.empty());

        // when,then
        // 리턴 타입이 void라 오류가 없으면 정상적으로 처리
        assertDoesNotThrow(() -> userService.signup(signupRequestDto));

    }

    @Test
    @DisplayName("회원 가입 - 이미 username존재 하는 경우")
    void signup_user_duplication(){
        // given
        SignupRequestDto signupRequestDto = new SignupRequestDto("kolin136","qwer136abc");

        given(userRepository.findByUsername("kolin136")).willReturn(Optional.of(new User()));

        // when,then
        Exception exception = assertThrows(IllegalArgumentException.class ,() -> userService.signup(signupRequestDto));
        assertEquals("중복된 username 입니다.",exception.getMessage());

    }





}