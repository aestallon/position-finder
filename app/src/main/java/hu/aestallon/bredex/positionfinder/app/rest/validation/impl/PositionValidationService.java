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
import org.springframework.stereotype.Service;
import com.google.common.base.Strings;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiErrorElement;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.Position;
import hu.aestallon.bredex.positionfinder.app.rest.validation.ValidationService;

@Service
public class PositionValidationService implements ValidationService<Position> {

  @Override
  public List<ApiErrorElement> validate(Position position) {
    final List<ApiErrorElement> errors = new ArrayList<>();

    final String name = position.getName();
    if (Strings.isNullOrEmpty(name)) {
      errors.add(new ApiErrorElement().fieldName("name").code("empty").message(""));
    } else if (name.length() > 50) {
      errors.add(new ApiErrorElement().fieldName("name").code("length.exceeded").message(""));
    }

    final String location = position.getLocation();
    if (Strings.isNullOrEmpty(location)) {
      errors.add(new ApiErrorElement().fieldName("name").code("empty").message(""));
    } else if (location.length() > 50) {
      errors.add(new ApiErrorElement().fieldName("name").code("length.exceeded").message(""));
    }

    return errors;
  }

}
