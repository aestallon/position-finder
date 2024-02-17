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
 * The object returned after a query is performed. Contained URLs are to be interpreted to be relative to the API host. 
 */

@Schema(name = "PositionSearchResult", description = "The object returned after a query is performed. Contained URLs are to be interpreted to be relative to the API host. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class PositionSearchResult {

  @JsonProperty("urls")
  @Valid
  private java.util.List<String> urls = null;

  public PositionSearchResult urls(java.util.List<String> urls) {
    this.urls = urls;
    return this;
  }

  public PositionSearchResult addUrlsItem(String urlsItem) {
    if (this.urls == null) {
      this.urls = new java.util.ArrayList<>();
    }
    this.urls.add(urlsItem);
    return this;
  }

  /**
   * Get urls
   * @return urls
  */
  
  @Schema(name = "urls", required = false)
  public java.util.List<String> getUrls() {
    return urls;
  }

  public void setUrls(java.util.List<String> urls) {
    this.urls = urls;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PositionSearchResult positionSearchResult = (PositionSearchResult) o;
    return Objects.equals(this.urls, positionSearchResult.urls);
  }

  @Override
  public int hashCode() {
    return Objects.hash(urls);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PositionSearchResult {\n");
    sb.append("    urls: ").append(toIndentedString(urls)).append("\n");
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

