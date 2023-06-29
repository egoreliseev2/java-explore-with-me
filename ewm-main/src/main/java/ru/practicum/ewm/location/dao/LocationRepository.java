package ru.practicum.ewm.location.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.location.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByLatAndLon(Float lat, Float lon);
}
