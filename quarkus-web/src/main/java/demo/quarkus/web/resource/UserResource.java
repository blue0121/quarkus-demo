package demo.quarkus.web.resource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

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
    @Path("{id}")
    public Response get(@PathParam("id") Integer id) {
        var user = userService.get(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(UserResponse.fromEntity(user)).build();
    }

    @POST
    public Response create(UserRequest request) {
        var user = request.toEntity();
        user = userService.save(user);
        return Response.ok(UserResponse.fromEntity(user)).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Integer id, UserRequest request) {
        var user = request.toEntity();
        user.setId(id);
        user = userService.save(user);
        return Response.ok(UserResponse.fromEntity(user)).build();
    }

}
