package demo.quarkus.web.resource.vo;

import demo.quarkus.web.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2022-03-29
 */
@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Integer id;
    private String name;

    public static UserResponse fromEntity(User user) {
        var response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        return response;
    }
}
