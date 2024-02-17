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
 * ClientCreationResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ClientCreationResponse {

  @JsonProperty("apiKey")
  private java.util.UUID apiKey;

  public ClientCreationResponse apiKey(java.util.UUID apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  /**
   * The API key with which this application's endpoints may be interacted with. 
   * @return apiKey
  */
  @Valid 
  @Schema(name = "apiKey", description = "The API key with which this application's endpoints may be interacted with. ", required = false)
  public java.util.UUID getApiKey() {
    return apiKey;
  }

  public void setApiKey(java.util.UUID apiKey) {
    this.apiKey = apiKey;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientCreationResponse clientCreationResponse = (ClientCreationResponse) o;
    return Objects.equals(this.apiKey, clientCreationResponse.apiKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apiKey);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClientCreationResponse {\n");
    sb.append("    apiKey: ").append(toIndentedString(apiKey)).append("\n");
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

