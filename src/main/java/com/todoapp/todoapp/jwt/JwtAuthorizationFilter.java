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
        else {
            log.info("Token null");
            res.setContentType("application/json");
            res.setCharacterEncoding("utf-8");
            PrintWriter writer = res.getWriter();
            writer.println("헤더에 토큰을 넣어주시고 로그인 하거나 로그인을 먼저 해주세요.");

        }
        //로그인을 하려는 경우에는 토큰이 비어 있어도 아무 이상 없어야하는데 config에서 인증보다 인가를 먼저 처리하도록 해서 로그인 요청시에 문제다...

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