package com.app.user.config;

import com.app.user.domain.LoginRequestModel;
import com.app.user.entity.UserEntity;
import com.app.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private UserService userService;

  private static final String SECRET_KEY =
      "9e4ebc68-631f-4268-8f0e-8f699b0fcfd4-9e4ebc68-631f-4268-8f0e-8f699b0fcfd4";

  public UserAuthenticationFilter(
      AuthenticationManager authenticationManager, UserService userService) {
    super(authenticationManager);
    this.userService = userService;
  }

  // This method get called when user attempt to login first time
  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      LoginRequestModel creds =
          new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
      return getAuthenticationManager()
          .authenticate(
              new UsernamePasswordAuthenticationToken(
                  creds.getEmail(), creds.getPassword(), Collections.emptyList()));
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  // successfulAuthentication get called after calling loadUserByUsername and loading
  // user successfully  from database and send it to
  @Override
  protected void successfulAuthentication(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
      throws IOException, ServletException {
    User user = (User) auth.getPrincipal();
    System.out.println(user.getUsername());
    UserEntity userEntity = userService.findByEmail(user.getUsername());
    SecretKey secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(SECRET_KEY.getBytes()));
    Instant now = Instant.now();
    String token =
        Jwts.builder()
            .claim("scope", auth.getAuthorities())
            .subject(userEntity.getEmail())
            .expiration(Date.from(now.plusMillis(28800000)))
            .issuedAt(Date.from(now))
            .signWith(secretKey)
            .compact();
    res.addHeader("token", token);
  }
}
