package org.hse.software.construction.authservice.Controller;

import org.hse.software.construction.authservice.Utility.AuthenticationRequest;
import org.hse.software.construction.authservice.Model.User;
import org.hse.software.construction.authservice.Service.Impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserServiceImpl userService;

    @PostMapping("/registration")
    public HttpEntity<String> registration(@RequestBody User user) {
        try {
            return userService.registerUser(user);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest request) {
        try {
            return userService.login(request);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/information")
    public ResponseEntity<Claims> getInformationFromToken(@RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            return userService.getUserInfoFromToken(token);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }
}
