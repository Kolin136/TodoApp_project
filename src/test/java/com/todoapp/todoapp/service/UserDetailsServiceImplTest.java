package com.todoapp.todoapp.service;

import com.todoapp.todoapp.entity.User;
import com.todoapp.todoapp.repository.UserRepository;
import com.todoapp.todoapp.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    @DisplayName("DB에서 유저 찾기")
    void loadUserByUsername() {
        // given
        String username = "seok";

        given(userRepository.findByUsername(username)).willReturn(Optional.of(new User(username, "qwer")));

        // when
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // then
        assertEquals("seok", userDetails.getUsername());

    }

    @Test
    @DisplayName("DB에서 유저 찾기 - 없는 경우")
    void loadUserByUsername_null() {
        // given
        String username = "seok";

        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        // when,then
        Exception exception = assertThrows(UsernameNotFoundException.class, () ->
                userDetailsService.loadUserByUsername(username));

        assertEquals(username +"유저는 찾을 수 없습니다.",exception.getMessage());

    }

}