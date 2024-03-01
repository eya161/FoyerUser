package tn.esprit.foyeruniversiteeya.jwt;

import lombok.Data;

@Data
public class JWTAuthenticationRequest {
    private String email;
    private String password;
}