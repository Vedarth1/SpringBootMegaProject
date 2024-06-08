package org.megaproject.chatboot.Controller;

import org.megaproject.chatboot.Models.Request;
import org.megaproject.chatboot.Models.Response;
import org.megaproject.chatboot.Models.Users;
import org.megaproject.chatboot.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    public AuthService authService;

    @GetMapping("/home")
    public ResponseEntity<Map<String, String>> auth() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello World");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody Request request) {
        return authService.login(request.getEmail(),request.getPassword());
    }

    @PostMapping("/signup")
    public ResponseEntity<Response> signup(@RequestBody Users user) {
        return authService.signup(user);
    }
}
