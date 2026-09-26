package com.villageevents.dto;

import java.time.LocalDate;

import com.villageevents.entity.EventType;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicEventResponse {

    private Long id;
    private String personName;
    private EventType eventType;
    private LocalDate eventDate;
    private String location;

    public PublicEventResponse(
            Long id,
            String personName,
            EventType eventType,
            LocalDate eventDate,
            String location) {

        this.id = id;
        this.personName = personName;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getPersonName() {
        return personName;
    }

    public EventType getEventType() {
        return eventType;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public String getLocation() {
        return location;
    }
}