package demo.quarkus.web.it;

import demo.quarkus.web.resource.HelloResourceTest;
import io.quarkus.test.junit.NativeImageTest;

/**
 * @author Jin Zheng
 * @since 2022-03-28
 */
@NativeImageTest
public class NativeHelloResourceIT extends HelloResourceTest {
    public NativeHelloResourceIT() {
    }
}
