package com.todoapp.todoapp.service;

import com.todoapp.todoapp.dto.user.SignupRequestDto;
import com.todoapp.todoapp.entity.User;
import com.todoapp.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkUsername = userRepository.findByUsername(username);
        //isPresent()는 Optional메소드이고 현재 값이 존재하는지 안하는지 확인하는 메소드
        if(checkUsername.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }

        User user = new User(username,password);
        userRepository.save(user);

    }
}
