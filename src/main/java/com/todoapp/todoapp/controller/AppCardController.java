package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.dto.card.AllCardResponseDto;
import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.dto.card.SelectCardResponseDto;
import com.todoapp.todoapp.security.UserDetailsImpl;
import com.todoapp.todoapp.service.AppCardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/todo")
@RestController
public class AppCardController {

    private final AppCardService appCardService;

    public AppCardController(AppCardService appCardService) {
        this.appCardService = appCardService;
    }

    //회원 정보를 넘겨줘야하니 인증객체AuthenticationPrincipal에 들어있는 UserDetailsImpl 가져오면된다
    @PostMapping("appcard")
    public SelectCardResponseDto createCard(@RequestBody CardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return appCardService.createCard(requestDto,userDetails.getUser());
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

    //깃허브 체크

}
