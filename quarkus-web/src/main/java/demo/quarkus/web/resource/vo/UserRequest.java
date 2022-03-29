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
public class UserRequest {
    private String name;

    public User toEntity() {
        User user = new User();
        user.setName(name);
        return user;
    }
}
