package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.dto.card.AllCardResponseDto;
import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.dto.card.SelectCardResponseDto;
import com.todoapp.todoapp.service.AppCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/todo")
@RestController
public class AppCardController {

    private final AppCardService appCardService;

    public AppCardController(AppCardService appCardService) {
        this.appCardService = appCardService;
    }


    @PostMapping("appcard")
    public SelectCardResponseDto createCard(@RequestBody CardRequestDto requestDto){
        return appCardService.createCard(requestDto);
    }

    @GetMapping("/appcard/{id}")
    public SelectCardResponseDto getIdCard(@PathVariable Long id){
        return appCardService.getIdCard(id);
    }

    @GetMapping("/appcard")
    public List<AllCardResponseDto> getCards(){
        return appCardService.getCards();
    }

    @PutMapping("/appcard/{id}")
    public SelectCardResponseDto updateCard(@PathVariable Long id, @RequestBody CardRequestDto requestDto ){
        return appCardService.updateCard(id,requestDto);
    }

    @DeleteMapping("/appcard/{id}")
    public void deleteCard(@PathVariable Long id){
       appCardService.deleteCard(id);
    }

    @GetMapping("/appcard/finish")
    private Integer finishCheck(@RequestParam int checkNum,Long id){
        return appCardService.finishCheck(checkNum,id);

    }


}
