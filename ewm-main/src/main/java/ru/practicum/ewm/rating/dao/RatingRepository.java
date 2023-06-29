package ru.practicum.ewm.rating.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.rating.model.Rating;
import ru.practicum.ewm.rating.model.RatingCompositePK;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingCompositePK> {
    void deleteByUserIdAndEventId(Long userId, Long eventId);
}
