package com.villageevents.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.villageevents.entity.EventStatus;
import com.villageevents.entity.EventType;

public class AdminEventResponse {

    private Long id;
    private String personName;
    private EventType eventType;
    private LocalDate eventDate;
    private String location;
    private String contactPhone;
    private String notes;
    private EventStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean hasDateConflict;
    private int sameDateCount;

    public AdminEventResponse(
            Long id,
            String personName,
            EventType eventType,
            LocalDate eventDate,
            String location,
            String contactPhone,
            String notes,
            EventStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            boolean hasDateConflict,
            int sameDateCount) {

        this.id = id;
        this.personName = personName;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.location = location;
        this.contactPhone = contactPhone;
        this.notes = notes;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.hasDateConflict = hasDateConflict;
        this.sameDateCount = sameDateCount;
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

    public String getContactPhone() {
        return contactPhone;
    }

    public String getNotes() {
        return notes;
    }

    public EventStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isHasDateConflict() {
        return hasDateConflict;
    }

    public int getSameDateCount() {
        return sameDateCount;
    }
}