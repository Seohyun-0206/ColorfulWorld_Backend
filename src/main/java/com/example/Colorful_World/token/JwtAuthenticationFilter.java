package com.example.Colorful_World.token;

import com.example.Colorful_World.exception.BaseException;
import com.example.Colorful_World.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{

        String accessToken = jwtTokenProvider.resolveToken(request, "access_token");
        String refreshToken = jwtTokenProvider.resolveToken(request, "refresh_token");

        System.out.println("access : [" + accessToken + "]");
        System.out.println("refresh : [" + refreshToken + "]");

        if(accessToken != null){
            if(jwtTokenProvider.validateToken(accessToken)){

                /*logout 여부 확인 후 로그아웃 안했을 경우 정보 저장*/
                String isLogout = (String)redisTemplate.opsForValue().get(accessToken);
                if(ObjectUtils.isEmpty(isLogout)){
                    Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            //ATK 만료된 상황
            else if(refreshToken != null){
                //RTK 만료X
                if(jwtTokenProvider.validateToken(refreshToken)){

                    String email = jwtTokenProvider.getEmail(refreshToken);

                    //redis에 저장된 RTK와 똑같은지 확인
                    String redisRefreshToken = (String)redisTemplate.opsForValue().get("RTK: " + email);
                    if(!ObjectUtils.isEmpty(redisRefreshToken)){
                        if(redisRefreshToken.equals(refreshToken)) {

                            //새로운 ATK 발급
                            String newAcessToken = jwtTokenProvider.createToken(email, "access");

                            response.setHeader("access_token", newAcessToken);
                            System.out.println("atk 재발급");

                            //정보 저장
                            Authentication authentication = jwtTokenProvider.getAuthentication(newAcessToken);
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                    else{
                        throw new BaseException(ErrorCode.UNAUTHORIZED);
                    }
                }
                //ATK 만료 & RTK 만료 -> 인증 안됨
                else{
                    throw new BaseException(ErrorCode.UNAUTHORIZED);
                }

            }

        }

        chain.doFilter(request, response);
    }
}
