package com.villageevents.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.villageevents.entity.Event;
import com.villageevents.entity.EventStatus;

public interface EventRepository extends JpaRepository<Event, Long> {

    boolean existsByEventDateAndStatus(LocalDate eventDate, EventStatus status);

    long countByEventDateAndIdNotAndStatusNot(
            LocalDate eventDate,
            Long id,
            EventStatus status);

    List<Event> findByStatusOrderByEventDateAsc(EventStatus status);

    List<Event> findByStatusAndEventDateBetweenOrderByEventDateAsc(
            EventStatus status,
            LocalDate startDate,
            LocalDate endDate);
}