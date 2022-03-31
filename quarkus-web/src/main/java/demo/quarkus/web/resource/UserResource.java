package demo.quarkus.web.resource;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import demo.quarkus.web.exception.ErrorCode;
import demo.quarkus.web.resource.vo.UserRequest;
import demo.quarkus.web.resource.vo.UserResponse;
import demo.quarkus.web.service.UserService;

/**
 * @author Jin Zheng
 * @since 2022-03-29
 */
@Path("/users")
@ApplicationScoped
public class UserResource {
    @Inject
    UserService userService;

    public UserResource() {
    }

    @GET
    public List<UserResponse> listAll() {
        var list = userService.listAll();
        return UserResponse.fromEntity(list);
    }

    @GET
    @Path("{id}")
    public UserResponse get(@PathParam("id") Integer id) {
        var user = userService.get(id);
        if (user == null) {
            throw ErrorCode.USER_NOT_EXIST.newException(id);
        }
        return UserResponse.fromEntity(user);
    }

    @POST
    public Response create(UserRequest request) {
        var user = request.toEntity();
        user = userService.save(user);
        return Response.ok(UserResponse.fromEntity(user)).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    public UserResponse update(@PathParam("id") Integer id, UserRequest request) {
        var u = userService.get(id);
        if (u == null) {
            throw ErrorCode.USER_NOT_EXIST.newException(id);
        }

        var user = request.toEntity();
        user.setId(id);
        user = userService.save(user);
        return UserResponse.fromEntity(user);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        int rs = userService.delete(id);
        if (rs == 0) {
            throw ErrorCode.USER_NOT_EXIST.newException(id);
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
