package com.huzeji.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }

    @GetMapping("/secure")
    public String secureEndpoint(Principal principal) {
        return "This is a secure endpoint. Hello, " + principal.getName();
    }
}
