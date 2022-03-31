package demo.quarkus.web.resource;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import demo.quarkus.web.config.HelloProperties;
import demo.quarkus.web.resource.vo.GreetingResponse;
import demo.quarkus.web.service.HelloService;

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
	public GreetingResponse greeting(@PathParam("name") String name) {
		logger.info("Name: {}", name);
		var message = helloService.greeting(name);
		return new GreetingResponse(name, message);
	}

	@GET
	public String hello() {
		return helloProperties.welcome();
	}
}
