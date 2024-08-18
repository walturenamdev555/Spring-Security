package com.app.account.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class AuthorizationFilter extends BasicAuthenticationFilter {

  private static final String SECRET_KEY =
      "9e4ebc68-631f-4268-8f0e-8f699b0fcfd4-9e4ebc68-631f-4268-8f0e-8f699b0fcfd4";

  public AuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  protected void doFilterInternal(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    String authorizationHeader = req.getHeader("Authorization");
    if (authorizationHeader == null) {
      chain.doFilter(req, res);
      return;
    }
    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader == null) {
      return null;
    }

    SecretKey secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(SECRET_KEY.getBytes()));
    JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
    Claims claims = (Claims) jwtParser.parse(authorizationHeader).getPayload();
    String subject = claims.getSubject();
    return new UsernamePasswordAuthenticationToken(subject, null, getUserAuthorities(claims));
  }

  public Collection<? extends GrantedAuthority> getUserAuthorities(Claims claims) {

    Collection<Map<String, String>> scopes = claims.get("scope", List.class);

    return scopes.stream()
        .map(scopeMap -> new SimpleGrantedAuthority(scopeMap.get("authority")))
        .collect(Collectors.toList());
  }
}
