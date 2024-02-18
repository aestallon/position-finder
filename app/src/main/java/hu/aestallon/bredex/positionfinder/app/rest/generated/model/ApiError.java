package hu.aestallon.bredex.positionfinder.app.rest.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiErrorElement;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Generic response the API answers with, when it cannot return a 2xx status response. 
 */

@Schema(name = "ApiError", description = "Generic response the API answers with, when it cannot return a 2xx status response. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ApiError {

  @JsonProperty("status")
  private Integer status = -1;

  @JsonProperty("errors")
  @Valid
  private java.util.List<ApiErrorElement> errors = new java.util.ArrayList<>();

  public ApiError status(Integer status) {
    this.status = status;
    return this;
  }

  /**
   * The HTTP status code is provided here for the sake of convenience. 
   * @return status
  */
  @NotNull 
  @Schema(name = "status", example = "400", description = "The HTTP status code is provided here for the sake of convenience. ", required = true)
  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public ApiError errors(java.util.List<ApiErrorElement> errors) {
    this.errors = errors;
    return this;
  }

  public ApiError addErrorsItem(ApiErrorElement errorsItem) {
    this.errors.add(errorsItem);
    return this;
  }

  /**
   * Get errors
   * @return errors
  */
  @NotNull @Valid 
  @Schema(name = "errors", required = true)
  public java.util.List<ApiErrorElement> getErrors() {
    return errors;
  }

  public void setErrors(java.util.List<ApiErrorElement> errors) {
    this.errors = errors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiError apiError = (ApiError) o;
    return Objects.equals(this.status, apiError.status) &&
        Objects.equals(this.errors, apiError.errors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, errors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiError {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
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

