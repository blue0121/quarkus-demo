package demo.quarkus.web.resource;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import demo.quarkus.web.service.HelloService;

/**
 * @author Jin Zheng
 * @since 2022-03-27
 */
@Path("/hello")
public class HelloResource {
	@Inject
	private Logger logger;

	@ConfigProperty(name = "hello.welcome")
	private String welcome;

	@Inject
	private HelloService helloService;

	@GET
	@Path("/greeting/{name}")
	public String greeting(@PathParam("name") String name) {
		logger.info("Name: {}, Logger: {}", name, logger.hashCode());
		return helloService.greeting(name);
	}

	@GET
	public String hello() {
		return welcome;
	}
}
