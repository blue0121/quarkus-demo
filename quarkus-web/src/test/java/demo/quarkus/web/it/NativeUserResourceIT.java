package demo.quarkus.web.it;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import demo.quarkus.web.entity.User;
import demo.quarkus.web.resource.vo.UserResponse;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * @author Jin Zheng
 * @since 2022-03-31
 */
@QuarkusIntegrationTest
public class NativeUserResourceIT {

    private int id = 1;
    private String name = "test";
    private String newname = "newtest";

    public NativeUserResourceIT() {
    }

    private User create(Integer id, String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }

    @Test
    public void testAll() {
        var user = this.create(id, name);
        var response = RestAssured.given().when().body(user).contentType(ContentType.JSON).post("/users").then()
                .statusCode(201)
                .body("name", Matchers.is(name))
                .extract().body().as(UserResponse.class);

        RestAssured.given().pathParam("id", response.getId()).when().get("/users/{id}").then()
                .statusCode(200)
                .body("id", Matchers.is(response.getId()))
                .body("name", Matchers.is(name));

        RestAssured.given().when().get("/users").then()
                .statusCode(200)
                .body("$.size()", Matchers.is(1))
                .body("[0].id", Matchers.is(response.getId()))
                .body("[0].name", Matchers.is(name));

        var newUser = this.create(response.getId(), newname);
        RestAssured.given().pathParam("id", response.getId()).when().body(newUser).contentType(ContentType.JSON)
                .put("/users/{id}").then()
                .statusCode(200)
                .body("id", Matchers.is(response.getId()))
                .body("name", Matchers.is(newname));

        RestAssured.given().pathParam("id", response.getId()).when().delete("/users/{id}").then()
                .statusCode(204);

        RestAssured.given().pathParam("id", response.getId()).when().get("/users/{id}").then()
                .statusCode(404)
                .body("code", Matchers.is(400002))
                .body("message", Matchers.is("用户 [" + response.getId() + "] 不存在"));
    }

}
