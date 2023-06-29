package ru.practicum.ewm.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCompilationDto {
    Set<Long> events;

    @Builder.Default
    Boolean pinned = false;

    @NotBlank(message = "title cannot be empty or null.")
    @Size(min = 1, max = 50, message = "title cannot be less 1 or more than 50.")
    String title;
}
