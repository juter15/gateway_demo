package com.example.gateway_demo.security;

import com.example.gateway_demo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil implements Serializable {
	private static final long serialVersionUID = -8253948383106826464L;

	@Value("${jwt.secret}")
	private String secret;

	/**
	 * DB값으로 변경
	 */
//	@Value("${jwt.expire-seconds}")
//	private String expirationTime;

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	public String getUserFromToken(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}

	public String getUserNameFromToken(String token) {
		return getAllClaimsFromToken(token).get("name", String.class);
	}

	public String getUserSeqFromToken(String token) {
		return getAllClaimsFromToken(token).get("userId", String.class);
	}
	public String getUserIdFromToken(String token) {
		return getAllClaimsFromToken(token).get("userName", String.class);
	}

//
//	public String getUserTypeFromToken(String token) {
//		return getAllClaimsFromToken(token).get("type", String.class);
//	}
//
//	public String getUserGradeFromToken(String token) {
//		return getAllClaimsFromToken(token).get("grade", String.class);
//	}
//
//	public String getUserStatusFromToken(String token) {
//		return getAllClaimsFromToken(token).get("status", String.class);
//	}

	public Date getExpirationDateFromToken(String token) {
		return getAllClaimsFromToken(token).getExpiration();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	@SuppressWarnings("Duplicates")
	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userNa", user.getUserNa());
		claims.put("authorities", user.getAuthorities());
		claims.put("userId", user.getUserId());
		claims.put("userSeq", user.getUserSeq());
		claims.put("userName", user.getUsername());

		return doGenerateToken(claims, user);
	}

	@SuppressWarnings("Duplicates")
	private String doGenerateToken(Map<String, Object> claims, User user) {
		Long expirationTimeLong = user.getExpireTime(); //in second 500 -> 분

		final Date createdDate = new Date();
		final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(user.getUsername())
				.setIssuedAt(createdDate)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	/**
	 * 토큰의 유효기간 체크
	 * @param token
	 * @return 토큰 유효시: true / 무효: false
	 */
	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}
}
