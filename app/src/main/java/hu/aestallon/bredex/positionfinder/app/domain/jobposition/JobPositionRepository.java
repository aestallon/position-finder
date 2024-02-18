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

import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import hu.aestallon.bredex.positionfinder.app.domain.jobposition.JobPosition;

@Repository
public interface JobPositionRepository extends CrudRepository<JobPosition, Long> {

  @Query("select * from job_position jp "
      + "where lower(jp.pos_name) like '%' || :name|| '%' "
      + "and lower(jp.pos_location) like '%' || :location || '%'")
  List<JobPosition> searchPositions(final @Param("name") String name,
                                    final @Param("location") String location);
}
