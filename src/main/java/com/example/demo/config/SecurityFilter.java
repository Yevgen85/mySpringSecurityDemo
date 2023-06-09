package com.example.demo.config;

import com.example.demo.token.TokenConfig;
import com.example.demo.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenConfig tokenConfig;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String token = tokenHeader.substring(7);
        String userName = this.tokenConfig.getUserNameFromToken(token);
        if (userName != null) {
            boolean isTokenExpirationValid = this.tokenConfig.isExpirationToken(token);
            if (isTokenExpirationValid) {
                UserDetails userDetails = this.userService.loadUserByUsername(userName);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(
                                 userDetails,
                                 null,
                                 new ArrayList<>()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
