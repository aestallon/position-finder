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

package hu.aestallon.bredex.positionfinder.app.domain.appclient;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import hu.aestallon.bredex.positionfinder.app.domain.appclient.ApplicationClient;
import hu.aestallon.bredex.positionfinder.app.domain.appclient.ApplicationClientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationClientService {

  private final ApplicationClientRepository applicationClientRepository;

  @Transactional
  public ApplicationClient create(final String name, final String email) {
    if (applicationClientRepository.existsByName(name)) {
      throw new IllegalArgumentException("Cannot create client: name taken!");
    }

    final var client = new ApplicationClient();
    client.setName(name);
    client.setEmail(email);
    client.setApiKey(UUID.randomUUID());
    client.setCreatedAt(LocalDateTime.now());

    return applicationClientRepository.save(client);
  }

  public boolean isValid(UUID apiKey) {
    return applicationClientRepository.existsByApiKey(apiKey);
  }

}
