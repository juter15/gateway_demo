package com.example.gateway_demo.service.proxy.fallback;

import com.example.gateway_demo.service.proxy.UserProxy;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserProxyFallbackFactory implements FallbackFactory<UserProxy> {
	@Override
	public UserProxy create(Throwable cause) {
		return new UserProxyFallback(cause);
	}
}
