package org.hse.software.construction.authservice.Utility;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    public String username;
    public String email;
    public String password;
}
