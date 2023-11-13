package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.dto.card.AllCardResponseDto;
import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.dto.card.SelectCardResponseDto;
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
    public SelectCardResponseDto createCard(@RequestBody CardRequestDto requestDto){
        return todoAppService.createCard(requestDto);
    }

    @GetMapping("/appcard/{id}")
    public SelectCardResponseDto getIdCard(@PathVariable Long id){
        return todoAppService.getIdCard(id);
    }

    @GetMapping("/appcard")
    public List<AllCardResponseDto> getCards(){
        return todoAppService.getCards();
    }

    @PutMapping("/appcard/{id}")
    public SelectCardResponseDto updateCard(@PathVariable Long id, @RequestBody CardRequestDto requestDto ){
        return todoAppService.updateCard(id,requestDto);
    }

    @DeleteMapping("/appcard/{id}")
    public void deleteCard(@PathVariable Long id){
       todoAppService.deleteCard(id);
    }





}
