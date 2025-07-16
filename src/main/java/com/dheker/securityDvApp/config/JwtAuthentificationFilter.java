package com.dheker.securityDvApp.config;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
//everytime we get a req from user this filter should be activated
@RequiredArgsConstructor
public class JwtAuthentificationFilter  extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(
            @NonNull  HttpServletRequest request,
            @NonNull                HttpServletResponse response,
            @NonNull              FilterChain filterChain)
            throws ServletException, IOException {
        //header that contains bearer token
        final String authHeader= request.getHeader("Authorization");




    }

}
