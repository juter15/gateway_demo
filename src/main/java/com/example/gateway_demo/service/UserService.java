package com.example.gateway_demo.service;

import com.example.gateway_demo.model.LoginRequest;
import com.example.gateway_demo.model.LoginResponse;
import com.example.gateway_demo.model.User;
import com.example.gateway_demo.security.JwtUtil;
import com.example.gateway_demo.service.proxy.UserProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserProxy userProxy;
	private final JwtUtil jwtUtil;
	//private final RefreshTokenOperator refreshTokenOperator;


	public Mono<LoginResponse> signin(LoginRequest logindata) {

		log.info("### service");

		User user = userProxy.getUser(logindata);
		log.info("### {}", user);

		if (user != null) {
			return Mono.just(createTokens(user));
		} else {
			return Mono.empty();
		}
	}


//	public Mono<AuthResponse> refreshToken(String refreshToken, String userAgent) {
//		log.debug("### refreshToken={}, userAgent={}", refreshToken, userAgent);
//
//		String authCode = refreshTokenOperator.findAuthCode(refreshToken);
//		log.debug("### authCode: {}", authCode);
//
//		if (authCode != null) {
//			Map<String, String> requestBody = new HashMap<>();
//			requestBody.put("authCode", authCode);
//
//			User user = userProxy.findByAuthCode(requestBody);
//			log.debug("### {}", user);
//
//			if (user != null) {
//				refreshTokenOperator.removeRefreshToken(refreshToken);
//				return Mono.just(createTokens(user, userAgent));
//			}
//		}
//
//		return Mono.empty();
//	}
//
//
//	public void logout(String refreshToken) {
//		refreshTokenOperator.removeRefreshToken(refreshToken);
//	}


	private LoginResponse createTokens(@NotNull User user) {
		String accessToken = jwtUtil.generateToken(user);
		//String refreshToken = refreshTokenOperator.generateRefreshToken();

		ModelMapper modelMapper = new ModelMapper();

		LoginResponse response = modelMapper.map(user, LoginResponse.class);
		response.setAccessToken(accessToken);
		log.info("response: {}", response);
		//refreshTokenOperator.saveRefreshToken(user, refreshToken);

		return response;
	}
}
