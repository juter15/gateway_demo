package com.example.gateway_demo.configuration;

import com.example.gateway_demo.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Configuration
public class RouteConfiguration {

	private final JwtUtil jwtUtil;

	@Value("${service.user-service}")
	private String UserService;



	public RouteConfiguration(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}


	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

		return builder.routes()

				//login Demo Routing
				.route("store_rout", r -> r.path("/store/**")
						.filters(defaultFilters())
						.uri(UserService))
				.build();
	}


	private Function<GatewayFilterSpec, UriSpec> defaultFilters() {
		return f -> f.preserveHostHeader().filter(this::addHeaders);
	}


	private Mono<Void> addHeaders(ServerWebExchange exchange, GatewayFilterChain chain) {
		List<String> authorization = exchange.getRequest().getHeaders().get("Authorization");

		if (!CollectionUtils.isEmpty(authorization)) {
			String token = authorization.get(0).replace("Bearer ", "");
			log.debug("### token: {}", token);

			String userId = jwtUtil.getUserIdFromToken(token);
			String userSeq = jwtUtil.getUserSeqFromToken(token);
			String userName = jwtUtil.getUserNameFromToken(token);
			String id = jwtUtil.getUserFromToken(token);


			log.debug("### userId={}, userSeq={}, id={}, userName={}",
					userId, userSeq, id, userName);

			ServerHttpRequest request = exchange.getRequest().mutate()
					.header("userId", ArrayUtils.toArray(userId))
					.header("userSeq", ArrayUtils.toArray(userSeq))
					.header("id", ArrayUtils.toArray(id))
					.header("userName", ArrayUtils.toArray(userName))
					.build();

			return chain.filter(exchange.mutate().request(request).build());
		}

		return chain.filter(exchange);
	}
}
