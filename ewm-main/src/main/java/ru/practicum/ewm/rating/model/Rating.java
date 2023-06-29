package ru.practicum.ewm.rating.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(RatingCompositePK.class)
@Table(name = "ratings")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rating {
    @Id
    @Column(name = "user_id")
    Long userId;

    @Id
    @Column(name = "event_id")
    Long eventId;

    @Column(name = "is_positive")
    Boolean isPositive;

    @Column(name = "initiator_id")
    long initiatorId;
}
