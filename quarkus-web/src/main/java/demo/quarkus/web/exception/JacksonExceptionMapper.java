package demo.quarkus.web.exception;

import com.fasterxml.jackson.core.JacksonException;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Jin Zheng
 * @since 2022-03-31
 */
@Provider
public class JacksonExceptionMapper implements ExceptionMapper<JacksonException> {
    @Inject
    Logger logger;

    public JacksonExceptionMapper() {
    }

    @Override
    public Response toResponse(JacksonException e) {
        var errorCode = ErrorCode.INVALID_JSON;
        logger.error(errorCode.getMessage(), e);
        return Response.status(errorCode.getStatus()).entity(errorCode.toJson()).type(MediaType.APPLICATION_JSON).build();
    }
}
