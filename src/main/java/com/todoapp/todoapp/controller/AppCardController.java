package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.customException.uqualsException;
import com.todoapp.todoapp.dto.card.AllCardResponseDto;
import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.dto.card.SelectCardResponseDto;
import com.todoapp.todoapp.security.UserDetailsImpl;
import com.todoapp.todoapp.service.AppCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //회원 정보를 넘겨줘야하니 인증객체 AuthenticationPrincipal에 들어있는 UserDetailsImpl 가져오면된다
    @PostMapping("appcard")
    public SelectCardResponseDto createCard(@RequestBody CardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return appCardService.createCard(requestDto,userDetails.getUser());
    }

    @GetMapping("/appcard/{id}")
    public ResponseEntity<?> getIdCard(@PathVariable Long id){

        try {
            SelectCardResponseDto selectCardResponseDto = appCardService.getIdCard(id);
            return ResponseEntity.ok(selectCardResponseDto);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/appcard")
    public List<AllCardResponseDto> getCards(){
        return appCardService.getCards();
    }

    @PutMapping("/appcard/{id}")
    public ResponseEntity<?> updateCard(@PathVariable Long id, @RequestBody CardRequestDto requestDto , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            SelectCardResponseDto selectCardResponseDto = appCardService.updateCard(id,requestDto,userDetails.getUser());
            return ResponseEntity.ok(selectCardResponseDto);

        } catch (uqualsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("다른 유저의 앱카드 입니다.");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 앱카드 존재하지 않습니다.");
        }


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
