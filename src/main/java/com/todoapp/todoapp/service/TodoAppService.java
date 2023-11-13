package com.todoapp.todoapp.service;

import com.todoapp.todoapp.dto.CardRequestDto;
import com.todoapp.todoapp.dto.CardResponseDto;
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


    }

    public CardResponseDto getIdCard(Long id) {
    }

    public List<CardResponseDto> getCards() {
    }

    public CardResponseDto updateCard(Long id, CardRequestDto requestDto) {
    }

    public void deleteCard(Long id) {
    }
}
