package demo.quarkus.web.resource;

import demo.quarkus.web.service.HelloService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

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

	@ConfigProperty(name = "hello.welcome")
	private String welcome;

	@Inject
	private HelloService helloService;

	@GET
	@Path("/greeting/{name}")
	public String greeting(@PathParam("name") String name) {
		return helloService.greeting(name);
	}

	@GET
	public String hello() {
		return welcome;
	}
}
