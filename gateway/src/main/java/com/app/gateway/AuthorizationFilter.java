package com.app.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);
  private static final String SECRET_KEY =
      "9e4ebc68-631f-4268-8f0e-8f699b0fcfd4-9e4ebc68-631f-4268-8f0e-8f699b0fcfd4";

  public AuthorizationFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    LOGGER.info(String.valueOf(config != null));
    return ((exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
        return onError(exchange, "Invalid User Login", HttpStatus.UNAUTHORIZED);
      }
      String authorizationHeader =
          exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
      if (!isJwtTokenValid(authorizationHeader)) {
        onError(exchange, "Jwt token is not valid", HttpStatus.UNAUTHORIZED);
      }
      return chain.filter(exchange);
    });
  }

  private boolean isJwtTokenValid(String authorizationHeader) {
    SecretKey secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(SECRET_KEY.getBytes()));
    JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
    Claims claims = (Claims) jwtParser.parse(authorizationHeader).getPayload();
    String subject = claims.getSubject();
    if (subject == null) {
      return false;
    }
    return true;
  }

  private Mono<Void> onError(
      ServerWebExchange exchange, String invalid_user_login, HttpStatus unauthorized) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(unauthorized);
    return response.setComplete();
  }

  public static class Config {
    // Config properties

  }
}
