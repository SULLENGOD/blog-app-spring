package com.posts.posts_service.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.posts.posts_service.adapter.in.UserContext;
import com.posts.posts_service.domain.exception.InvalidToken;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private JwtService jwtService;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);

            if (jwtService.isTokenValid(token)) {
                String username = jwtService.extractUsername(token);
                String userId = jwtService.extractUserId(token);

                UserContext userContext = new UserContext();
                userContext.setUserId(userId);
                userContext.setUsername(username);

                request.setAttribute("userContext", userContext);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, new ArrayList<>());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                handlerExceptionResolver.resolveException(request, response, null,
                        new InvalidToken("Invalid token. Access denied."));
            }
        } catch (ExpiredJwtException e) {
            handlerExceptionResolver.resolveException(request, response, null,
                    new InvalidToken("Token expired. Please log in again."));
        } catch (JwtException e) {
            handlerExceptionResolver.resolveException(request, response, null,
                    new InvalidToken("Invalid token. Access denied."));
        }
        filterChain.doFilter(request, response);
    }
}
