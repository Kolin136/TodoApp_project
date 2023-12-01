package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.customException.uqualsException;
import com.todoapp.todoapp.dto.comment.CommentRequestDto;
import com.todoapp.todoapp.dto.comment.CommentResponseDto;
import com.todoapp.todoapp.security.UserDetailsImpl;
import com.todoapp.todoapp.service.AppCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/todo")
@RestController
@RequiredArgsConstructor
public class AppcommentController {
    private final AppCommentService appCommentService;

    @PostMapping("/appcomment/{id}")
    public ResponseEntity<?> createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CommentResponseDto commentResponseDto = appCommentService.createComment(id, requestDto,userDetails.getUser());
            return ResponseEntity.ok(commentResponseDto);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/appcomment")
    public ResponseEntity<?> updateComment(@RequestParam ("cardid")Long cardId,
                                            @RequestParam ("commentid") Long commentId,
                                            @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CommentResponseDto commentResponseDto = appCommentService.updateComment(cardId,commentId ,requestDto, userDetails.getUser());
            return ResponseEntity.ok(commentResponseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/appcomment/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            appCommentService.deleteComment(id,userDetails.getUser());
            return ResponseEntity.ok().body(String.format("Pk %d번 댓글 삭제 완료", id));
        } catch (uqualsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
