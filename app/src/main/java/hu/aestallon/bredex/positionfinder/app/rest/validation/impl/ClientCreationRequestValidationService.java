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

package hu.aestallon.bredex.positionfinder.app.rest.validation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import com.google.common.base.Strings;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiErrorElement;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ClientCreationRequest;
import hu.aestallon.bredex.positionfinder.app.rest.validation.ValidationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientCreationRequestValidationService
    implements ValidationService<ClientCreationRequest> {

  private static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
  public static final Pattern EMAIL_PTRN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

  public static final int CLIENT_NAME_MAX_LENGTH = 100;
  public static final int CLIENT_MAIL_MAX_LENGTH = 100;

  private final MessageSource messageSource;

  @Override
  public List<ApiErrorElement> validate(ClientCreationRequest request) {
    final List<ApiErrorElement> errors = new ArrayList<>();

    final String name = request.getName();
    if (Strings.isNullOrEmpty(name)) {
      errors.add(new ApiErrorElement()
          .fieldName("name")
          .code("empty")
          .message(messageSource.getMessage(
              "client.name.empty",
              null,
              "Client name must not be empty!",
              LOCALE_EN)));
    } else if (name.length() > CLIENT_NAME_MAX_LENGTH) {
      errors.add(new ApiErrorElement()
          .fieldName("name")
          .code("length.exceeded")
          .message(messageSource.getMessage(
              "client.name.length.exceeded",
              new Object[] {CLIENT_NAME_MAX_LENGTH},
              "Client name too long!",
              LOCALE_EN)));
    }

    final String email = request.getEmail();
    if (Strings.isNullOrEmpty(email)) {
      errors.add(new ApiErrorElement()
          .fieldName("email")
          .code("empty")
          .message(messageSource.getMessage(
              "client.mail.empty",
              null,
              "Client mail cannot be empty!",
              LOCALE_EN)));
    } else if (request.getEmail().length() > CLIENT_MAIL_MAX_LENGTH) {
      errors.add(new ApiErrorElement()
          .fieldName("email")
          .code("length.exceeded")
          .message(messageSource.getMessage(
              "client.mail.length.exceeded",
              new Object[] {CLIENT_MAIL_MAX_LENGTH},
              "Client mail too long!",
              LOCALE_EN)));
    } else if (!EMAIL_PTRN.matcher(email).matches()) {
      errors.add(new ApiErrorElement()
          .fieldName("email")
          .code("bad.format")
          .message(messageSource.getMessage(
              "client.mail.bad.format",
              new Object[] {email},
              "Client mail bad format!",
              LOCALE_EN)));
    }

    return errors;
  }

}
