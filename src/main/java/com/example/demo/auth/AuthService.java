package com.example.demo.auth;

import com.example.demo.token.TokenConfig;
import com.example.demo.user.User;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;
    private final UserService userService;

    public TokenResponseDTO validateLoginDetails(LoginRequestDTO loginRequestDTO) {
        boolean isLoginDetailsValid = this.isLoginDetailsValid(loginRequestDTO);
        if (isLoginDetailsValid) {
            User user = (User) this.userService.loadUserByUsername(loginRequestDTO.getEmail());
            String token = this.tokenConfig.generateToken(this.tokenConfig
                    .buildClaims(user));
            return new TokenResponseDTO(token);
        }
        return null;
    }

    private boolean isLoginDetailsValid(LoginRequestDTO loginRequestDTO) {
        System.out.println(loginRequestDTO.getEmail());
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getEmail(),
                            loginRequestDTO.getPassword()
                    )
            );
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

}
