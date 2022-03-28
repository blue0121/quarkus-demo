package demo.quarkus.web.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author Jin Zheng
 * @since 2022-03-27
 */
@ApplicationScoped
public class HelloService {
	@Inject
	private Logger logger;

	@ConfigProperty(name = "hello.greeting")
	private String greeting;

	public String greeting(String name) {
		logger.info("Name: {}, Logger: {}", name, logger.hashCode());
		return greeting + name;
	}
}
