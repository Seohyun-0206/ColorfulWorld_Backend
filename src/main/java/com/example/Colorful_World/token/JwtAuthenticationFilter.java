package com.example.Colorful_World.token;

import com.example.Colorful_World.exception.BaseException;
import com.example.Colorful_World.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{

        String accessToken = jwtTokenProvider.resolveToken(request, "access_token");
        String refreshToken = jwtTokenProvider.resolveToken(request, "refresh_token");

        System.out.println("access : [" + accessToken + "]");
        System.out.println("refresh : [" + refreshToken + "]");

        if(accessToken != null){
            if(jwtTokenProvider.validateToken(accessToken)){

                /*logout 여부 확인 코드 추가*/

                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            //ATK 만료된 상황
            else if(refreshToken != null){
                //RTK 만료X
                if(jwtTokenProvider.validateToken(refreshToken)){

                    /*RTK redis에 있는것과 같은지 확인 코드 추가*/

                    String email = jwtTokenProvider.getEmail(refreshToken);

                    String newAcessToken = jwtTokenProvider.createToken(email, "access");

                    response.setHeader("access_token", newAcessToken);

                    Authentication authentication = jwtTokenProvider.getAuthentication(newAcessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
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
