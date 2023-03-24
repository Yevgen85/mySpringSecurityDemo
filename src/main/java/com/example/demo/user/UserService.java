package com.example.demo.user;

import com.example.demo.auth.TokenResponseDTO;
import com.example.demo.token.TokenConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenConfig tokenConfig;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       TokenConfig tokenConfig,
                       @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenConfig = tokenConfig;
        this.passwordEncoder = passwordEncoder;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Call DB to get user details.
//        System.out.println("loadUserByUsername has been called");
//        return new User("idan", "{noop}1234", new ArrayList<>());
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmailAndIsActiveTrue(username);
    }

    public TokenResponseDTO addUser(User user) throws Exception {
        if (this.userRepository.existsByEmail(user.getUsername())) {
            throw new Exception("Email Already exist");
        }
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        String token = this.tokenConfig.generateToken(this.tokenConfig.buildClaims(user));
        return new TokenResponseDTO(token);
    }
}
