package com.example.gateway_demo.configuration;

import com.example.gateway_demo.security.AuthenticationManager;
import com.example.gateway_demo.security.SecurityContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@RequiredArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfiguration {

	private final AuthenticationManager authenticationManager;
	private final SecurityContextRepository securityContextRepository;


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http
				.csrf().disable()
				.formLogin().disable()
				.httpBasic().disable()
				.logout().disable()
				.authenticationManager(authenticationManager)
				.securityContextRepository(securityContextRepository)
				.authorizeExchange()
					.pathMatchers(HttpMethod.POST, "/login").permitAll()
					.pathMatchers(HttpMethod.POST, "/refresh").permitAll()
					.pathMatchers("/robots.txt").permitAll()
					.anyExchange().authenticated()
					.and()
				.build();
	}
}
