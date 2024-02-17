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
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("job_position")
public class JobPosition {

  @Id
  @Column("id")
  private Long id;

  @Column("pos_name")
  private String name;

  @Column("pos_location")
  private String location;

  @Column("created_at")
  private LocalDateTime createdAt;

  public JobPosition() {}

  public Long id() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String name() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String location() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public LocalDateTime createdAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    JobPosition that = (JobPosition) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name)
        && Objects.equals(location, that.location) && Objects.equals(createdAt,
        that.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, location, createdAt);
  }

  @Override
  public String toString() {
    return "JobPosition {" +
        "\n  id: " + id +
        ",\n  name: " + name +
        ",\n  location: " + location +
        ",\n  createdAt: " + createdAt +
        "\n}";
  }

}
