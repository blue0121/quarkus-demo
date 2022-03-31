package demo.quarkus.web.exception;

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
public class UnknownExceptionMapper implements ExceptionMapper<Exception> {
    @Inject
    Logger logger;

    public UnknownExceptionMapper() {
    }

    @Override
    public Response toResponse(Exception e) {
        logger.error("未知错误: ", e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ErrorCode.SYSTEM_ERROR.toJson())
                .type(MediaType.APPLICATION_JSON).build();
    }
}
