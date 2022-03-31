package demo.quarkus.web.exception;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Jin Zheng
 * @since 2022-03-31
 */
@Provider
public class ClientErrorExceptionMapper implements ExceptionMapper<ClientErrorException> {
    @Inject
    Logger logger;

    public ClientErrorExceptionMapper() {
    }

    @Override
    public Response toResponse(ClientErrorException e) {
        var errorCode = ErrorCode.CLIENT_ERROR;
        logger.error(errorCode.getMessage(e.getMessage()));
        var response = e.getResponse();
        return Response.status(response.getStatus())
                .entity(errorCode.toJson(e.getMessage()))
                .type(MediaType.APPLICATION_JSON).build();
    }
}
