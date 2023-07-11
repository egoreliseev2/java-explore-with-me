package ru.practicum.ewm.user;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.rating.model.Rating;

import java.util.Set;

@UtilityClass
public class RatingScore {
    public static Integer calculate(Set<Rating> ratings) {
        Integer result = 0;
        for (Rating rating : ratings) {
            if (rating.getIsPositive()) {
                result++;
            } else {
                result--;
            }
        }
        return result;
    }
}
