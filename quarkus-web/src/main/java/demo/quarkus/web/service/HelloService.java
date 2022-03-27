package demo.quarkus.web.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author Jin Zheng
 * @since 2022-03-27
 */
@ApplicationScoped
public class HelloService {
	@ConfigProperty(name = "hello.greeting")
	private String greeting;

	public String greeting(String name) {
		return greeting + name;
	}
}
