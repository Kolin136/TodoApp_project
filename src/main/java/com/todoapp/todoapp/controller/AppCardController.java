package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.dto.card.AllCardResponseDto;
import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.dto.card.SelectCardResponseDto;
import com.todoapp.todoapp.security.UserDetailsImpl;
import com.todoapp.todoapp.service.AppCardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/todo")
@RestController
@RequiredArgsConstructor
public class AppCardController {

  private final AppCardService appCardService;

  //회원 정보를 넘겨줘야하니 인증객체 AuthenticationPrincipal에 들어있는 UserDetailsImpl 가져오면된다
  @PostMapping("/appcard")
  public ResponseEntity<SelectCardResponseDto> createCard(@RequestBody CardRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    SelectCardResponseDto selectCardResponseDto = appCardService.createCard(requestDto,
        userDetails.getUser().getId());

    return ResponseEntity.ok(selectCardResponseDto);
  }


  @GetMapping("/appcard/{id}")
  public ResponseEntity<?> getIdCard(@PathVariable Long id) {

    SelectCardResponseDto selectCardResponseDto = appCardService.getIdCard(id);
    return ResponseEntity.ok(selectCardResponseDto);

  }


  @GetMapping("/appcard")
  public ResponseEntity<Page<AllCardResponseDto>> getCards(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc) {

    Page<AllCardResponseDto> allCardResponseDtoList = appCardService.getCards(page-1,size,sortBy,isAsc);

    return ResponseEntity.ok(allCardResponseDtoList);
  }


  @PutMapping("/appcard/{id}")
  public ResponseEntity<?> updateCard(@PathVariable Long id, @RequestBody CardRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    SelectCardResponseDto selectCardResponseDto = appCardService.updateCard(id, requestDto,
        userDetails.getUser());
    return ResponseEntity.ok(selectCardResponseDto);

  }


  @DeleteMapping("/appcard/{id}")
  public ResponseEntity<?> deleteCard(@PathVariable Long id,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    appCardService.deleteCard(id, userDetails.getUser());
    return ResponseEntity.ok().body(String.format("Pk %d번 앱카드 삭제 완료", id));

  }


  @PutMapping("/appcard/finish")
  private ResponseEntity<?> finishCheck(@RequestParam("cardid") Long cardId,
      @RequestParam("checknum") int checkNum,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    appCardService.finishCheck(cardId, checkNum, userDetails.getUser());
    return ResponseEntity.ok().body("앱카드 체크 처리 완료");

  }

}
