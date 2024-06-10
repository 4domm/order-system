package org.hse.software.construction.authservice.Service;

import org.hse.software.construction.authservice.Model.User;
import org.hse.software.construction.authservice.Utility.AuthenticationRequest;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public interface UserService {
    HttpEntity<String> registerUser(User user);

    ResponseEntity<String> login(AuthenticationRequest request);

    ResponseEntity<Claims> getUserInfoFromToken(String token);
}
