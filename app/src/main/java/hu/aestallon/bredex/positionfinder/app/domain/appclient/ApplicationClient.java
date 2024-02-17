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

package hu.aestallon.bredex.positionfinder.app.domain.appclient;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("app_client")
public class ApplicationClient {

  @Id
  @Column("id")
  private Long id;

  @Column("client_name")
  private String name;

  @Column("client_email")
  private String email;

  @Column("api_key")
  private UUID apiKey;

  @Column("created_at")
  private LocalDateTime createdAt;

  public ApplicationClient() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UUID getUuid() {
    return apiKey;
  }

  public void setApiKey(UUID apiKey) {
    this.apiKey = apiKey;
  }

  public LocalDateTime getCreatedAt() {
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
    ApplicationClient that = (ApplicationClient) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name)
        && Objects.equals(email, that.email) && Objects.equals(apiKey,
        that.apiKey) && Objects.equals(createdAt, that.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, apiKey, createdAt);
  }

  @Override
  public String toString() {
    return "ApplicationClient {" +
        "\n  id: " + id +
        ",\n  name: " + name +
        ",\n  email: " + email +
        ",\n  apiKey: " + apiKey +
        ",\n  createdAt: " + createdAt +
        "\n}";
  }

}
