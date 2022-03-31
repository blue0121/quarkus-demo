package demo.quarkus.web.interceptor;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @author Jin Zheng
 * @since 2022-03-31
 */
@Logged
@Interceptor
public class LoggingInterceptor {
    @Inject
    Logger logger;

    @AroundInvoke
    Object invoke(InvocationContext ctx) throws Exception {
        var method = ctx.getMethod();
        var now = System.currentTimeMillis();
        logger.info(">>> 进入方法: {}.{}()", method.getDeclaringClass().getSimpleName(), method.getName());
        Object ret = null;
        try {
            ret = ctx.proceed();
        } catch (Exception e) {
            logger.error("=== 异常: ", e);
            throw e;
        } finally {
            logger.info("<<< 离开方法: {}.{}(), 用时: {}ms", method.getDeclaringClass().getSimpleName(), method.getName(),
                    System.currentTimeMillis() - now);
        }
        return ret;
    }


}
