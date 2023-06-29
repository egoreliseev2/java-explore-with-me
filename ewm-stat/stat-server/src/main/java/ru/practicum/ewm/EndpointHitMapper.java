package ru.practicum.ewm;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.dto.EndPointHitDto;
import ru.practicum.ewm.model.EndpointHit;

@UtilityClass
public class EndpointHitMapper {
    public static EndPointHitDto toEndpointHitDto(EndpointHit endpointHit) {
        return EndPointHitDto.builder()
                .id(endpointHit.getId())
                .app(endpointHit.getApp())
                .uri(endpointHit.getUri())
                .ip(endpointHit.getIp())
                .timestamp(endpointHit.getTimestamp())
                .build();
    }

    public static EndpointHit toEndPointHit(EndPointHitDto endPointHitDto) {
        return EndpointHit.builder()
                .id(endPointHitDto.getId())
                .app(endPointHitDto.getApp())
                .uri(endPointHitDto.getUri())
                .ip(endPointHitDto.getIp())
                .timestamp(endPointHitDto.getTimestamp())
                .build();
    }
}
