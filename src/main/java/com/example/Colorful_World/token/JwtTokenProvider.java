package com.example.Colorful_World.token;

import com.example.Colorful_World.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${spring.jwt.secretkey}")
    private String secretKey;

    //토큰 유효 시간 설정
    private Long accessTime = 1 * 60 * 1000L;
    private Long refreshTime = 5 * 60 * 1000L;

    private Key key;


    //secretKey encoding
    @PostConstruct
    protected void init(){
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto createAllToken(String email){
        return new TokenDto(createToken(email, "access"), createToken(email, "refresh"), refreshTime);
    }

    //토큰 생성
    public String createToken(String email, String type){

        Date now = new Date();

        //토큰 종류에 따른 시간 설정
        long time = type.equals("access") ? accessTime : refreshTime;

        //Payload
        Claims claims = Jwts.claims()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + time));

        claims.put("type", type);


        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.forSigningKey(key))
                .compact();
    }

    private Map<String, Object> createHeader(){
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");

        return header;
    }



}
