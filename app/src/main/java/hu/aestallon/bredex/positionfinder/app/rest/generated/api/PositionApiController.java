package hu.aestallon.bredex.positionfinder.app.rest.generated.api;

import hu.aestallon.bredex.positionfinder.app.rest.generated.model.ApiError;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.Position;
import hu.aestallon.bredex.positionfinder.app.rest.generated.model.PositionSearchResult;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.positionFinder.base-path:/api/v1}")
public class PositionApiController implements PositionApi {

    private final PositionApiDelegate delegate;

    public PositionApiController(@Autowired(required = false) PositionApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new PositionApiDelegate() {});
    }

    @Override
    public PositionApiDelegate getDelegate() {
        return delegate;
    }

}
