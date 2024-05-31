package org.megaproject.chatboot.Service;

import org.megaproject.chatboot.Models.Response;
import org.megaproject.chatboot.Models.Users;
import org.megaproject.chatboot.Repository.UserRepository;
import org.megaproject.chatboot.Utils.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JWTGenerator jwtGenerator;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, JWTGenerator jwtGenerator, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<Response> login(String email, String password) {
        Users user = userRepository.findByEmail(email);
        if (user != null) {
            if (matches(password, user.getPassword())) {
                System.out.println("Hey");
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password)
                );
                System.out.println("Authentication: " + authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String token = jwtGenerator.generateToken(authentication);

                System.out.println("username: " + jwtGenerator.getUsernameFromJWT(token));
                System.out.println("validate: " + jwtGenerator.validateToken(token));

                Response response = Response.builder()
                        .message("Login successful")
                        .token(token)
                        .build();
                return ResponseEntity.ok(response);
            }
        }
        Response response = Response.builder()
                .message("Invalid email or password")
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    public ResponseEntity<Response> signup(Users user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            Response response = Response.builder()
                    .message("Email is already in use")
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        user.setPassword(encode(user.getPassword()));
        Users savedUser = userRepository.save(user);

        Response response = Response.builder()
                .message("Signup successful")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public String encode(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }
}
