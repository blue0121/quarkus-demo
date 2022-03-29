package demo.quarkus.web.resource;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import demo.quarkus.web.service.HelloService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.restassured.RestAssured;

/**
 * @author Jin Zheng
 * @since 2022-03-28
 */
@QuarkusTest
public class HelloResourceTest {
    @InjectSpy
    HelloService helloService;

    public HelloResourceTest() {
    }

    @Test
    public void testHello() {
        RestAssured.given().when().get("/hello").then()
                .statusCode(200)
                .body(CoreMatchers.is("Hello World"));
    }

    @Test
    public void testGreetingMock() {
        String uuid = UUID.randomUUID().toString();
        Mockito.when(helloService.greeting(Mockito.eq(uuid))).thenReturn("Hello");
        RestAssured.given().pathParam("name", uuid).when().get("/hello/greeting/{name}").then()
                .statusCode(200)
                .body("name", CoreMatchers.is(uuid))
                .body("message", CoreMatchers.is("Hello"));
    }

    @Test
    public void testGreeting() {
        String uuid = UUID.randomUUID().toString();
        RestAssured.given().pathParam("name", uuid).when().get("/hello/greeting/{name}").then()
                .statusCode(200)
                .body("name", Matchers.is(uuid))
                .body("message", Matchers.is("Hello," + uuid));
    }
}
