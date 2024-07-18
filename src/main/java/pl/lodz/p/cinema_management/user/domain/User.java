package pl.lodz.p.cinema_management.user.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.lodz.p.cinema_management.annotation.ddd.AggregateRoot;

@AggregateRoot
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

}
