package com.todoapp.todoapp.dto;

import com.todoapp.todoapp.entity.Card;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardResponseDto {

    private Long id;
    private String name;
    private String title;
    private String contents;
    private LocalDateTime createAt;

    public CardResponseDto(Card card) {  //name은 나중 로그인 구현후 토큰을 통해서
        this.id = card.getId();
        this.title = card.getTitle();
        this.contents = card.getContents();
        this.createAt = card.getCreateAt();
    }


}
