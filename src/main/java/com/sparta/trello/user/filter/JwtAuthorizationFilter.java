package com.sparta.trello.user.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.trello.user.dto.CommonResponseDto;
import com.sparta.trello.user.jwt.JwtUtil;
import com.sparta.trello.user.security.UserDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    private  final UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request);

        if(Objects.nonNull(token)) {
            if(jwtUtil.validateToken(token)){
                Claims info = jwtUtil.getUserInfoFromToken(token);

                //인증정보에 유저정보(nickname) 넣기
                //nickname->user조회
                String nickname= info.getSubject();
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                //->userDetails에 담고
                UserDetails userDetails = userDetailsService.getUserDetails(nickname);
                //->authentication의 principal에 담고
                Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                //->securityContent에 담고
                context.setAuthentication(authentication);
                //->SecurityContextHolder에 담고
                SecurityContextHolder.setContext(context);
                //->이제 @AuthenticationPrincipal로 조회할 수 있음

            }else{
                //인증정보 존재하지 을때
                CommonResponseDto commonResponseDto = new CommonResponseDto("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST.value());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(commonResponseDto));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
