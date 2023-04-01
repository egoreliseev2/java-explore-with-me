package ewm.service;

import dto.EndpointHitDto;
import dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    EndpointHitDto create(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getStats(LocalDateTime start,
                                LocalDateTime end,
                                List<String> uris,
                                Boolean unique);
}
