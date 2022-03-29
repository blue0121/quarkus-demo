package demo.quarkus.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@Entity
@Table(name = "`user`")
public class User {
    @Id
    @SequenceGenerator(name = "user_seq", sequenceName="user_id_seq")
    @GeneratedValue(generator = "user_seq")
    @Column(name = "id")
    Integer id;

    @Column(name = "name")
    String name;
}
