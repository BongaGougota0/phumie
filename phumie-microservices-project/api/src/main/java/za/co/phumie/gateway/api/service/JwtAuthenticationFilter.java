package za.co.phumie.gateway.api.service;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private final List<String> publicPaths = Arrays.asList(
            "/api/auth/login",
            "/api/auth/signup",
            "/api/health"
    );

    private final JwtAuthService jwtAuthService;

    public JwtAuthenticationFilter(JwtAuthService jwtAuthService) {
        this.jwtAuthService = new JwtAuthService();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (isPublicPath(request.getPath().toString())) {
            return chain.filter(exchange);
        }

        String token = extractToken(request);
        if(token == null || !jwtAuthService.validateToken(token)) {
            return handleUnauthorizedAccess(exchange);
        }

        String userEmail = jwtAuthService.extractUserEmail(token);
        String userId = jwtAuthService.extractUserId(token);
        String userRole = jwtAuthService.extractUserRole(token);

        // If userId is null (not in token), use email
        if (userId == null) {
            userId = userEmail;
        }

        ServerHttpRequest modifiedRequest = request.mutate()
                .header("userId",userId)
                .header("userRole", userRole != null ? userRole : "USER")
                .header("userEmail", userEmail)
                .build();
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private String extractToken(ServerHttpRequest request) {
        List<String> authHeaders = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authHeaders == null || authHeaders.isEmpty()) {
            return null;
        }
        String authHeader = authHeaders.get(0);
        if (authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean isPublicPath(String path) {
        return publicPaths.stream()
                .anyMatch(publicPath ->
                        publicPath.endsWith("/**")
                                ? path.startsWith(publicPath.substring(0, publicPath.length() - 3))
                                : path.equals(publicPath)
                );
    }

    private Mono<Void> handleUnauthorizedAccess(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json");
        String body = "{\"error\":\"Unauthorized\",\"message\":\"Invalid or missing JWT token\"}";
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes());
        return response.writeWith(Mono.just(buffer));
    }
}
