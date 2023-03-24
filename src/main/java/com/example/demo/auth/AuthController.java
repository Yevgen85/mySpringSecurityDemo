package com.example.demo.auth;

import com.example.demo.user.User;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return this.authService.validateLoginDetails(loginRequestDTO);
    }

    @PostMapping("/registration")
    public TokenResponseDTO registration(@RequestBody User user) throws Exception {
        return this.userService.addUser(user);
    }

//    @PostMapping
//    public Company addCompany(@RequestBody Company company) {
//        return //...
//    }
}
