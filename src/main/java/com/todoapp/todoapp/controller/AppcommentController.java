package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.dto.comment.CommentRequestDto;
import com.todoapp.todoapp.dto.comment.CommentResponseDto;
import com.todoapp.todoapp.service.AppCommentService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/todo")
@RestController
public class AppcommentController {
    private final AppCommentService appCommentService;

    public AppcommentController(AppCommentService appCommentService) {
        this.appCommentService = appCommentService;
    }


    @PostMapping("/appcomment")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto) {
        return appCommentService.createComment(requestDto);
    }

    @PutMapping("/appcomment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return appCommentService.updateComment(id, requestDto);
    }

    @DeleteMapping("/appcomment/{id}")
    public void deleteCard(@PathVariable Long id) {
        appCommentService.deleteComment(id);
    }


}
