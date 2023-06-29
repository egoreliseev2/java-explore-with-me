package ru.practicum.ewm.user.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.rating.model.Rating;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", insertable = false, nullable = false)
    @ToString.Exclude
    Set<Rating> ratings = new HashSet<>();
}
