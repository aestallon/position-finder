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

package hu.aestallon.bredex.positionfinder.app.rest.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import hu.aestallon.bredex.positionfinder.app.domain.appclient.ApplicationClient;
import hu.aestallon.bredex.positionfinder.app.domain.appclient.ApplicationClientService;
import hu.aestallon.bredex.positionfinder.app.rest.generated.api.ClientApiDelegate;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ClientCreationRequest;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ClientCreationResponse;
import hu.aestallon.bredex.positionfinder.app.rest.validation.impl.ClientCreationRequestValidationService;
import hu.aestallon.bredex.positionfinder.app.rest.validation.InvalidApiObjectException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientApiDelegateImpl implements ClientApiDelegate {

  private final ClientCreationRequestValidationService validationService;
  private final ApplicationClientService applicationClientService;

  @Override
  public ResponseEntity<ClientCreationResponse> createClient(
      ClientCreationRequest clientCreationRequest) {
    final var errors = validationService.validate(clientCreationRequest);
    if (!errors.isEmpty()) {
      throw new InvalidApiObjectException(errors);
    }

    final ApplicationClient applicationClient = applicationClientService.create(
        clientCreationRequest.getName(),
        clientCreationRequest.getEmail());
    return new ResponseEntity<>(
        new ClientCreationResponse().apiKey(applicationClient.getUuid()),
        HttpStatus.CREATED);
  }

}
