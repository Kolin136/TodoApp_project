package com.todoapp.todoapp.service;

import com.todoapp.todoapp.dto.card.AllCardResponseDto;
import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.dto.card.SelectCardResponseDto;
import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.entity.User;
import com.todoapp.todoapp.exception.BusinessException;
import com.todoapp.todoapp.exception.CustomException;
import com.todoapp.todoapp.exception.ErrorCode;
import com.todoapp.todoapp.repository.CardRepository;
import com.todoapp.todoapp.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AppCardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public SelectCardResponseDto createCard(CardRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
            new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION)
        );
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
    public Page<AllCardResponseDto> getCards(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        Sort sort = Sort.by(direction,sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Card> cardList = cardRepository.findAll(pageable);

        return cardList.map(AllCardResponseDto::new);
    }

    public SelectCardResponseDto updateCard(Long id, CardRequestDto requestDto, User user) {
        Card card = findCard(id);

        if (!card.getUser().getUsername().equals(user.getUsername())){
            throw new CustomException(ErrorCode.NOT_USER_OWNED_POST_EXCEPTION);
        }

        card.update(requestDto);

        return new SelectCardResponseDto(card);
    }

    public void deleteCard(Long id, User user) {
        Card card = findCard(id);

        if (!card.getUser().getUsername().equals(user.getUsername())){
            throw new CustomException(ErrorCode.NOT_USER_OWNED_POST_EXCEPTION);
        }

        cardRepository.delete(card);

    }

    public void finishCheck(Long cardId, int checkNum, User user) {
        Card card = findCard(cardId);

        if (!card.getUser().getUsername().equals(user.getUsername())){
            throw new CustomException(ErrorCode.NOT_USER_OWNED_POST_EXCEPTION);
        }

        if (checkNum != 1){
            throw new CustomException(ErrorCode.INVALID_INPUT_EXCEPTION);
        }

        card.finishChange(1);

    }

    private Card findCard(Long id) {
        return cardRepository.findById(id).orElseThrow(() ->
             new BusinessException(ErrorCode.NOT_FOUND_APPCARD_EXCEPTION)
        );
    }


}
