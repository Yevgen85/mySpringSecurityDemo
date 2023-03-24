package com.example.demo.clr;

import com.example.demo.user.User;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
@RequiredArgsConstructor
public class Demo implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User user = User.builder()
                .firstName("Idan")
                .lastName("Ayash")
                .email("idan@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .isActive(true)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        this.userService.addUser(user);

        User user1 = User.builder()
                .firstName("Idan")
                .lastName("Ayash")
                .email("idan@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .isActive(true)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        this.userService.addUser(user1);
    }
}
