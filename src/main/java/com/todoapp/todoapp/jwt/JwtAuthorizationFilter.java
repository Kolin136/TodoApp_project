package com.todoapp.todoapp.jwt;

import com.todoapp.todoapp.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getJWtHeader(req);


//        if (!StringUtils.hasText(tokenValue) || !jwtUtil.validateToken(tokenValue)) {
//            log.error("Token Error");
//            res.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 상태 보내기
//            res.setContentType("application/json");
//            res.setCharacterEncoding("utf-8");
//            PrintWriter writer = res.getWriter();
//            writer.println("토큰이 유효하지 않습니다.");
//            return;
//        }
        if (StringUtils.hasText(tokenValue)){

            if (!jwtUtil.validateToken(tokenValue)) {
                log.error("Token Error");
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 상태 보내기
                res.setContentType("application/json");
                res.setCharacterEncoding("utf-8");
                PrintWriter writer = res.getWriter();
                writer.println("토큰이 유효하지 않습니다.");
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

            //info.getSubject()는 인증 단계에서 토큰 생성할때 넣은 username 가져온다.
            try {
                setAuthentication(info.getSubject());
                log.info("인가 시작");
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }
//        else {
//            log.info("Token null");
//            res.setContentType("application/json");
//            res.setCharacterEncoding("utf-8");
//            PrintWriter writer = res.getWriter();
//            writer.println("토큰이 없는 상태입니다.");
//        }

        // 과제 필수사항에 "토큰이 필요한 API 요청에서 토큰을 전달하지 않았거나 정상 토큰이 아닐 때는 "토큰이 유효하지 않습니다." 라는 에러메시지와 statusCode: 400을 Client에 반환하기"
        // 시큐리티 필터가 JwtAuthentizationFilter -> JwtAuthenticaionFilter 순으로 하도록 config에서 설정해서 토큰 생성전 로그인 하려할때도 JwtAuthentizationFilter를 걸치니
        // JwtAuthentizationFilter에서 토큰이 비어있을시 오류 메세지 보내는게 문제..
        // 일단 현재 해결 방법은 모르겠고 토큰 비어있을시 메세지 보내면 회원가입쪽이랑 충돌 이어나서 주석 처리합니다.

        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        log.info("인가 성공");
    }

    // 인증 객체 생성
    // UsernamePasswordAuthenticationToken는 첫번째부터 들어가는 매개변수는 principal(사용자정보 userDetails),credentials (비밀번호),authorities(사용자권한)
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}