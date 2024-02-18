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

package hu.aestallon.bredex.positionfinder.app.domain.jobposition;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.google.common.base.Strings;
import hu.aestallon.bredex.positionfinder.app.domain.jobposition.JobPosition;
import hu.aestallon.bredex.positionfinder.app.domain.jobposition.JobPositionRepository;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.Position;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobPositionService {

  private final JobPositionRepository jobPositionRepository;

  public Optional<JobPosition> findPosition(final long id) {
    return jobPositionRepository.findById(id);
  }

  public List<JobPosition> searchPositions(final String name, final String location) {
    return jobPositionRepository.searchPositions(
        normalise(name),
        normalise(location));
  }

  public JobPosition createPosition(final String name, final String location) {
    final var position = new JobPosition();
    position.setName(name);
    position.setLocation(location);
    position.setCreatedAt(LocalDateTime.now());
    return jobPositionRepository.save(position);
  }

  private static String normalise(final String s) {
    return (Strings.isNullOrEmpty(s)) ? "" : s.strip().toLowerCase();
  }
}
