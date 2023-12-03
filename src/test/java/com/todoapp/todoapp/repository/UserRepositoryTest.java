package com.todoapp.todoapp.repository;

import com.todoapp.todoapp.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("유저 저장")
    void saveUser() {
        // given
        User user = new User("seok", "qwer");

        // when
        User saveUser = userRepository.save(user);

        // then
        assertNotNull(saveUser);
        assertEquals(1L, saveUser.getId());
        assertEquals("seok", saveUser.getUsername());
        assertEquals("qwer", saveUser.getPassword());

    }

    @Test
    @DisplayName("유저 이름 검색으로 가져오기")
    void findUsername() {
        // given
        User user = new User("seok", "qwer");

        userRepository.save(user);

        // when
        User findUser = userRepository.findByUsername("seok").orElseThrow();
        Exception exception = assertThrows(NoSuchElementException.class, () -> userRepository.findByUsername("jae").orElseThrow());

        // then
        assertEquals("seok", findUser.getUsername());
        assertNotNull(exception);

    }

}