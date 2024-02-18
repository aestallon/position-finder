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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import hu.aestallon.bredex.positionfinder.app.domain.appclient.ClientNameConflictException;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiError;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiErrorElement;

@ControllerAdvice
public class ApiExceptionHandler {

  private static ApiError apiErrorOf(HttpStatus httpStatus, List<ApiErrorElement> errors) {
    return new ApiError().status(httpStatus.value()).errors(errors);
  }

  @ExceptionHandler(InvalidApiObjectException.class)
  public ResponseEntity<ApiError> invalidApiObjectException(InvalidApiObjectException e,
                                                            WebRequest request) {
    final ApiError error = apiErrorOf(HttpStatus.BAD_REQUEST, e.errors());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ClientNameConflictException.class)
  public ResponseEntity<ApiError> clientNameConflictException(ClientNameConflictException e,
                                                              WebRequest request) {
    final ApiError error = apiErrorOf(HttpStatus.CONFLICT, List.of(new ApiErrorElement()
        .fieldName("name")
        .code("name.taken")
        .message("Requested client name is already taken: " + e.clientName())));
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
  }

}
