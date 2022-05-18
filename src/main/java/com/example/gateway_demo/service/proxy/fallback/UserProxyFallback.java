package com.example.gateway_demo.service.proxy.fallback;

import com.example.gateway_demo.model.LoginRequest;
import com.example.gateway_demo.model.User;
import com.example.gateway_demo.service.proxy.UserProxy;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class UserProxyFallback implements UserProxy {

	private final Throwable cause;

	UserProxyFallback(Throwable cause) {
		this.cause = cause;
	}


	@Override
	public User getUser(LoginRequest loginRequest) {
		log.error(cause.getMessage());
		return null;
	}
}
