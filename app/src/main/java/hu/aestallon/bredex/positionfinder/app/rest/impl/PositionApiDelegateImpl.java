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

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import hu.aestallon.bredex.positionfinder.app.domain.jobposition.JobPosition;
import hu.aestallon.bredex.positionfinder.app.domain.jobposition.JobPositionService;
import hu.aestallon.bredex.positionfinder.app.rest.generated.api.PositionApiDelegate;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiError;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiErrorElement;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.Position;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.PositionSearchResult;
import hu.aestallon.bredex.positionfinder.app.rest.validation.InvalidApiObjectException;
import hu.aestallon.bredex.positionfinder.app.rest.validation.impl.PositionValidationService;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
public class PositionApiDelegateImpl implements PositionApiDelegate {

  private static final Logger log = LoggerFactory.getLogger(PositionApiDelegateImpl.class);

  private static Position asDto(final JobPosition jobPosition) {
    return new Position()
        .name(jobPosition.name())
        .location(jobPosition.location());
  }

  private static ApiError positionNotFound(final long id) {
    return new ApiError()
        .status(HttpStatus.NOT_FOUND.value())
        .addErrorsItem(new ApiErrorElement()
            .fieldName("id")
            .code("not.found")
            .message("No entry can be found by id: " + id));
  }

  private final String apiPath;
  private final PositionValidationService positionValidationService;
  private final JobPositionService jobPositionService;

  public PositionApiDelegateImpl(@Value("${openapi.positionFinder.base-path}") String apiPath,
                                 PositionValidationService positionValidationService,
                                 JobPositionService jobPositionService) {
    this.apiPath = apiPath;
    this.positionValidationService = positionValidationService;
    this.jobPositionService = jobPositionService;
  }

  @Override
  public ResponseEntity<Position> createPosition(Position position) {
    log.debug("createPosition({})...", position);

    final var errors = positionValidationService.validate(position);
    if (!errors.isEmpty()) {
      log.debug("createPosition - validation returned errors: {}", errors);
      throw new InvalidApiObjectException(errors);
    }

    final JobPosition domainPosition = jobPositionService.createPosition(
        position.getName(),
        position.getLocation());
    log.debug("createPosition - created new position: {}", domainPosition);

    return new ResponseEntity<>(asDto(domainPosition), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<Position> getPosition(Long id) {
    return jobPositionService
        .findPosition(id)
        .map(PositionApiDelegateImpl::asDto)
        .map(ResponseEntity::ok)
        // sadly raw-types: we can't coerce these distinct types, and we shouldn't even if we could:
        .orElseGet(() -> new ResponseEntity(positionNotFound(id), HttpStatus.NOT_FOUND));
  }

  @Override
  public ResponseEntity<PositionSearchResult> searchPosition(Optional<String> name,
                                                             Optional<String> location) {
    return jobPositionService.searchPositions(name.orElse(""), location.orElse("")).stream()
        .map(JobPosition::id)
        .map(id -> apiPath + '/' + id)
        .collect(collectingAndThen(
            toList(),
            urls -> ResponseEntity.ok(new PositionSearchResult().urls(urls))));
  }

}
