package com.villageevents.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.villageevents.dto.EventSubmissionResponse;
import com.villageevents.dto.PublicEventResponse;
import com.villageevents.entity.Event;
import com.villageevents.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public EventSubmissionResponse createEvent(@Valid @RequestBody Event event) {
        return eventService.saveEvent(event);
    }

    @GetMapping
    public List<PublicEventResponse> getApprovedEventsByYear(
            @RequestParam int year) {

        return eventService.getApprovedEventsByYear(year);
    }
}