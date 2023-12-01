package com.todoapp.todoapp.dto.card;

import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SelectCardResponseDto {

    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createAt;

    public SelectCardResponseDto(Card card) {
        this.id = card.getId();
        this.username = card.getUser().getUsername();
        this.title = card.getTitle();
        this.contents = card.getContents();
        this.createAt = card.getCreateAt();
    }


}
