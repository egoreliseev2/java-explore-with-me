package ru.practicum.ewm;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.dto.ViewStatDto;
import ru.practicum.ewm.model.ViewStat;

@UtilityClass
public class ViewStatMapper {
    public static ViewStatDto toViewStatDto(ViewStat viewStat) {
        return ViewStatDto.builder()
                .app(viewStat.getApp())
                .uri(viewStat.getUri())
                .hits(viewStat.getHits())
                .build();
    }
}
