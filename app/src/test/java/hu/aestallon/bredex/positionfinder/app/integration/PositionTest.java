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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import hu.aestallon.bredex.positionfinder.app.annotation.DirtyTest;
import hu.aestallon.bredex.positionfinder.app.annotation.IntegrationTest;
import hu.aestallon.bredex.positionfinder.app.domain.jobposition.JobPositionService;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiError;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiErrorElement;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.Position;
import hu.aestallon.bredex.positionfinder.app.rest.impl.PositionApiDelegateImpl;
import hu.aestallon.bredex.positionfinder.app.rest.validation.InvalidApiObjectException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTest
class PositionTest {

  @Autowired
  PositionApiDelegateImpl positionApi;
  @Autowired
  JobPositionService positionService;
  @Test
  void contextLoads() {}

  @DirtyTest
  void afterCreatingPosition_itIsPresentInTheDatabase() {
    final var name = "Developer";
    final var location = "Brandenburg";
    Position position = positionApi
        .createPosition(new Position().name(name).location(location))
        .getBody();
    assertThat(position)
        .isNotNull()
        .returns(name, Position::getName)
        .returns(location, Position::getLocation);

    assertThat(positionService.searchPositions("", "")).hasSize(1);
  }

  @DirtyTest
  void creatingPositionWithEmptyName_fails() {
    final var exception = assertThrows(
        InvalidApiObjectException.class,
        () -> positionApi.createPosition(new Position()
            .name("")
            .location("Brandenburg")));
    assertThat(exception).isNotNull();
    final var errors = exception.errors();
    assertThat(errors).hasSize(1);
    final ApiErrorElement apiErrorElement = errors.get(0);
    assertThat(apiErrorElement).isNotNull()
        .returns("name", ApiErrorElement::getFieldName)
        .returns("empty", ApiErrorElement::getCode);

    assertThat(positionService.searchPositions("", "")).isEmpty();
  }

  @DirtyTest
  void creatingPositionWithEmptyLocation_fails() {
    final var exception = assertThrows(
        InvalidApiObjectException.class,
        () -> positionApi.createPosition(new Position()
            .name("Developer")
            .location("")));
    assertThat(exception).isNotNull();
    final var errors = exception.errors();
    assertThat(errors).hasSize(1);
    final ApiErrorElement apiErrorElement = errors.get(0);
    assertThat(apiErrorElement).isNotNull()
        .returns("location", ApiErrorElement::getFieldName)
        .returns("empty", ApiErrorElement::getCode);

    assertThat(positionService.searchPositions("", "")).isEmpty();
  }

  @DirtyTest
  void creatingPositionWithTooLongName_fails() {
    final String name = "a".repeat(51);

    final var exception = assertThrows(
        InvalidApiObjectException.class,
        () -> positionApi.createPosition(new Position()
            .name(name)
            .location("Brandenburg")));
    assertThat(exception).isNotNull();
    final var errors = exception.errors();
    assertThat(errors).hasSize(1);
    final ApiErrorElement apiErrorElement = errors.get(0);
    assertThat(apiErrorElement).isNotNull()
        .returns("name", ApiErrorElement::getFieldName)
        .returns("length.exceeded", ApiErrorElement::getCode);

    assertThat(positionService.searchPositions("", "")).isEmpty();
  }

  @DirtyTest
  void creatingPositionWithTooLongLocation_fails() {
    final String location = "a".repeat(51);
    final var exception = assertThrows(
        InvalidApiObjectException.class,
        () -> positionApi.createPosition(new Position()
            .name("Developer")
            .location(location)));
    assertThat(exception).isNotNull();
    final var errors = exception.errors();
    assertThat(errors).hasSize(1);
    final ApiErrorElement apiErrorElement = errors.get(0);
    assertThat(apiErrorElement).isNotNull()
        .returns("location", ApiErrorElement::getFieldName)
        .returns("length.exceeded", ApiErrorElement::getCode);

    assertThat(positionService.searchPositions("", "")).isEmpty();
  }

  @DirtyTest
  void creatingPositionsWithIssuesWithBothFields_returnsBothErrors() {
    final String location = "a".repeat(51);
    final var exception = assertThrows(
        InvalidApiObjectException.class,
        () -> positionApi.createPosition(new Position()
            .name("")
            .location(location)));
    assertThat(exception).isNotNull();
    final var errors = exception.errors();
    assertThat(errors)
        .hasSize(2)
        .anySatisfy(it -> assertThat(it).isNotNull()
            .returns("name", ApiErrorElement::getFieldName)
            .returns("empty", ApiErrorElement::getCode))
        .anySatisfy(it -> assertThat(it).isNotNull()
            .returns("location", ApiErrorElement::getFieldName)
            .returns("length.exceeded", ApiErrorElement::getCode));

    assertThat(positionService.searchPositions("", "")).isEmpty();
  }

  @DirtyTest
  void searchingForPositionsOnlyReturnsReferencesWhichContainReferencesToTheQueryTerms() {
    final var p1 = new Position()
        .name("Accountant")
        .location("London");
    final var p2 = new Position()
        .name("Developer")
        .location("London");
    final var p3 = new Position()
        .name("Musician")
        .location("Vienna");

    positionApi.createPosition(p1);
    positionApi.createPosition(p2);
    positionApi.createPosition(p3);

    assertThat(positionService.searchPositions("", "")).hasSize(3);

    {
      final long[] matchingIds = searchForPositionIds("A", null);
      final Set<Position> foundPositions = fetchPositionsById(matchingIds);
      assertThat(foundPositions)
          .hasSize(2)
          .containsExactlyInAnyOrder(p1, p3);
    }
    {
      final long[] matchingIds = searchForPositionIds(null, "London");
      final Set<Position> foundPositions = fetchPositionsById(matchingIds);
      assertThat(foundPositions)
          .hasSize(2)
          .containsExactlyInAnyOrder(p1, p2);
    }
    {
      final long[] matchingIds = searchForPositionIds("E", "London");
      final Set<Position> foundPositions = fetchPositionsById(matchingIds);
      assertThat(foundPositions)
          .hasSize(1)
          .containsExactlyInAnyOrder(p2);
    }
  }

  @DirtyTest
  void lookingForNonExistingPositionReturnsAppropriateApiError() {
    @SuppressWarnings({"rawtypes", "unchecked"})
    final ResponseEntity<ApiError> errorResponse = (ResponseEntity) positionApi.getPosition(999L);
    final ApiError error = errorResponse.getBody();
    assertThat(error).isNotNull();

    final List<ApiErrorElement> elements = error.getErrors();
    assertThat(elements).isNotNull().hasSize(1);

    ApiErrorElement apiErrorElement = elements.get(0);
    assertThat(apiErrorElement)
        .isNotNull()
        .returns("id", ApiErrorElement::getFieldName)
        .returns("not.found", ApiErrorElement::getCode);
  }

  private long[] searchForPositionIds(String qName, String qLocation) {
    return positionApi.searchPosition(Optional.ofNullable(qName), Optional.ofNullable(qLocation))
        .getBody()
        .getUrls().stream()
        .map(url -> url.split("/"))
        .map(arr -> arr[arr.length - 1])
        .mapToLong(Long::parseLong)
        .toArray();
  }

  private Set<Position> fetchPositionsById(long[] ids) {
    assertThat(ids).isNotNull();
    return Arrays.stream(ids)
        .mapToObj(positionApi::getPosition)
        .map(ResponseEntity::getBody)
        .collect(Collectors.toSet());
  }


}
