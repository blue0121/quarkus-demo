package demo.quarkus.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * @author Jin Zheng
 * @since 2022-03-28
 */
@ApplicationScoped
public class LoggerConfig {
    public LoggerConfig() {
    }

    @Produces
    @Dependent
    public Logger logger(InjectionPoint ip) {
        return LoggerFactory.getLogger(ip.getBean().getBeanClass());
    }
}
