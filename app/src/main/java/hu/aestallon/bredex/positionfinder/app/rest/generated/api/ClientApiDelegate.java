package hu.aestallon.bredex.positionfinder.app.rest.generated.api;

import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiError;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ClientCreationRequest;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ClientCreationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

/**
 * A delegate to be called by the {@link ClientApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface ClientApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /client : Registers a client to acquire an API key.
     * Unsecured endpoint that enables &#39;registering&#39; a client with this API, returning an key, which is valid until revocation. 
     *
     * @param clientCreationRequest  (required)
     * @return Ok (status code 201)
     *         or Bad Request (status code 400)
     * @see ClientApi#createClient
     */
    default ResponseEntity<ClientCreationResponse> createClient(ClientCreationRequest clientCreationRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"apiKey\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
