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
 * Describes an individual error that was encountered while interpreting or processing the request and its payload. 
 */

@Schema(name = "ApiErrorElement", description = "Describes an individual error that was encountered while interpreting or processing the request and its payload. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ApiErrorElement {

  @JsonProperty("fieldName")
  private String fieldName;

  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  public ApiErrorElement fieldName(String fieldName) {
    this.fieldName = fieldName;
    return this;
  }

  /**
   * The name of the exact element pertaining to this error. 
   * @return fieldName
  */
  @NotNull 
  @Schema(name = "fieldName", description = "The name of the exact element pertaining to this error. ", required = true)
  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public ApiErrorElement code(String code) {
    this.code = code;
    return this;
  }

  /**
   * A short standardised code of the kind of error encountered. 
   * @return code
  */
  @NotNull 
  @Schema(name = "code", description = "A short standardised code of the kind of error encountered. ", required = true)
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ApiErrorElement message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Human readable message describing the error. 
   * @return message
  */
  @NotNull 
  @Schema(name = "message", description = "Human readable message describing the error. ", required = true)
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiErrorElement apiErrorElement = (ApiErrorElement) o;
    return Objects.equals(this.fieldName, apiErrorElement.fieldName) &&
        Objects.equals(this.code, apiErrorElement.code) &&
        Objects.equals(this.message, apiErrorElement.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fieldName, code, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiErrorElement {\n");
    sb.append("    fieldName: ").append(toIndentedString(fieldName)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

