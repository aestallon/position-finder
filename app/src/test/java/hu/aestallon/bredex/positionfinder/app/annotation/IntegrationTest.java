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

package hu.aestallon.bredex.positionfinder.app.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import hu.aestallon.bredex.positionfinder.app.config.IntegrationTestConfig;
import hu.aestallon.bredex.positionfinder.app.config.db.H2DatabaseConfig;


@SpringBootTest(
    classes = {
        H2DatabaseConfig.class,
        IntegrationTestConfig.class
    })
@ComponentScan(
    basePackages = {
        "hu.aestallon.bredex.positionfinder.app.domain",
        "hu.aestallon.bredex.positionfinder.app.rest.impl",
        "hu.aestallon.bredex.positionfinder.app.rest.validation"
    })
@EnableJdbcRepositories(basePackages = "hu.aestallon.bredex.positionfinder.app.domain")
@ActiveProfiles({"h2"})
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Sql({"/schema-h2.sql"})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface IntegrationTest {}
