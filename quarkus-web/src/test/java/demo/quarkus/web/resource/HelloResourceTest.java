package demo.quarkus.web.resource;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

/**
 * @author Jin Zheng
 * @since 2022-03-28
 */
@QuarkusTest
public class HelloResourceTest {
    public HelloResourceTest() {
    }

    @Test
    public void testHello() {
        RestAssured.given().when().get("/hello").then()
                .statusCode(200)
                .body(CoreMatchers.is("Hello World"));
    }

    @Test
    public void testGreeting() {
        String uuid = UUID.randomUUID().toString();
        RestAssured.given().pathParam("name", uuid).when().get("/hello/greeting/{name}").then()
                .statusCode(200)
                .body(CoreMatchers.is("Hello," + uuid));
    }
}
