package com.example.gateway_demo.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

	private final JwtUtil jwtUtil;


	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String authToken = authentication.getCredentials().toString();
		String username;

		try {
			username = jwtUtil.getUserFromToken(authToken);
		} catch (Exception e) {
			username = null;
		}

		if (username != null && jwtUtil.validateToken(authToken)) {
			Claims claims = jwtUtil.getAllClaimsFromToken(authToken);

			@SuppressWarnings("unchecked")
			Collection<String> authorities = claims.get("authorities", List.class);

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					username,
					null,
					 authorities.stream()
							.map(SimpleGrantedAuthority::new)
							.collect(toList())
			);

			return Mono.just(auth);
		} else {
			return Mono.empty();
		}
	}
}
