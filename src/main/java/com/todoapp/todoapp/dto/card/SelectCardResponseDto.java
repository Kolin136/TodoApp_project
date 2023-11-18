package com.todoapp.todoapp.dto.card;

import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SelectCardResponseDto {

    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createAt;

    public SelectCardResponseDto(Card card, String name) {
        this.id = card.getId();
        this.username = name;
        this.title = card.getTitle();
        this.contents = card.getContents();
        this.createAt = card.getCreateAt();
    }


}
