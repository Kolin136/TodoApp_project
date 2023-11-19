package com.todoapp.todoapp.service;

import com.todoapp.todoapp.customException.uqualsException;
import com.todoapp.todoapp.dto.card.AllCardResponseDto;
import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.dto.card.SelectCardResponseDto;
import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.entity.User;
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

    public SelectCardResponseDto createCard(CardRequestDto requestDto, User user) {
        Card card = new Card(requestDto, user);
        user.cardListAdd(card);

        SelectCardResponseDto cardResponseDto = new SelectCardResponseDto(cardRepository.save(card), user.getUsername());

        return cardResponseDto;
    }

    @Transactional(readOnly = true)
    public SelectCardResponseDto getIdCard(Long id) {
        Card card = findCard(id);

        SelectCardResponseDto cardResponseDto = new SelectCardResponseDto(card, card.getUser().getUsername());

        return cardResponseDto;
    }

    @Transactional(readOnly = true)
    public List<AllCardResponseDto> getCards() {
        return cardRepository.findAllByOrderByUserUsernameAscCreateAtDesc()
                .stream().map(AllCardResponseDto::new).toList();
    }

    public SelectCardResponseDto updateCard(Long id, CardRequestDto requestDto, User user) throws uqualsException {
        Card card = findCard(id);

        if (!card.getUser().getUsername().equals(user.getUsername())){
            throw new uqualsException();
        }

        card.update(requestDto);

        return new SelectCardResponseDto(card, card.getUser().getUsername());
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
                new IllegalArgumentException("해당 앱카드는 없습니다")
        );
    }


}
