package com.company.salestracker.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	@Value("${jwt.secret}")
	private String SECRET ;
	@Value("${jwt.access-token-expiration}")
	private long ACCESS_EXPIRY;
	@Value("${jwt.access-token-refresh-expiry}")
	private long REFRESH_EXPIRY;
	
	private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }
	
	public String generateAccessToken(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRY))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
	
	 public String generateRefreshToken(String username) {
	        return Jwts.builder()
	                .setSubject(username)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRY))
	                .signWith(getKey(), SignatureAlgorithm.HS256)
	                .compact();
	    }
	 
	 public String extractUsername(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject();
	    }

	    public boolean isExpired(String token) {
	        Date exp = Jwts.parserBuilder()
	                .setSigningKey(getKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .getExpiration();
	        return exp.before(new Date());
	    }
	    
	    
	    public boolean validateToken(String token) {
	        try {
	            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	    }
	
	
	
}
