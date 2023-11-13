package com.todoapp.todoapp.service;

import com.todoapp.todoapp.dto.CardRequestDto;
import com.todoapp.todoapp.dto.CardResponseDto;
import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.repository.TodoAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TodoAppService {

    private final TodoAppRepository todoAppRepository;

    public CardResponseDto createCard(CardRequestDto requestDto) {
        CardResponseDto cardResponseDto = new CardResponseDto(new Card(requestDto));

        return cardResponseDto;
    }

    @Transactional(readOnly = true)
    public CardResponseDto getIdCard(Long id) {

        CardResponseDto cardResponseDto = new CardResponseDto(findCard(id));

        return cardResponseDto;
    }

    @Transactional(readOnly = true)
    public List<CardResponseDto> getCards() {
        return todoAppRepository.findAllByOrderByCreateAtDesc()
                .stream().map(CardResponseDto::new).toList();
    }

    public CardResponseDto updateCard(Long id, CardRequestDto requestDto) {

        Card card = findCard(id);
        card.update(requestDto);

        return new CardResponseDto(card);
    }

    public void deleteCard(Long id) {
        Card card = findCard(id);
        todoAppRepository.delete(card);
    }

    private Card findCard(Long id) {
        return todoAppRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 카드는 없습니다")
        );
    }


}
