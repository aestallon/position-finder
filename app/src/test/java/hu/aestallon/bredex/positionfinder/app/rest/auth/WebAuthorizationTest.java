package hu.aestallon.bredex.positionfinder.app.rest.auth;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import hu.aestallon.bredex.positionfinder.app.config.WebAuthorizationTestConfig;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ClientCreationRequest;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ClientCreationResponse;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.Position;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.PositionSearchResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootApplication
@SpringBootTest(
    classes = WebAuthorizationTestConfig.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WebAuthorizationTest {

  private static final Logger log = LoggerFactory.getLogger(WebAuthorizationTest.class);

  @Autowired
  RestTemplate restTemplate;
  @LocalServerPort
  int localServerPort;
  @Value("${openapi.positionFinder.base-path:/api/v1}")
  String basePath;

  private UUID apiKey;

  private <T> HttpEntity<T> requestEntity() {
    return requestEntity(null);
  }

  private <T> HttpEntity<T> requestEntity(T body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    if (apiKey != null) {
      headers.set("X-Aest-Token", apiKey.toString());
    }
    return new HttpEntity<>(body, headers);
  }

  private String endpointPath(String endpoint) {
    return new StringBuilder("http://localhost:")
        .append(localServerPort)
        .append('/')
        .append(basePath)
        .append(endpoint)
        .toString();
  }

  private <T> T post(String endpoint, Object payload, Class<T> responseType) {
    log.debug("Invoking [ {} ] with payload [ {} ]", endpoint, payload);
    final ResponseEntity<T> response = restTemplate.exchange(
        endpointPath(endpoint),
        HttpMethod.POST,
        requestEntity(payload),
        responseType
    );
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).satisfies(
        code -> assertThat(code.is2xxSuccessful()).isTrue());

    if (Void.class == responseType) {
      return null;
    }
    return response.getBody();
  }

  private <T> T get(String endpoint, Class<T> responseType) {
    final ResponseEntity<T> response = restTemplate.exchange(
        endpointPath(endpoint),
        HttpMethod.GET,
        requestEntity(),
        responseType
    );
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).satisfies(
        code -> assertThat(code.is2xxSuccessful()).isTrue());

    if (Void.class == responseType) {
      return null;
    }
    return response.getBody();
  }

  @Test
  @Order(0)
  void contextLoads() {}

  @Test
  @Order(1)
  void requestingUndefinedEndpointWithoutApiKey_yieldsUnauthorizedStatus() {
    assertThrows(
        HttpClientErrorException.Unauthorized.class,
        () -> get("/foo", Void.class));
  }

  @Test
  @Order(2)
  void requestingExistingEndpointWithoutApiKey_yieldsUnauthorizedStatus() {
    assertThrows(
        HttpClientErrorException.Unauthorized.class,
        () -> post("/position", new Position().name("foo").location("bar"), Position.class));
    assertThrows(
        HttpClientErrorException.Unauthorized.class,
        () -> get("/position/search?name=xaa&location=yee", PositionSearchResult.class));
  }

  @Test
  @Order(3)
  void addingClientWithoutApiKey_returnsUuidKey() {
    final ClientCreationResponse response = post(
        "/client",
        new ClientCreationRequest().name("aestallon").email("noreply@aestallon.com"),
        ClientCreationResponse.class);
    assertThat(response).isNotNull();
    assertThat(response.getApiKey()).isNotNull();

    this.apiKey = response.getApiKey();
  }

  @Test
  @Order(4)
  void requestingUndefinedEndpointWithValidApiKey_yieldsNotFoundResponse() {
    assertThrows(HttpClientErrorException.NotFound.class, () -> get("/foo", Void.class));
  }

  @Test
  @Order(5)
  void creatingNewPositionWithValidApiKey_returnsMatchingPosition() {
    final var position = new Position().name("XXE").location("AADE");
    final Position response = post("/position", position, Position.class);
    assertThat(response).isEqualTo(position);
  }

  @Test
  @Order(6)
  void searchingForPositionsAfterCreation_yieldsNotEmptySearchResult() {
    final PositionSearchResult response = get(
        "/position/search?name=xx&location=aa",
        PositionSearchResult.class);
    assertThat(response).isNotNull();

    final List<String> urls = response.getUrls();
    assertThat(urls).isNotNull().isNotEmpty().hasSize(1);
  }

}
