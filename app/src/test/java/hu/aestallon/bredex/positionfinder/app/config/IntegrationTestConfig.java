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

package hu.aestallon.bredex.positionfinder.app.config;

import javax.sql.DataSource;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@TestConfiguration
public class IntegrationTestConfig {

  @Bean
  DataSource dataSource() {
    final var dataSource = new DriverManagerDataSource(
        "jdbc:h2:mem:test-db;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;CASE_INSENSITIVE_IDENTIFIERS=TRUE;DB_CLOSE_DELAY=-1;",
        "sa",
        "sa");
    dataSource.setDriverClassName("org.h2.Driver");
    return dataSource;
  }

}
