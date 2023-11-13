package com.todoapp.todoapp.dto.comment;

import com.todoapp.todoapp.entity.Comments;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String name;
    private String comment;
    private LocalDateTime createAt;

    public CommentResponseDto(Comments comments) { //name은 나중 로그인 구현후 토큰을 통해서
        this.id = comments.getId();
        this.comment = comments.getComment();
        this.createAt = comments.getCreateAt();
    }
}
