package com.example.gateway_demo.service.proxy;

import com.example.gateway_demo.model.LoginRequest;
import com.example.gateway_demo.model.User;
import com.example.gateway_demo.service.proxy.fallback.UserProxyFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-service", url = "${service.user-service}", fallbackFactory = UserProxyFallbackFactory.class)
public interface UserProxy {

    @PostMapping("/login")
    User getUser(@RequestBody LoginRequest loginRequest);

}
