package com.todoapp.todoapp.service;

import com.todoapp.todoapp.customException.InputException;
import com.todoapp.todoapp.customException.uqualsException;
import com.todoapp.todoapp.dto.card.AllCardResponseDto;
import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.dto.card.SelectCardResponseDto;
import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.entity.User;
import com.todoapp.todoapp.repository.CardRepository;
import com.todoapp.todoapp.security.UserDetailsImpl;
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
        cardRepository.save(card);
        SelectCardResponseDto cardResponseDto = new SelectCardResponseDto(card);

        return cardResponseDto;
    }

    @Transactional(readOnly = true)
    public SelectCardResponseDto getIdCard(Long id) {
        Card card = findCard(id);

        SelectCardResponseDto cardResponseDto = new SelectCardResponseDto(card);

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

        return new SelectCardResponseDto(card);
    }

    public void deleteCard(Long id, User user) throws uqualsException{
        Card card = findCard(id);

        if (!card.getUser().getUsername().equals(user.getUsername())){
            throw new uqualsException();
        }

        cardRepository.delete(card);

    }

    public void finishCheck(Long cardId, int checkNum, User user) throws uqualsException,InputException{
        Card card = findCard(cardId);

        if (!card.getUser().getUsername().equals(user.getUsername())){
            throw new uqualsException();
        }

        if (checkNum != 1){
            throw new InputException();
        }

        card.finishChange(1);

    }

    private Card findCard(Long id) {
        return cardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 앱카드는 없습니다")
        );
    }


}
