package com.todoapp.todoapp.service;

import com.todoapp.todoapp.dto.card.AllCardResponseDto;
import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.dto.card.SelectCardResponseDto;
import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.repository.TodoAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AppCardService {

    private final TodoAppRepository todoAppRepository;

    public SelectCardResponseDto createCard(CardRequestDto requestDto) {
        SelectCardResponseDto cardResponseDto = new SelectCardResponseDto(new Card(requestDto));

        return cardResponseDto;
    }

    @Transactional(readOnly = true)
    public SelectCardResponseDto getIdCard(Long id) {

        SelectCardResponseDto cardResponseDto = new SelectCardResponseDto(findCard(id));

        return cardResponseDto;
    }

    @Transactional(readOnly = true)
    public List<AllCardResponseDto> getCards() {
        return todoAppRepository.findAllByOrderByCreateAtDesc()
                .stream().map(AllCardResponseDto::new).toList();
    }

    public SelectCardResponseDto updateCard(Long id, CardRequestDto requestDto) {

        Card card = findCard(id);
        card.update(requestDto);

        return new SelectCardResponseDto(card);
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


    public Integer finishCheck(int checkNum, Long id) {
        Card card = findCard(id);
        card.setFinish(1);


        return checkNum;
    }
}
