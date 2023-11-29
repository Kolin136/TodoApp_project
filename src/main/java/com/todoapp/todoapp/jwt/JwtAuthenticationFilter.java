package com.todoapp.todoapp.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.todoapp.dto.user.LoginRequestDto;
import com.todoapp.todoapp.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/todo/user/login");  // 클라가 해당 url요청한경우 필터 작동

    }


    //사용자가 전송한 로그인 정보를 받아 AuthenticationManager한테 제출하고, 실제로 인증을 수행하는 과정
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // HttpServletRequest body데이터인 json형식

        String tokenValue = jwtUtil.getJWtHeader(request);



        log.info("로그인 시작");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            //ObjectMapper().readValue는 String형 jason을 Object형으로 변환하고 첫번째 매개변수에는 해당 데이터 가져오고,두번쨰에는
            //변환할 object 타입

            return getAuthenticationManager().authenticate(    // authenticate()는 검증하는 메소드이고 그러므로 매개변수에 토큰 넣어줘야한다
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null    // 인증할땐 권한이 필요 없으니 null
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        log.info("로그인 성공 및 JWT 생성");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        String token = jwtUtil.createToken(username);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);   //헤더에 직접 JWT토큰을 담는다
        response.setStatus(HttpServletResponse.SC_OK); // 상태 보내기
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.println("로그인 성공");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)  throws IOException {
        log.info("로그인 실패");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);   //실패할경우 401상태 보낸다
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.println("회원을 찾을 수 없습니다.");
    }


}