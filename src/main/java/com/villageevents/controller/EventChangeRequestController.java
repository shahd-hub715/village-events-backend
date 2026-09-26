package com.villageevents.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.villageevents.entity.EventChangeRequest;
import com.villageevents.service.EventChangeRequestService;

@RestController
@RequestMapping("/api")
public class EventChangeRequestController {

    private final EventChangeRequestService service;

    public EventChangeRequestController(EventChangeRequestService service) {
        this.service = service;
    }

    @PostMapping("/change-requests")
    public EventChangeRequest createRequest(
            @RequestBody EventChangeRequest request) {

        return service.createRequest(request);
    }

    @GetMapping("/admin/change-requests/pending")
    public List<EventChangeRequest> getPendingRequests() {
        return service.getPendingRequests();
    }

    @PutMapping("/admin/change-requests/{id}/resolve")
    public EventChangeRequest resolveRequest(@PathVariable Long id) {
        return service.markResolved(id);
    }
}