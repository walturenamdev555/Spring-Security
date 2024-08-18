package com.app.account.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {


  public static final String[] PUBLIC_URLS = {"/api/v1/auth/**, /swagger-ui.html"};

  // STEP 1 : configuration of spring security
  @Bean
  protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

    AuthenticationManagerBuilder authenticationManagerBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);
    // Pass service object that is implementing UserDetailsService and password encoder
    AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

    // Cross-Site Request Forgery : Disabled spring default security
    http.csrf(AbstractHttpConfigurer::disable);
    // Disabled default frame
    http.headers(
        (headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

    return http.authorizeHttpRequests(
            (auth) -> auth.requestMatchers("/user/save").permitAll().anyRequest().authenticated())
        // Pass authenticationManager
        .authenticationManager(authenticationManager)
        // Configure AuthorizationFilter
        .addFilter(new AuthorizationFilter(authenticationManager))
        // TO make session stateless
        .sessionManagement(
            (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
  }

}
