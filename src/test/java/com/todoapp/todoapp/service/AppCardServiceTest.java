package com.todoapp.todoapp.service;

import com.todoapp.todoapp.oldCustomException.InputException;
import com.todoapp.todoapp.oldCustomException.uqualsException;
import com.todoapp.todoapp.dto.card.AllCardResponseDto;
import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.dto.card.SelectCardResponseDto;
import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.entity.User;
import com.todoapp.todoapp.repository.CardRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class AppCardServiceTest {
    @Mock
    CardRepository cardRepository;
    @InjectMocks
    AppCardService appCardService;

    public Card getcard() {
        return new Card(1L, "제목", "내용", 0, new User("seok", "qwer"), new ArrayList<>());
    }

    @Nested
    @DisplayName("할일 카드 서비스 모두 한번에 테스트")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class AllCardServiceTest {
        @Test
        @Order(1)
        @DisplayName("할일 카드 생성")
        void createCard() {
            // given
            CardRequestDto requestDto = CardRequestDto.builder().title("제목").contents("내용").build();
            User user = new User("seok", "qwer");
            Card card = new Card(requestDto, user);

            given(cardRepository.save(any())).willReturn(card);

            // when
            SelectCardResponseDto responseDto = appCardService.createCard(requestDto, user);

            // then
            assertEquals("제목", responseDto.getTitle());
            assertEquals("내용", responseDto.getContents());
            assertEquals("seok", responseDto.getUsername());

        }

        @Test
        @Order(2)
        @DisplayName("특정 할일 카드 조회")
        void getIdCard() {
            // given
            Long cardId = 1L;
            Card card = getcard();

            given(cardRepository.findById(cardId)).willReturn(Optional.of(card));

            // when
            SelectCardResponseDto responseDto = appCardService.getIdCard(cardId);

            // then
            assertEquals("제목", responseDto.getTitle());
            assertEquals("내용", responseDto.getContents());
            assertEquals("seok", responseDto.getUsername());

        }

        @Test
        @Order(3)
        @DisplayName("특정 할일 카드 없을때")
        void getId_NullCard() {
            // given
            Long cardId = 2L;

            given(cardRepository.findById(2L)).willReturn(Optional.empty());

            // when ,then
            Exception exception = assertThrows(IllegalArgumentException.class, () -> appCardService.getIdCard(cardId));
            assertEquals("해당 앱카드는 없습니다", exception.getMessage());

        }


        @Test
        @Order(4)
        @DisplayName("모든 할일 카드 조회")
        void getCards() {
            // given
            List<Card> checkCardList = new ArrayList<>(Arrays.asList(
                    new Card(1L, "제목1", "내용1", 0, new User(), new ArrayList<>()),
                    new Card(2L, "제목2", "내용2", 0, new User(), new ArrayList<>()),
                    new Card(3L, "제목3", "내용3", 0, new User(), new ArrayList<>())
            ));

            given(cardRepository.findAllByOrderByUserUsernameAscCreateAtDesc()).willReturn(checkCardList);

            // when
            List<AllCardResponseDto> responseDtos = appCardService.getCards();

            // then
            assertEquals(checkCardList.size(), responseDtos.size());
        }

        @Test
        @Order(5)
        @DisplayName("할일 카드 수정 - 회원 일치")
        void updateCard() throws uqualsException {
            // given
            Card card = getcard();

            CardRequestDto requestDto = CardRequestDto.builder().title("제목변경").contents("내용변경").build();

            given(cardRepository.findById(card.getId())).willReturn(Optional.of(card));

            // when
            SelectCardResponseDto responseDto = appCardService.updateCard(card.getId(), requestDto, card.getUser());

            // then
            assertEquals("제목변경", responseDto.getTitle());
            assertEquals("내용변경", responseDto.getContents());

        }

        @Test
        @Order(6)
        @DisplayName("할일 카드 수정 - 회원 불일치")
        void updateCard_UserInconsistency() {
            // given
            Card card = getcard();

            CardRequestDto requestDto = CardRequestDto.builder().title("제목변경").contents("내용변경").build();
            User user = new User("jae", "qwer");

            given(cardRepository.findById(card.getId())).willReturn(Optional.of(card));

            // when,then
            assertThrows(uqualsException.class, () -> appCardService.updateCard(card.getId(), requestDto, user));

        }

        @Test
        @Order(7)
        @DisplayName("할일 카드 완료 체크")
        void finishCheck() {
            // given
            Card card = getcard();

            given(cardRepository.findById(card.getId())).willReturn(Optional.of(card));

            // when,then
            // 리턴 타입이 void라 오류가 없으면 정상적으로 처리
            assertDoesNotThrow(() -> appCardService.finishCheck(card.getId(), 1, card.getUser()));

        }

        @Test
        @Order(8)
        @DisplayName("할일 카드 완료 체크 - input 오류")
        void finishCheck_Input_Exception() {
            // given
            Card card = getcard();

            given(cardRepository.findById(card.getId())).willReturn(Optional.of(card));

            // when,then

            Exception exception = assertThrows(InputException.class, () -> appCardService.finishCheck(card.getId(), 5, card.getUser()));
            assertEquals("잘못된 입력입니다!,앱카드 완료 처리 하신다면 1 입력해주세요", exception.getMessage());

        }
    }

}