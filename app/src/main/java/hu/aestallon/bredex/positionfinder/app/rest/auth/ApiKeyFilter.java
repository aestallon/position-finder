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

package hu.aestallon.bredex.positionfinder.app.rest.auth;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import hu.aestallon.bredex.positionfinder.app.domain.appclient.ApplicationClientService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

  private static final Logger log = LoggerFactory.getLogger(ApiKeyFilter.class);

  private final ApplicationClientService applicationClientService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    final String apiKeyStr = request.getHeader("X-Aest-Token");
    final UUID apiKey;
    try {
      apiKey = UUID.fromString(apiKeyStr);
    } catch (NullPointerException | IllegalArgumentException e) {
      log.debug(e.getMessage(), e);
      filterChain.doFilter(request, response);
      return;
    }

    if (applicationClientService.isValid(apiKey)) {
      final var securityContext = SecurityContextHolder.createEmptyContext();
      final var token = new PreAuthenticatedAuthenticationToken(
          "me",
          apiKey,
          Collections.emptyList());
      token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      securityContext.setAuthentication(token);
      SecurityContextHolder.setContext(securityContext);
    }

    filterChain.doFilter(request, response);
  }

}
