package com.todoapp.todoapp.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;


@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    // Header의 KEY값
    public static final String AUTHORIZATION_HEADER = "Authorization";

    //어떤값앞에 Bearer이라 붙어 있으면 해당하는 그 값은 토큰이라 알려주는거다. VALUE값인 토큰 앞에 Bearer붙여주는데 한칸뛴다.(공백)
    public static final String BEARER_PREFIX = "Bearer ";

    // 토큰 만료시간 10분
    private final long TOKEN_TIME = 60 * 60 * 1000L;

    @Value("${jwt.secret.key}") // application.properties 에서 설정한 시크릿키 가져오기
    private String secretKey;

    //시크릿키를 담을 Key객체
    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct  // 주입 완료후 자동으로 해당 메소드 수행
    public void init(){
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key= Keys.hmacShaKeyFor(bytes);

    }

    public String createToken(String username){
//        LocalDateTime now = LocalDateTime.now().withNano(0); //
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()  //builder() 하고 마지막에 compact() 하면 JWT토큰 생성한다
                        .setSubject(username) //이번엔 pk말고 이름으로
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))   // setExpiration는 LocalDateTime 안되고 Date 타입
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 첫번째 매개변수는 시크릿키,두번째는 암호화 알고리즘
                        .compact();
    }


    public String getJWtHeader(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.substring(7);
        }

        return null;  //토큰 존재하지 않을때
    }

    //토큰 유효성 검사
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }  catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();  //이 작업하면 body안에 claim기반 데이터 반환
    }





}
