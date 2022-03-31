package demo.quarkus.web.exception;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Jin Zheng
 * @since 2022-03-31
 */
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Inject
    Logger logger;

    public WebApplicationExceptionMapper() {
    }

    @Override
    public Response toResponse(WebApplicationException e) {
        var errorCode = ErrorCode.WEB_ERROR;
        logger.error(errorCode.getMessage(e.getMessage()));
        var response = e.getResponse();
        return Response.status(response.getStatus())
                .entity(errorCode.toJson(e.getMessage()))
                .type(MediaType.APPLICATION_JSON).build();
    }
}
