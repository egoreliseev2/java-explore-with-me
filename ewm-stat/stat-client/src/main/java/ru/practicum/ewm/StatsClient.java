package ru.practicum.ewm;

import org.springframework.http.ResponseEntity;
import ru.practicum.ewm.dto.EndPointHitDto;
import ru.practicum.ewm.dto.ViewStatDto;

import java.util.List;

public interface StatsClient {
    ResponseEntity<EndPointHitDto> save(String app, String uri, String ip, String timestamp);

    List<ViewStatDto> get(String start, String end, List<String> uris, Boolean unique);
}
