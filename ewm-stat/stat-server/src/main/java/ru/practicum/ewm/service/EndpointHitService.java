package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.EndPointHitDto;
import ru.practicum.ewm.dto.ViewStatDto;

import java.util.List;

public interface EndpointHitService {
    EndPointHitDto create(EndPointHitDto endPointHitDto);

    List<ViewStatDto> getStats(String start, String end, List<String> uris, Boolean unique);
}
