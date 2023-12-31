package com.todoapp.todoapp.dto.card;

import com.todoapp.todoapp.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AllCardResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createAt;
    private Integer finish;

    public AllCardResponseDto(Card card) {  //name은 나중 로그인 구현후 토큰을 통해서
        this.id = card.getId();
        this.username = card.getUser().getUsername();
        this.title = card.getTitle();
        this.contents = card.getContents();
        this.createAt = card.getCreateAt();
        this.finish = card.getFinish();
    }


}
