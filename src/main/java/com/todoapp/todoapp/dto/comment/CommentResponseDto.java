package com.todoapp.todoapp.dto.comment;

import com.todoapp.todoapp.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String username;
    private String comment;
    private LocalDateTime createAt;

    public CommentResponseDto(Comments comments) { //name은 나중 로그인 구현후 토큰을 통해서
        this.id = comments.getId();
        this.username = comments.getUser().getUsername();
        this.comment = comments.getComment();
        this.createAt = comments.getCreateAt();
    }
}
