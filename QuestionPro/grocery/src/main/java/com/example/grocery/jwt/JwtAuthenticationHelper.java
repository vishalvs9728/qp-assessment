package com.example.grocery.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtAuthenticationHelper {

	private String secretKey = "thisisvishalbvkeythekeythatidontwanttoforgetthedatefifthocttwozerotwofoursaturday";
	private static final long JWT_TOKEN_VALIDITY = 60 * 60;

	public String getUsernameFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		return claims.getSubject();
	}

	public Claims getClaimsFromToken(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token)
				.getBody();
		return claims;
	}

	public boolean isTokenExpired(String token) {
		Claims claims = getClaimsFromToken(token);
		Date expDate = claims.getExpiration();
		Date currentDate = new Date();

		return expDate.before(currentDate);
	}

	public String generateToken(UserDetails userDetails) {
		// TODO Auto-generated method stub
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().addClaims(claims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()),
						SignatureAlgorithm.HS512)
				.compact();
	}

}
