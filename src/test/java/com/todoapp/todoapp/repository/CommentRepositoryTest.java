package com.todoapp.todoapp.repository;

import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.entity.Comments;
import com.todoapp.todoapp.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("댓글 저장")
    void saveComment() {
        // given
        User user = new User("seok", "qwer");
        Card card = new Card(1L, "제목", "내용", 0, new User(), new ArrayList<>());
        Comments comment = new Comments(1L, "댓글", user, card);

        // when
        Comments saveComment = commentRepository.save(comment);

        // then
        assertNotNull(saveComment);
        assertEquals("댓글", saveComment.getComment());
        assertEquals("seok", saveComment.getUser().getUsername());

    }

}