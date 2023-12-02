package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.config.WebSecurityConfig;
import com.todoapp.todoapp.dto.comment.CommentRequestDto;
import com.todoapp.todoapp.dto.comment.CommentResponseDto;
import com.todoapp.todoapp.service.AppCommentService;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {AppcommentController.class}, // 테스트할 컨트롤러를 지정한다
        excludeFilters = {   // 테스트 제외할 컨트롤러(이부분은 메인에서 시큐리티 사용했을때 한다)
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class AppcommentControllerTest extends SettingMvc{

    @MockBean
    AppCommentService appCommentService;


    @BeforeEach
    void setUp() {
        super.mockUserSetup();


    }
    @Nested
    @DisplayName("댓글 컨트롤러 모두 한번에 테스트")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class AllCommentControllerTest {

        @Test
        @Order(1)
        @DisplayName("댓글 등록")
        void createComment() throws Exception {
            //given
            CommentRequestDto requestDto = new CommentRequestDto("댓글");
            CommentResponseDto responseDto = new CommentResponseDto(1L, "seok", "댓글", LocalDateTime.MIN);

            String dtoJson = objectMapper.writeValueAsString(requestDto);

            // when
            given(appCommentService.createComment(any(), any(), any())).willReturn(responseDto);

            // then
            mvc.perform(post("/todo/appcomment/1")
                            .content(dtoJson)
                            .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                            .accept(MediaType.APPLICATION_JSON)
                            .principal(mockPrincipal))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username").value("seok"))
                    .andExpect(jsonPath("$.comment").value("댓글"));

        }

        @Test
        @Order(2)
        @DisplayName("댓글 수정")
        void updateComment() throws Exception {
            // given
            CommentRequestDto requestDto = new CommentRequestDto("댓글 수정");
            CommentResponseDto responseDto = new CommentResponseDto(1L, "seok", "댓글 수정", null);

            String dtoJson = objectMapper.writeValueAsString(requestDto);

            // when
            given(appCommentService.updateComment(any(), any(), any(), any())).willReturn(responseDto);

            // then
            mvc.perform(put("/todo/appcomment")
                            .param("cardid", "1")
                            .param("commentid", "1")
                            .content(dtoJson)
                            .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                            .accept(MediaType.APPLICATION_JSON)
                            .principal(mockPrincipal))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username").value("seok"))
                    .andExpect(jsonPath("$.comment").value("댓글 수정"));

        }

        @Test
        @Order(3)
        @DisplayName("댓글 삭제")
        void deleteCard() throws Exception {
            // given
            String ResponseEntityMessage = "Pk 1번 댓글 삭제 완료";

            mvc.perform(delete("/todo/appcomment/1")
                            .principal(mockPrincipal))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string(ResponseEntityMessage));

        }

    }

}