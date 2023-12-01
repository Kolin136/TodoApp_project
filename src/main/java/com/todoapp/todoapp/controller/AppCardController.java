package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.customException.InputException;
import com.todoapp.todoapp.customException.uqualsException;
import com.todoapp.todoapp.dto.card.AllCardResponseDto;
import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.dto.card.SelectCardResponseDto;
import com.todoapp.todoapp.security.UserDetailsImpl;
import com.todoapp.todoapp.service.AppCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/todo")
@RestController
@RequiredArgsConstructor
public class AppCardController {

    private final AppCardService appCardService;

    //회원 정보를 넘겨줘야하니 인증객체 AuthenticationPrincipal에 들어있는 UserDetailsImpl 가져오면된다
    @PostMapping("/appcard")
    public ResponseEntity<SelectCardResponseDto> createCard(@RequestBody CardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        SelectCardResponseDto selectCardResponseDto = appCardService.createCard(requestDto, userDetails.getUser());

        return ResponseEntity.ok(selectCardResponseDto);
    }


    @GetMapping("/appcard/{id}")
    public ResponseEntity<?> getIdCard(@PathVariable Long id) {

        try {
            SelectCardResponseDto selectCardResponseDto = appCardService.getIdCard(id);
            return ResponseEntity.ok(selectCardResponseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/appcard")
    public ResponseEntity<List<AllCardResponseDto>> getCards() {

        List<AllCardResponseDto> allCardResponseDtoList = appCardService.getCards();

        return ResponseEntity.ok(allCardResponseDtoList);
    }


    @PutMapping("/appcard/{id}")
    public ResponseEntity<?> updateCard(@PathVariable Long id, @RequestBody CardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            SelectCardResponseDto selectCardResponseDto = appCardService.updateCard(id, requestDto, userDetails.getUser());
            return ResponseEntity.ok(selectCardResponseDto);

        } catch (uqualsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/appcard/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            appCardService.deleteCard(id, userDetails.getUser());
            return ResponseEntity.ok().body(String.format("Pk%d번 앱카드 삭제 완료", id));
        } catch (uqualsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping("/appcard/finish")
    private ResponseEntity<?> finishCheck(@RequestParam ("cardid")Long cardId,@RequestParam  ("checknum") int checkNum, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            appCardService.finishCheck(cardId, checkNum, userDetails.getUser());
            return ResponseEntity.ok().body("앱카드 체크 처리 완료");
        } catch (uqualsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (InputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
