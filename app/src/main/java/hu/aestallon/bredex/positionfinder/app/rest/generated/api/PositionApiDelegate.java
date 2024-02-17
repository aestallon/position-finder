package hu.aestallon.bredex.positionfinder.app.rest.generated.api;

import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiError;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.Position;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.PositionSearchResult;
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
 * A delegate to be called by the {@link PositionApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface PositionApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /position : Creates a new position.
     * Creates a new position.
     *
     * @param position  (required)
     * @return Ok (status code 201)
     *         or Invalid payload (status code 400)
     *         or Unauthorized (status code 401)
     * @see PositionApi#createPosition
     */
    default ResponseEntity<Position> createPosition(Position position) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"Accountant\", \"location\" : \"Budapest\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /position/{id} : Fetches a position.
     * Attempts to return a position by the denoted identifier. 
     *
     * @param id  (required)
     * @return Ok (status code 200)
     *         or Unauthorized (status code 401)
     *         or Not found (status code 404)
     * @see PositionApi#getPosition
     */
    default ResponseEntity<Position> getPosition(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"Accountant\", \"location\" : \"Budapest\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /position/search : Searches for matching positions
     * Search all persisted positions to find the ones with matching name/location. The search parameters are to be supplied in the query fragment of the URL. The operation returns a list of URLS (relative to the host of this API), on which the found positions can be inspected. 
     *
     * @param name  (optional)
     * @param location  (optional)
     * @return Ok (status code 200)
     *         or Invalid payload (status code 400)
     *         or Unauthorized (status code 401)
     * @see PositionApi#searchPosition
     */
    default ResponseEntity<PositionSearchResult> searchPosition(Optional<String> name,
        Optional<String> location) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"urls\" : [ \"http://example.com/aeiou\", \"http://example.com/aeiou\" ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
