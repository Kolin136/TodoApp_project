package com.todoapp.todoapp.service;

import com.todoapp.todoapp.customException.uqualsException;
import com.todoapp.todoapp.dto.comment.CommentRequestDto;
import com.todoapp.todoapp.dto.comment.CommentResponseDto;
import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.entity.Comments;
import com.todoapp.todoapp.entity.User;
import com.todoapp.todoapp.repository.CardRepository;
import com.todoapp.todoapp.repository.CommentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AppCommentServiceTest {
    @Mock
    CardRepository cardRepository;
    @Mock
    CommentRepository commentRepository;
    @InjectMocks
    AppCommentService appCommentService;

    Card card;
    User user;

    @BeforeEach
    void setUp() {
        card = new Card(1L, "제목", "내용", 0, new User("seok", "qwer"), new ArrayList<>());
        user = new User("seok", "qwer");
    }

    @Nested
    @DisplayName("댓글 서비스 모두 한번에 테스트")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class AllCommentServiceTest {

        @Test
        @Order(1)
        @DisplayName("댓글 생성")
        void createComment() {
            // given
            CommentRequestDto commentRequestDto = new CommentRequestDto("댓글");

            given(cardRepository.findById(card.getId())).willReturn(Optional.of(card));

            // when
            CommentResponseDto responseDto = appCommentService.createComment(card.getId(), commentRequestDto, user);

            // then
            verify(commentRepository, times(1)).save(any());  // commentRepository save 가 한번만 호출했는지
            assertEquals("seok", responseDto.getUsername());
            assertEquals("댓글", responseDto.getComment());

        }

        @Test
        @Order(2)
        @DisplayName("댓글 생성중 댓글달 할일 카드 없을때")
        void createComment_NullCard() {
            // given
            CommentRequestDto requestDto = new CommentRequestDto("댓글");

            given(cardRepository.findById(2L)).willReturn(Optional.empty());

            // when,then
            Exception exception = assertThrows(IllegalArgumentException.class, () -> appCommentService.createComment(2L, requestDto, user));
            assertEquals("해당 앱카드는 없습니다", exception.getMessage());

        }

        @Test
        @Order(3)
        @DisplayName("댓글 수정")
        void updateComment() throws uqualsException {
            // given
            CommentRequestDto requestDto = new CommentRequestDto("댓글 수정");
            Comments comments = new Comments(1L, "댓글", user, card);

            given(commentRepository.findById(1L)).willReturn(Optional.of(comments));

            // when,then
            CommentResponseDto responseDto = appCommentService.updateComment(1L, 1L, requestDto, user);

            assertEquals("댓글 수정", responseDto.getComment());
            assertEquals("seok", responseDto.getUsername());

        }

        @Test
        @Order(4)
        @DisplayName("댓글 수정 - 해당 댓글 없을때")
        void updateComment_NullComment() {
            // given
            CommentRequestDto requestDto = new CommentRequestDto("댓글 수정");

            given(commentRepository.findById(2L)).willReturn(Optional.empty());

            // when,then
            Exception exception = assertThrows(IllegalArgumentException.class, () -> appCommentService.updateComment(1L, 2L, requestDto, user));
            assertEquals("해당 댓글은 없습니다", exception.getMessage());
        }

        @Test
        @Order(5)
        @DisplayName("댓글 수정 - 댓글 주인 유저 불일치")
        void updateComment_UserInconsistency() {
            // given
            CommentRequestDto requestDto = new CommentRequestDto("댓글 수정");
            Comments comments = new Comments(1L, "댓글", new User("jae", "qwer"), card);

            given(commentRepository.findById(1L)).willReturn(Optional.of(comments));

            // when,then
            Exception exception = assertThrows(uqualsException.class, () -> appCommentService.updateComment(1L, 1L, requestDto, user));
            assertEquals("해당글은 해당유저의 글이 아닙니다.", exception.getMessage());

        }

        @Test
        @Order(6)
        @DisplayName("댓글 삭제")
        void deleteComment() throws uqualsException {
            // given
            Comments comments = new Comments(1L, "댓글", user, card);

            given(commentRepository.findById(1L)).willReturn(Optional.of(comments));

            // when,then
            appCommentService.deleteComment(1L, user);
            verify(commentRepository, times(1)).delete(any());
        }
    }

}