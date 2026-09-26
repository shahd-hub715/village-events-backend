package com.villageevents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.villageevents.entity.EventChangeRequest;
import com.villageevents.entity.EventChangeRequestStatus;

public interface EventChangeRequestRepository
        extends JpaRepository<EventChangeRequest, Long> {

    List<EventChangeRequest> findByStatusOrderByCreatedAtAsc(
            EventChangeRequestStatus status);
}