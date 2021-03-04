package com.greeting.demo;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApplicationController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

//    These paths are case-sensitive it appears
    @GetMapping("/greeting")
    public Client greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Client(counter.incrementAndGet(), String.format(template, name));
    }

}