package com.todoapp.todoapp.dto.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CardRequestDto {
    private String title;
    private String contents;

}
