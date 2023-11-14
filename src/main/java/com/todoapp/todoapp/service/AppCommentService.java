package com.todoapp.todoapp.service;

import com.todoapp.todoapp.dto.comment.CommentRequestDto;
import com.todoapp.todoapp.dto.comment.CommentResponseDto;
import com.todoapp.todoapp.entity.Comments;
import com.todoapp.todoapp.repository.CardRepository;
import com.todoapp.todoapp.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class AppCommentService {
    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        Comments comments = new Comments(requestDto);

        CommentResponseDto commentResponseDto = new CommentResponseDto(commentRepository.save(comments));

        return commentResponseDto;
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto) {

        Comments comments = findComment(id);
        comments.update(requestDto);

        return new CommentResponseDto(comments);
    }

    public void deleteComment(Long id) {
        Comments comments = findComment(id);
        commentRepository.delete(comments);
    }



    private Comments findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글은 없습니다")
        );
    }



}
