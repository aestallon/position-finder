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
 * ClientCreationRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ClientCreationRequest {

  @JsonProperty("name")
  private String name;

  @JsonProperty("email")
  private String email;

  public ClientCreationRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The unique name of this client. 
   * @return name
  */
  @NotNull @Size(min = 3, max = 100) 
  @Schema(name = "name", example = "Client-001", description = "The unique name of this client. ", required = true)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ClientCreationRequest email(String email) {
    this.email = email;
    return this;
  }

  /**
   * A valid e-mail address 
   * @return email
  */
  @NotNull @Size(min = 3, max = 100) 
  @Schema(name = "email", example = "user@domain", description = "A valid e-mail address ", required = true)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientCreationRequest clientCreationRequest = (ClientCreationRequest) o;
    return Objects.equals(this.name, clientCreationRequest.name) &&
        Objects.equals(this.email, clientCreationRequest.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, email);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClientCreationRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
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

