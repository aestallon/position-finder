/*
 * Copyright 2024 Szabolcs Bazil Papp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.aestallon.bredex.positionfinder.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.aestallon.bredex.positionfinder.app.rest.auth.ApiKeyFilter;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiError;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiErrorElement;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class PositionFinderSecurityConfig {

  private static final ApiError UNAUTHORIZED_RESPONSE = new ApiError()
      .status(HttpStatus.UNAUTHORIZED.value())
      .addErrorsItem(new ApiErrorElement()
          .code("invalid")
          .fieldName("token")
          .message("Invalid API key provided!"));

  private final ApiKeyFilter apiKeyFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http, ObjectMapper objectMapper)
      throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(request -> request
            .requestMatchers(
                new AntPathRequestMatcher("/api/v1/client"), // the only unprotected API-endpoint
                new AntPathRequestMatcher("/ui/**")) // where the public Vue client is served
            .permitAll()
            .antMatchers(
                "/",
                "/favicon.ico", // static stuff for the Vue client
                "/favicon.png",
                "/styles.css.map",
                "/polyfills.js.map",
                "/runtime.js.map",
                "/vendor.js.map",
                "/main.js.map",
                "/index.html",
                "/assets/**")
            .permitAll()
            .anyRequest()
            .authenticated())
        .formLogin(FormLoginConfigurer::disable)
        .logout(LogoutConfigurer::permitAll)
        .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(ex -> ex
            .accessDeniedHandler((req, res, exc) -> {
              res.setStatus(HttpStatus.UNAUTHORIZED.value());
              res.setContentType("application/json");
              res.getWriter().write(objectMapper.writeValueAsString(UNAUTHORIZED_RESPONSE));
            })
            .addObjectPostProcessor(new ObjectPostProcessor<ExceptionTranslationFilter>() {

              @Override
              public <O extends ExceptionTranslationFilter> O postProcess(O object) {
                object.setAuthenticationTrustResolver(new RejectingAuthTrustResolver());
                return object;
              }

            }))
        .build();
  }

  @Bean
  UserDetailsService userDetailsService() {
    return username -> null; // Only a mock, we are not handling authentication, only authorization.
  }

  // only enabled in dev-mode:
  @Bean
  @Profile("h2")
  WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().antMatchers(
        "/h2-console/**", // embedded database console
        "/v3/api-docs/**", // JSON-rendering of the API documentation
        "/swagger-ui/**"); // SwaggerUI
  }

  private static class RejectingAuthTrustResolver implements AuthenticationTrustResolver {
    @Override
    public boolean isAnonymous(Authentication authentication) {
      return false;
    }

    @Override
    public boolean isRememberMe(Authentication authentication) {
      return false;
    }
  }

}
