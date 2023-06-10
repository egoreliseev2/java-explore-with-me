package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatsDto;
import lombok.RequiredArgsConstructor;
import ru.practicum.ewm.mapper.StatsMapper;
import ru.practicum.ewm.model.EndpointHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    @Transactional
    public EndpointHitDto create(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = statsRepository.save(StatsMapper.toEndpointHit(endpointHitDto));
        return StatsMapper.toEndpointHitDto(endpointHit);
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start,
                                       LocalDateTime end,
                                       List<String> uris,
                                       Boolean unique) {
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                return statsRepository.getHitsUniqueIp(start,end).stream().map(StatsMapper::toViewStatsDto)
                        .collect(Collectors.toList());
            }
            return statsRepository.getHits(start,end).stream().map(StatsMapper::toViewStatsDto)
                    .collect(Collectors.toList());
        }
        if (unique) {
            return statsRepository.getStatsUnique(start, end, uris).stream()
                    .map(StatsMapper::toViewStatsDto)
                    .collect(Collectors.toList());
        } else {
            return statsRepository.getStatsNotUnique(start, end, uris).stream()
                    .map(StatsMapper::toViewStatsDto)
                    .collect(Collectors.toList());
        }
    }
}
