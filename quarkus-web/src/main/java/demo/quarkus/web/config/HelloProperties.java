package demo.quarkus.web.config;

import io.smallrye.config.ConfigMapping;

/**
 * @author Jin Zheng
 * @since 2022-03-28
 */
@ConfigMapping(prefix = "hello")
public interface HelloProperties {
	String greeting();
	String welcome();
}
