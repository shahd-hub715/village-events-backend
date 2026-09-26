package com.villageevents.controller;

import java.util.List;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.villageevents.entity.Event;
import com.villageevents.dto.AdminEventResponse;
import com.villageevents.service.EventService;

@RestController
@RequestMapping("/api/admin/events")
public class AdminEventController {

    private final EventService eventService;

    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/pending")
    public List<AdminEventResponse> getPendingEvents() {
        return eventService.getPendingEvents();
    }

    @GetMapping
    public List<AdminEventResponse> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PutMapping("/{id}/approve")
    public Event approveEvent(@PathVariable Long id) {
        return eventService.approveEvent(id);
    }

    @PutMapping("/{id}/reject")
    public Event rejectEvent(@PathVariable Long id) {
        return eventService.rejectEvent(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @PutMapping("/{id}")
    public Event updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody Event updatedEvent) {

        return eventService.updateEvent(id, updatedEvent);
    }
}