package com.huzeji.todomanager.svc.util;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {
    private static final String SECRET_KEY = "testing123";
    public String extractUserName( String token ) { return ""; }
    public Date extractExpiration( String token ) { return new Date(); }
    public Boolean isTokenExpired( String token ) { return false; }
    public String generateToken( Object userDetails ) { return ""; }
    public Boolean validateToken( String token, Object userDetails ) { return true; }

}
