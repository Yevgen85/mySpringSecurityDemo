package com.example.demo.task;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/task")
public class TasksController {

    @GetMapping
    public Object getTasks() {
        return new ArrayList<>();
    }
}
