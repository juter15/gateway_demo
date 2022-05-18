package com.example.gateway_demo.controller;

import com.example.gateway_demo.model.LoginRequest;
import com.example.gateway_demo.model.LoginResponse;
import com.example.gateway_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

	private final UserService userService;


	@PostMapping("/login")
	public Mono<ResponseEntity<LoginResponse>> login(
			@RequestBody LoginRequest loginData
	) {
		log.info("### Controller");
		return userService.signin(loginData)
				.map(loginResponse -> ResponseEntity.status(HttpStatus.OK).body(loginResponse))
				.defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}


//	@PostMapping("/logout")
//	public Mono<ResponseEntity> logout(@RequestBody AuthRequest ar) {
//		userService.logout(ar.getRefreshToken());
//		return Mono.just(ResponseEntity.status(HttpStatus.OK).build());
//	}


//	@PostMapping("/refresh")
//	public Mono<ResponseEntity<AuthResponse>> refresh(
//			@RequestHeader("user-agent") String userAgent,
//			@RequestBody AuthRequest ar
//	) {
//		return userService.refreshToken(ar.getRefreshToken(), userAgent)
//				.map(authResponse -> ResponseEntity.status(HttpStatus.OK).body(authResponse))
//				.defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//	}

	@RequestMapping(value = "/robots.txt")
	@ResponseBody
	public String robots() {
		return "<p>User-agent: *</p>"+"\n<p>"+"Disallow: /"+"</p>\n";
	}
}
