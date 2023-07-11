package ru.practicum.ewm.event.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.WhereJoinTable;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.rating.model.Rating;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "events")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "annotation", nullable = false)
    String annotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    @Column(name = "created_on", nullable = false)
    @CreationTimestamp
    LocalDateTime createdOn;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "event_date", nullable = false)
    LocalDateTime eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    User initiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    Location location;

    @Column(name = "paid", nullable = false)
    Boolean paid;

    @Column(name = "participant_limit", nullable = false)
    Integer participantLimit;

    @Column(name = "published_on", nullable = false)
    LocalDateTime publishedOn;

    @Column(name = "request_moderation", nullable = false)
    Boolean requestModeration;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    EventState state;

    @Column(name = "title", nullable = false)
    String title;

    @WhereJoinTable(clause = "status='CONFIRMED'")
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "requests", joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "requester_id"))
    Set<User> participants = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "event_id")
    Set<Rating> ratings;
}