package com.todoapp.todoapp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.todoapp.entity.User;
import com.todoapp.todoapp.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//컨트롤러 테스트 할때 사용하는 어노테이션
@WebMvcTest
public class SettingMvc {

    protected  MockMvc mvc; // 가짜 http요청을 날리는용

    protected  Principal mockPrincipal;  //가짜 인증용

    @Autowired
    protected  WebApplicationContext context;

    @Autowired
    protected  ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context) // MockMvcBuilders는 머 설정하고 할 수 있는거다
                .apply(springSecurity(new MockSecurityFilter()))  //테스트에서 동작하게할 가짜 시큐리티 필터 넣기(시큐리티 필터 작동할때 이 가짜 필터로 작동)
                .alwaysDo(print())
                .build();
    }

    protected  void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "seok";
        String password = "qwer";
        User testUser = new User(username, password );
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }



}
