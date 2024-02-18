package hu.aestallon.bredex.positionfinder.app.rest.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Position
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class Position {

  @JsonProperty("name")
  private String name;

  @JsonProperty("location")
  private String location;

  public Position name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the occupation. 
   * @return name
  */
  @NotNull @Size(min = 1, max = 50) 
  @Schema(name = "name", example = "Accountant", description = "The name of the occupation. ", required = true)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Position location(String location) {
    this.location = location;
    return this;
  }

  /**
   * Freeform location of the position. 
   * @return location
  */
  @NotNull @Size(min = 1, max = 50) 
  @Schema(name = "location", example = "Budapest", description = "Freeform location of the position. ", required = true)
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Position position = (Position) o;
    return Objects.equals(this.name, position.name) &&
        Objects.equals(this.location, position.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, location);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Position {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

