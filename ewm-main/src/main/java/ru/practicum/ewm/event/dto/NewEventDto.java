package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.location.dto.LocationDto;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {
    @NotBlank(message = "annotation cannot be empty or null.")
    @Size(min = 20, max = 2000, message = "annotation cannot be less than 20 or more than 2000.")
    String annotation;

    @NotNull(message = "category id cannot be null.")
    @Positive(message = "category id cannot be negative or zero.")
    Long category;

    @NotBlank(message = "description be empty or null.")
    @Size(min = 20, max = 7000, message = "description cannot be less than 20 or more than 7000.")
    String description;

    @NotNull(message = "event date cannot be null.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    @NotNull(message = "location cannot be null.")
    LocationDto location;

    @Builder.Default
    Boolean paid = false;

    @Builder.Default
    @PositiveOrZero(message = "participant limit cannot be negative.")
    Integer participantLimit = 0;

    @Builder.Default
    Boolean requestModeration = true;

    @NotBlank(message = "title cannot be empty or null.")
    @Size(min = 3, max = 120, message = "title cannot be less than 3 or more than 120.")
    String title;
}
