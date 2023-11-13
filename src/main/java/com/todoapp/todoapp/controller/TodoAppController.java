package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.dto.CardRequestDto;
import com.todoapp.todoapp.dto.CardResponseDto;
import com.todoapp.todoapp.service.TodoAppService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/doto")
@RestController
public class TodoAppController {

    private final TodoAppService todoAppService;

    public TodoAppController(TodoAppService todoAppService) {
        this.todoAppService = todoAppService;
    }


    @PostMapping("appcard")
    public CardResponseDto createCard(@RequestBody CardRequestDto requestDto){
        return todoAppService.createCard(requestDto);
    }

    @GetMapping("/appcard/{id}")
    public CardResponseDto getIdCard(@PathVariable Long id){
        return todoAppService.getIdCard(id);
    }

    @GetMapping("/appcard")
    public List<CardResponseDto> getCards(){
        return todoAppService.getCards();
    }

    @PutMapping("/appcard/{id}")
    public CardResponseDto updateCard(@PathVariable Long id, @RequestBody CardRequestDto requestDto ){
        return todoAppService.updateCard(id,requestDto);
    }

    @DeleteMapping("/appcard/{id}")
    public void deleteCard(@PathVariable Long id){
       todoAppService.deleteCard(id);
    }





}
