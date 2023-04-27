package com.example.Colorful_World.exception;

import com.example.Colorful_World.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        try{
            chain.doFilter(request,response);
        } catch (BaseException ex){
            setErrorResponse(response, ex);
        }

    }

    public void setErrorResponse(HttpServletResponse response, BaseException ex){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(ex.getErrorCode().getHttpStatus().value());
        response.setContentType("application/json;charset=utf-8");
        ResponseDto responseDto  = new ResponseDto(ex.getErrorCode().getHttpStatus().value(), ex.getErrorCode().getCode(), ex.getErrorCode().getDetail());

        try{
            response.getWriter().write(objectMapper.writeValueAsString(responseDto));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
