package com.todoapp.todoapp.entity;

import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.dto.comment.CommentRequestDto;
import com.todoapp.todoapp.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentsTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("댓글 생성 필드 확인")
    void commentsTest() {
        // given
        CommentRequestDto requestDto = new CommentRequestDto("댓글");
        Card card = new Card(1L, "제목", "내용", 0, new User("seok", "qwer"), new ArrayList<>());

        // when
        Comments comments = new Comments(requestDto, card);
        card.commentsListAdd(comments);

        // then
        assertEquals("댓글", comments.getComment());
        assertEquals("seok", comments.getUser().getUsername());
        assertEquals(card.getCommentsList().get(0).getId(), comments.getId());  // 카드 입장에서 해당 댓글이 자기꺼 인지 확인

    }

    @Test
    @Order(2)
    @DisplayName("댓글 Null일시")
    void comments_commentIsNull() {
        // given
        User user = new User("seok", "qwer");
        Card card = new Card(1L, "제목", "내용", 0, user, new ArrayList<>());

        Comments comments = new Comments(1L, null, user, card);

        // when,then
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> commentRepository.save(comments));
        assertNotNull(exception);
    }

}