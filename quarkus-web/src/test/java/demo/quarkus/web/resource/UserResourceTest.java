package demo.quarkus.web.resource;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import demo.quarkus.web.entity.User;
import demo.quarkus.web.service.UserService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * @author Jin Zheng
 * @since 2022-03-31
 */
@QuarkusTest
public class UserResourceTest {
    @InjectMock
    UserService userService;

    private int id = 1;
    private String name = "test";

    public UserResourceTest() {
    }

    @Test
    public void testListAll() {
        RestAssured.given().when().get("/users").then()
                .statusCode(200)
                .body("$.size()", Matchers.is(0));

        List<User> list = List.of(this.create(id, name));
        Mockito.when(userService.listAll()).thenReturn(list);
        RestAssured.given().when().get("/users").then()
                .statusCode(200)
                .body("$.size()", Matchers.is(1))
                .body("[0].id", Matchers.is(id))
                .body("[0].name", Matchers.is(name));
    }

    private User create(Integer id, String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }

    @Test
    public void testGet() {
        RestAssured.given().pathParam("id", id).when().get("/users/{id}").then()
                .statusCode(404)
                .body("code", Matchers.is(400002))
                .body("message", Matchers.is("用户 [" + id + "] 不存在"));

        Mockito.when(userService.get(Mockito.eq(1))).thenReturn(this.create(id, name));
        RestAssured.given().pathParam("id", id).when().get("/users/{id}").then()
                .statusCode(200)
                .body("id", Matchers.is(id))
                .body("name", Matchers.is(name));
    }

    @Test
    public void testSave() {
        var user = this.create(id, name);
        Mockito.when(userService.save(Mockito.any())).thenReturn(user);

        RestAssured.given().when().body(id).contentType(ContentType.JSON).post("/users").then()
                .statusCode(400)
                .body("code", Matchers.is(400002))
                .body("message", Matchers.is("Json格式错误"));

        RestAssured.given().when().body(user).contentType(ContentType.JSON).post("/users").then()
                .statusCode(201)
                .body("id", Matchers.is(id))
                .body("name", Matchers.is(name));
    }

    @Test
    public void testUpdate() {
        var user = this.create(id, name);
        RestAssured.given().pathParam("id", id).when().body(user).contentType(ContentType.JSON).put("/users/{id}").then()
                .statusCode(404)
                .body("code", Matchers.is(400002))
                .body("message", Matchers.is("用户 [" + id + "] 不存在"));

        Mockito.when(userService.get(Mockito.any())).thenReturn(user);
        Mockito.when(userService.save(Mockito.any())).thenReturn(user);

        RestAssured.given().pathParam("id", id).when().body(user).contentType(ContentType.JSON).put("/users/{id}").then()
                .statusCode(200)
                .body("id", Matchers.is(id))
                .body("name", Matchers.is(name));
    }

    @Test
    public void testDelete() {
        var user = this.create(id, name);
        RestAssured.given().pathParam("id", id).when().delete("/users/{id}").then()
                .statusCode(404)
                .body("code", Matchers.is(400002))
                .body("message", Matchers.is("用户 [" + id + "] 不存在"));

        Mockito.when(userService.delete(Mockito.any())).thenReturn(1);

        RestAssured.given().pathParam("id", id).when().delete("/users/{id}").then()
                .statusCode(204);
    }


}
