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

package hu.aestallon.bredex.positionfinder.app.rest.validation;

import java.util.List;
import java.util.Objects;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiErrorElement;

public class InvalidApiObjectException extends RuntimeException {

  private final List<ApiErrorElement> errors;

  public InvalidApiObjectException(List<ApiErrorElement> errors) {
    super();
    this.errors = Objects.requireNonNull(errors, "Errorlist must not be nulL!");
  }

  public List<ApiErrorElement> errors() {
    return errors;
  }

}
