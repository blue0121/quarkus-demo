package demo.quarkus.web.resource.vo;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class GreetingResponse {
    private String name;
    private String message;
}
