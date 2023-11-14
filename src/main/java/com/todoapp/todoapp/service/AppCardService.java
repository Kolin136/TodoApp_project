package com.todoapp.todoapp.service;

import com.todoapp.todoapp.dto.card.AllCardResponseDto;
import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.dto.card.SelectCardResponseDto;
import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AppCardService {

    private final CardRepository cardRepository;

    public SelectCardResponseDto createCard(CardRequestDto requestDto) {
        Card card = new Card(requestDto);

        SelectCardResponseDto cardResponseDto = new SelectCardResponseDto(cardRepository.save(card));

        return cardResponseDto;
    }

    @Transactional(readOnly = true)
    public SelectCardResponseDto getIdCard(Long id) {

        SelectCardResponseDto cardResponseDto = new SelectCardResponseDto(findCard(id));

        return cardResponseDto;
    }

    @Transactional(readOnly = true)
    public List<AllCardResponseDto> getCards() {
        return cardRepository.findAllByOrderByCreateAtDesc()
                .stream().map(AllCardResponseDto::new).toList();
    }

    public SelectCardResponseDto updateCard(Long id, CardRequestDto requestDto) {

        Card card = findCard(id);
        card.update(requestDto);

        return new SelectCardResponseDto(card);
    }

    public void deleteCard(Long id) {
        Card card = findCard(id);
        cardRepository.delete(card);
    }

    public Integer finishCheck(int checkNum, Long id) {
        Card card = findCard(id);
        card.setFinish(1);


        return checkNum;
    }

    private Card findCard(Long id) {
        return cardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 카드는 없습니다")
        );
    }


}
