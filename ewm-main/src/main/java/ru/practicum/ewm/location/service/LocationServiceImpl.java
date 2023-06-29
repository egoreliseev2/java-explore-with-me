package ru.practicum.ewm.location.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.location.LocationMapper;
import ru.practicum.ewm.location.dao.LocationRepository;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.model.Location;

import static ru.practicum.ewm.location.LocationMapper.toLocation;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public LocationDto createLocation(LocationDto locationDto) {
        Location location = toLocation(locationDto);

        return LocationMapper.toLocationDto(locationRepository.save(location));
    }
}
