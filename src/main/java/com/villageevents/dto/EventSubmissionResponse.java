package com.villageevents.dto;

public class EventSubmissionResponse {

    private boolean hasConflict;
    private String message;

    public EventSubmissionResponse(boolean hasConflict, String message) {
        this.hasConflict = hasConflict;
        this.message = message;
    }

    public boolean isHasConflict() {
        return hasConflict;
    }

    public String getMessage() {
        return message;
    }
}