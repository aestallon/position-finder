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
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import com.google.common.base.Strings;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiErrorElement;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ClientCreationRequest;
import hu.aestallon.bredex.positionfinder.app.rest.validation.ValidationService;

@Service
public class ClientCreationRequestValidationService
    implements ValidationService<ClientCreationRequest> {

  private static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
  public static final Pattern EMAIL_PTRN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

  @Override
  public List<ApiErrorElement> validate(ClientCreationRequest request) {
    final List<ApiErrorElement> errors = new ArrayList<>();

    final String name = request.getName();
    if (Strings.isNullOrEmpty(name)) {
      // TODO: Use messageSource!
      errors.add(new ApiErrorElement().fieldName("name").code("empty").message(""));
    } else if (name.length() > 100) {
      errors.add(new ApiErrorElement().fieldName("name").code("length.exceeded").message(""));
    }

    final String email = request.getEmail();
    if (Strings.isNullOrEmpty(email)) {
      errors.add(new ApiErrorElement().fieldName("email").code("empty").message(""));
    } else if (request.getEmail().length() > 100) {
      errors.add(new ApiErrorElement().fieldName("email").code("length.exceeded").message(""));
    } else if (!EMAIL_PTRN.matcher(email).matches()) {
      errors.add(new ApiErrorElement().fieldName("email").code("bad.format").message(""));
    }

    return errors;
  }

}
