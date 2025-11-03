package nl.tudelft.sem.domain;

import io.jsonwebtoken.Jwts;

public class JwtAuthResponse {
    private final String jwt;

    public JwtAuthResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}

