/*
 * Copyright 2024 Szabolcs Bazil Papp
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.aestallon.bredex.positionfinder.app.integration;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import hu.aestallon.bredex.positionfinder.app.annotation.DirtyTest;
import hu.aestallon.bredex.positionfinder.app.annotation.IntegrationTest;
import hu.aestallon.bredex.positionfinder.app.config.IntegrationTestConfig;
import hu.aestallon.bredex.positionfinder.app.config.db.H2DatabaseConfig;
import hu.aestallon.bredex.positionfinder.app.domain.appclient.ApplicationClientService;
import hu.aestallon.bredex.positionfinder.app.domain.appclient.ClientNameConflictException;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiErrorElement;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ClientCreationRequest;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ClientCreationResponse;
import hu.aestallon.bredex.positionfinder.app.rest.impl.ClientApiDelegateImpl;
import hu.aestallon.bredex.positionfinder.app.rest.validation.InvalidApiObjectException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTest
class ApplicationClientTest {

  @Autowired
  ClientApiDelegateImpl clientApi;
  @Autowired
  ApplicationClientService clientService;

  @Test
  void contextLoads() {}

  @DirtyTest
  void creatingApplicationClientWithValidName_succeeds() {
    ResponseEntity<ClientCreationResponse> res =
        clientApi.createClient(new ClientCreationRequest()
            .name("alpha")
            .email("alpha@alpha.com"));
    ClientCreationResponse ccr = res.getBody();
    assertThat(ccr).isNotNull();
    assertThat(ccr.getApiKey()).isNotNull();
    assertThat(clientService.isValid(ccr.getApiKey())).isTrue();
  }

  @DirtyTest
  void creatingApplicationWithSameNameTwice_fails() {
    ResponseEntity<ClientCreationResponse> res =
        clientApi.createClient(new ClientCreationRequest()
            .name("alpha")
            .email("alpha@alpha.com"));
    ClientCreationResponse ccr = res.getBody();
    assertThat(ccr).isNotNull();
    assertThat(ccr.getApiKey()).isNotNull();
    assertThat(clientService.isValid(ccr.getApiKey())).isTrue();



    final var exception = assertThrows(
        ClientNameConflictException.class,
        () -> clientApi.createClient(new ClientCreationRequest()
            .name("alpha")
            .email("alpha2@alpha.com")));
    assertThat(exception.clientName()).isEqualTo("alpha");
  }

  @DirtyTest
  void creatingApplicationWithEmptyName_fails() {
    final var exception = assertThrows(
        InvalidApiObjectException.class,
        () -> clientApi.createClient(new ClientCreationRequest()
            .name("")
            .email("alpha2@alpha.com")));
    final var errors = exception.errors();
    assertThat(errors).isNotNull().hasSize(1);

    final ApiErrorElement errorElement = errors.get(0);
    assertThat(errorElement)
        .isNotNull()
        .returns("name", ApiErrorElement::getFieldName)
        .returns("empty", ApiErrorElement::getCode);
  }

  @DirtyTest
  void creatingApplicationWithEmptyMail_failsWithEmptyFieldError() {
    final var exception = assertThrows(
        InvalidApiObjectException.class,
        () -> clientApi.createClient(new ClientCreationRequest()
            .name("myClientNameXax")
            .email("")));
    final var errors = exception.errors();
    assertThat(errors).isNotNull().hasSize(1);

    final ApiErrorElement errorElement = errors.get(0);
    assertThat(errorElement)
        .isNotNull()
        .returns("email", ApiErrorElement::getFieldName)
        .returns("empty", ApiErrorElement::getCode);
  }

  @DirtyTest
  void creatingApplicationWithInvalidMail_failsWithInvalidFieldError() {
    final var exception = assertThrows(
        InvalidApiObjectException.class,
        () -> clientApi.createClient(new ClientCreationRequest()
            .name("myClientNameXax")
            .email("not-an-email")));
    final var errors = exception.errors();
    assertThat(errors).isNotNull().hasSize(1);

    final ApiErrorElement errorElement = errors.get(0);
    assertThat(errorElement)
        .isNotNull()
        .returns("email", ApiErrorElement::getFieldName)
        .returns("bad.format", ApiErrorElement::getCode);
  }

  @DirtyTest
  void creatingApplicationWithTooLongName_failsWithTooLongFieldError() {
    final var name = "s".repeat(101);
    final var exception = assertThrows(
        InvalidApiObjectException.class,
        () -> clientApi.createClient(new ClientCreationRequest()
            .name(name)
            .email("validmail@domain.net")));
    final var errors = exception.errors();
    assertThat(errors).isNotNull().hasSize(1);

    final ApiErrorElement errorElement = errors.get(0);
    assertThat(errorElement)
        .isNotNull()
        .returns("name", ApiErrorElement::getFieldName)
        .returns("length.exceeded", ApiErrorElement::getCode);
  }

}
