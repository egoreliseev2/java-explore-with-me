package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.ViewStatMapper;
import ru.practicum.ewm.dao.EndpointHitRepository;
import ru.practicum.ewm.dto.EndPointHitDto;
import ru.practicum.ewm.dto.ViewStatDto;
import ru.practicum.ewm.exceptions.TimestampException;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.ViewStat;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.EndpointHitMapper.toEndPointHit;
import static ru.practicum.ewm.EndpointHitMapper.toEndpointHitDto;

@Service
@RequiredArgsConstructor
public class EndpointHitServiceImpl implements EndpointHitService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final EndpointHitRepository endpointHitRepository;

    @Override
    @Transactional
    public EndPointHitDto create(EndPointHitDto endPointHitDto) {
        EndpointHit endpointHit = toEndPointHit(endPointHitDto);

        return toEndpointHitDto(endpointHitRepository.save(endpointHit));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatDto> getStats(String start, String end, List<String> uris, Boolean unique) {

        LocalDateTime startDate = checkDate(start);
        LocalDateTime endDate = checkDate(end);

        if (startDate.isAfter(endDate)) {
            throw new TimestampException("End time cannot be before start time.");
        }

        List<ViewStat> result;
        if (unique) {
            if (uris.isEmpty()) {
                result = endpointHitRepository.getViewStatsUnique(startDate, endDate);
            } else {
                result = endpointHitRepository.getViewStatsByUrisAndUnique(startDate, endDate, uris);
            }
        } else {
            if (uris.isEmpty()) {
                result = endpointHitRepository.getViewStats(startDate, endDate);
            } else {
                result = endpointHitRepository.getViewStatsByUris(startDate, endDate, uris);
            }
        }

        return result.stream()
                .map(ViewStatMapper::toViewStatDto)
                .collect(Collectors.toList());
    }

    private LocalDateTime checkDate(String dateString) {
        if (dateString == null) {
            throw new TimestampException("Start date or end date cannot be null");
        }
        return LocalDateTime.parse(URLDecoder.decode(dateString, StandardCharsets.UTF_8), FORMATTER);
    }
}
