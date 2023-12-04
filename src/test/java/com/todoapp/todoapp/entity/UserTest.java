package com.todoapp.todoapp.entity;

import com.todoapp.todoapp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class UserTest {

    @Autowired
    UserRepository userRepository;

    @Nested
    @DisplayName("유저 Entity 모두 한번에 테스트")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class AllUserEntityTest {

        @Test
        @Order(1)
        @DisplayName("유저 생성 필드 확인")
        void userTest() {
            // given
            User user = new User("seok", "qwer");

            // when
            userRepository.save(user);

            // then
            assertEquals("seok", user.getUsername());
            assertEquals("qwer", user.getPassword());

        }

        @Test
        @Order(2)
        @DisplayName("유저 이름 Null,중복 일시")
        void user_NameIsNull_NameDuplication() {
            // given
            User user = new User("seok", "qwer");
            User userNameNull = new User(null, "qwer");
            User userNameDuplication = new User("seok", "aaaa");

            userRepository.save(user);

            //when,then
            Exception NameNullException = assertThrows(DataIntegrityViolationException.class,
                    () -> userRepository.save(userNameNull)
            );

            Exception NameDuplicationException = assertThrows(DataIntegrityViolationException.class,
                    () -> userRepository.save(userNameDuplication)
            );

            assertNotNull(NameNullException);
            assertNotNull(NameDuplicationException);
        }

        @Test
        @Order(3)
        @DisplayName("유저 비밀번호 Null일시")
        void user_PasswordIsNull() {
            // given
            User user = new User("seok", null);

            // when,then
            Exception exception = assertThrows(DataIntegrityViolationException.class,
                    () -> userRepository.save(user)
            );

            assertNotNull(exception);

        }
    }
}