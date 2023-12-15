package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.config.WebSecurityConfig;
import com.todoapp.todoapp.dto.card.AllCardResponseDto;
import com.todoapp.todoapp.dto.card.CardRequestDto;
import com.todoapp.todoapp.dto.card.SelectCardResponseDto;
import com.todoapp.todoapp.service.AppCardService;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {AppCardController.class}, // 테스트할 컨트롤러를 지정한다
        excludeFilters = {   // 테스트 제외할 컨트롤러(이부분은 메인에서 시큐리티 사용했을때 한다)
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class AppCardControllerTest extends SettingMvc {

    @MockBean
    AppCardService appCardService;

    @BeforeEach
    void setUp() {
        super.mockUserSetup();
    }

    @Nested
    @DisplayName("할일 카드 컨트롤러 모두 한번에 테스트")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class AllCardControllerTest {

        @Test
        @Order(1)
        @DisplayName("할일 카드 등록")
        void createCard() throws Exception {
            // given
            String title = "제목 테스트";
            String contents = "내용 테스트";
            CardRequestDto requestDto = new CardRequestDto(title, contents);

            String dtoJson = objectMapper.writeValueAsString(requestDto);

            // when,then
            mvc.perform(post("/todo/appcard")
                            .content(dtoJson) // content()는 body  내용 설정
                            .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)) //  JSON 형식의 데이터를 전송할 것이다 설정
                            .accept(MediaType.APPLICATION_JSON)  // 서버가 작업후 클라는 JSON 형식의 응답을 원한다 설정
                            .principal(mockPrincipal)
                    )
                    .andExpect(status().isOk());

        }


        @Test
        @Order(2)
        @DisplayName("특정 할일 카드 조회")
        void getIdCard() throws Exception {
            // given
            SelectCardResponseDto responseDto = new SelectCardResponseDto(1L, "seok", "제목", "내용", null);

            given(appCardService.getIdCard(1L)).willReturn(responseDto);

            // when,then
            mvc.perform(get("/todo/appcard/1")
                            .accept(MediaType.APPLICATION_JSON)
                            .principal(mockPrincipal))
                    .andExpect(status().isOk())
//                .andDo(MockMvcResultHandlers.print());
                    .andExpect(jsonPath("$.username").value(responseDto.getUsername()))
                    .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
                    .andExpect(jsonPath("$.contents").value(responseDto.getContents()));
        }

        @Test
        @Order(3)
        @DisplayName("모든 할일 카드 조회")
        void getCards() throws Exception {
            // given
            List<AllCardResponseDto> dtoList = new ArrayList<>(Arrays.asList(
                    new AllCardResponseDto(1L, "seok", "제목1", "내용1", null, 0),
                    new AllCardResponseDto(2L, "seok", "제목2", "내용2", null, 0),
                    new AllCardResponseDto(3L, "seok", "제목3", "내용3", null, 0)
            ));

            given(appCardService.getCards(page - 1, size, sortBy, isAsc)).willReturn(dtoList);

            // when,then
            mvc.perform(get("/todo/appcard")
                            .accept(MediaType.APPLICATION_JSON)
                            .principal(mockPrincipal))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$[0].title").value("제목1"))
                    .andExpect(jsonPath("$[1].title").value("제목2"))
                    .andExpect(jsonPath("$[2].title").value("제목3"));
        }

        @Test
        @Order(4)
        @DisplayName("할일 카드 수정")
        void updateCard() throws Exception {
            //given
            CardRequestDto requestDto = new CardRequestDto("제목 수정", "내용 수정");
            SelectCardResponseDto responseDto = new SelectCardResponseDto(1L, "seok", "제목 수정", "내용 수정", null);

            String dtoJson = objectMapper.writeValueAsString(requestDto);


            // 첫번째 매개변수 Long타입값 넣는거에서 any()말고 1L적으면 왜 목이 아닌 실제 서비스 돌아가서 오류 나는지 모르겠습니다.
            // 근데 또 이상하게 "특정 할일 카드 조회" 테스트쪽은 1L적어도 오류없이 정상적으로 돌아가는지... 버그가 있는건지 이상합니다
            given(appCardService.updateCard(any(), any(), any())).willReturn(responseDto);

            // when,then
            mvc.perform(put("/todo/appcard/1")
                            .content(dtoJson)
                            .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                            .accept(MediaType.APPLICATION_JSON)
                            .principal(mockPrincipal))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username").value("seok"))
                    .andExpect(jsonPath("$.title").value("제목 수정"))
                    .andExpect(jsonPath("$.contents").value("내용 수정"));
        }


        @Test
        @Order(5)
        @DisplayName("할일 카드 삭제")
        void deleteCard() throws Exception {
            // given
            String ResponseEntityMessage = "Pk 1번 앱카드 삭제 완료";

            // when,then
            mvc.perform(delete("/todo/appcard/1")
                            .principal(mockPrincipal))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string(ResponseEntityMessage));
            // MockMvcResultMatchers.content().string(문자열)은
            // 반환된 문자열이 ResponseEntityMessage와 완전히 일치하는지를 확인하는거

        }

        @Test
        @Order(6)
        @DisplayName("할일 카드 완료 체크")
        void finishCheck() throws Exception {
            // given
            String ResponseEntityMessage = "앱카드 체크 처리 완료";

            // when,then
            mvc.perform(put("/todo/appcard/finish")
                            .param("cardid", "1")
                            .param("checknum", "1")
                            .principal(mockPrincipal))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string(ResponseEntityMessage));

        }
    }


}