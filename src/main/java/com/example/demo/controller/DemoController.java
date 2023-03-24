package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/demo")
public class DemoController {

    @GetMapping
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/{text}")
    public String printText(@PathVariable String text) {
        return text;
    }
}
