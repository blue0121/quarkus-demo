package demo.quarkus.web.resource;

import demo.quarkus.web.config.HelloProperties;
import demo.quarkus.web.service.HelloService;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * @author Jin Zheng
 * @since 2022-03-27
 */
@Path("/hello")
public class HelloResource {
	@Inject
	Logger logger;

	@Inject
	HelloProperties helloProperties;

	@Inject
	HelloService helloService;

	@GET
	@Path("/greeting/{name}")
	public String greeting(@PathParam("name") String name) {
		logger.info("Name: {}, Logger: {}", name, logger.hashCode());
		return helloService.greeting(name);
	}

	@GET
	public String hello() {
		return helloProperties.welcome();
	}
}
