package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.dto.comment.CommentRequestDto;
import com.todoapp.todoapp.dto.comment.CommentResponseDto;
import com.todoapp.todoapp.security.UserDetailsImpl;
import com.todoapp.todoapp.service.AppCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/todo")
@RestController
@RequiredArgsConstructor
public class AppcommentController {

  private final AppCommentService appCommentService;

  @PostMapping("/appcomment/{id}")
  public ResponseEntity<?> createComment(@PathVariable Long id,
      @RequestBody CommentRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    CommentResponseDto commentResponseDto = appCommentService.createComment(id, requestDto,
        userDetails.getUser());
    return ResponseEntity.ok(commentResponseDto);

  }

  @PutMapping("/appcomment")
  public ResponseEntity<?> updateComment(@RequestParam("cardid") Long cardId,
      @RequestParam("commentid") Long commentId,
      @RequestBody CommentRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    CommentResponseDto commentResponseDto = appCommentService.updateComment(cardId, commentId,
        requestDto, userDetails.getUser());
    return ResponseEntity.ok(commentResponseDto);

  }

  @DeleteMapping("/appcomment/{id}")
  public ResponseEntity<?> deleteCard(@PathVariable Long id,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    appCommentService.deleteComment(id, userDetails.getUser());
    return ResponseEntity.ok().body(String.format("Pk %d번 댓글 삭제 완료", id));

  }

}
