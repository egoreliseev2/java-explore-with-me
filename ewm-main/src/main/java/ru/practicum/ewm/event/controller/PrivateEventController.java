package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateEventRequest;
import ru.practicum.ewm.event.service.EventService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Validated
public class PrivateEventController {

    private final EventService eventService;
    private final String path = "/{eventId}";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable("userId") Long userId,
                                    @RequestBody @Validated NewEventDto newEventDto) {
        return eventService.createEvent(userId, newEventDto);
    }

    @GetMapping
    public List<EventShortDto> getUserEvents(@PathVariable("userId") Long userId,
                                             @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                             @RequestParam(defaultValue = "10") @Min(1) Integer size) {
        return eventService.getUserEvents(userId, from, size);
    }

    @GetMapping(path)
    public EventFullDto getUserEventById(@PathVariable("userId") Long userId,
                                         @PathVariable("eventId") Long eventId) {
        return eventService.getUserEventByEventId(userId, eventId);
    }

    @PatchMapping(path)
    public EventFullDto updateEventByEventId(@PathVariable("userId") Long userId,
                                             @PathVariable("eventId") Long eventId,
                                             @RequestBody UpdateEventRequest updateEventRequest) {
        return eventService.updateEventByEventId(userId, eventId, updateEventRequest);
    }

    @PostMapping(path + "/rating")
    public void addRating(@PathVariable("userId") Long userId,
                          @PathVariable("eventId") Long eventId,
                          @RequestParam Boolean isPositive) {
        eventService.addRating(userId, eventId, isPositive);
    }

    @DeleteMapping(path + "/rating")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRating(@PathVariable("userId") Long userId,
                             @PathVariable("eventId") Long eventId) {
        eventService.deleteRating(userId, eventId);
    }
}
