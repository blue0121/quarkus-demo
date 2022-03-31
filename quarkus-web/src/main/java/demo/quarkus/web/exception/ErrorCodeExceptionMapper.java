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
public class ErrorCodeExceptionMapper implements ExceptionMapper<ErrorCodeException> {
    @Inject
    Logger logger;

    public ErrorCodeExceptionMapper() {
    }

    @Override
    public Response toResponse(ErrorCodeException e) {
        logger.error("状态码: {}, 错误代码: {}, 信息: {}", e.getStatus(), e.getCode(), e.getMessage());
        return Response.status(e.getStatus()).entity(e.getJson()).type(MediaType.APPLICATION_JSON).build();
    }
}
