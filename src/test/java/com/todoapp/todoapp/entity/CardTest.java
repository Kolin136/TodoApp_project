package com.todoapp.todoapp.entity;

import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.repository.CardRepository;
import jakarta.validation.constraints.Null;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CardTest {

    @Autowired
    CardRepository cardRepository;

    @Nested
    @DisplayName("할일 카드 Entity 모두 한번에 테스트")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class AllCardEntityTest {

        @Test
        @Order(1)
        @DisplayName("할일 카드 DTO-Entity변환 확인")
        void cardTest() {
            // given
            CardRequestDto requestDto = new CardRequestDto("제목", "내용");
            User user = new User("seok", "qwer");

            //when
            Card card = new Card(requestDto, user);

            // then
            assertEquals("제목", card.getTitle());
            assertEquals("내용", card.getContents());
            assertEquals("seok", card.getUser().getUsername());

        }

        @Test
        @Order(2)
        @DisplayName("할일 카드 제목 Null일시")
        void card_TitleIsNull() {
            // given
            CardRequestDto requestDto = new CardRequestDto(null, "내용");
            User user = new User("seok", "qwer");
            Card card = new Card(requestDto, user);

            // when,then
            Exception exception = assertThrows(DataIntegrityViolationException.class, () -> cardRepository.save(card));
            assertNotNull(exception);

        }

        @Test
        @Order(3)
        @DisplayName("할일 카드 내용 Null일시")
        void card_ContentsIsNull() {
            // given
            CardRequestDto requestDto = new CardRequestDto("제목", null);
            User user = new User("seok", "qwer");
            Card card = new Card(requestDto, user);

            // when,then
            Exception exception = assertThrows(DataIntegrityViolationException.class, () -> cardRepository.save(card));
            assertNotNull(exception);

        }
    }
}