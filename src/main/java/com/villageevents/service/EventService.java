package com.villageevents.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.villageevents.dto.AdminEventResponse;
import com.villageevents.dto.EventSubmissionResponse;
import com.villageevents.dto.PublicEventResponse;
import com.villageevents.entity.Event;
import com.villageevents.entity.EventStatus;
import com.villageevents.exception.EventNotFoundException;
import com.villageevents.repository.EventRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EmailService emailService;

    public EventService(
            EventRepository eventRepository,
            EmailService emailService) {
        this.eventRepository = eventRepository;
        this.emailService = emailService;
    }

    public EventSubmissionResponse saveEvent(Event event) {

        if (event.getEventDate().isBefore(LocalDate.now(ZoneId.of("Asia/Jerusalem")))) {
            throw new IllegalArgumentException("Event date cannot be in the past");
        }

        boolean hasConflict = eventRepository.existsByEventDateAndStatus(
                event.getEventDate(),
                EventStatus.APPROVED);

        event.setStatus(EventStatus.PENDING);

        LocalDateTime now = LocalDateTime.now();
        event.setCreatedAt(now);
        event.setUpdatedAt(now);

        eventRepository.save(event);

        emailService.sendEmail(
                "مناسبة جديدة بانتظار المراجعة",
                """
                <h2>وصلت مناسبة جديدة</h2>
                <p><strong>الاسم:</strong> %s</p>
                <p><strong>التاريخ:</strong> %s</p>
                <p><strong>المكان:</strong> %s</p>
                <p>ادخلي إلى لوحة الإدارة لمراجعة المناسبة.</p>
                """.formatted(
                        event.getPersonName(),
                        event.getEventDate(),
                        event.getLocation() == null || event.getLocation().isBlank()
                                ? "غير محدد"
                                : event.getLocation())
        );

        if (hasConflict) {
            return new EventSubmissionResponse(
                    true,
                    "يوجد مناسبة أخرى مسجلة بهذا التاريخ، وتم إرسال طلبك للمراجعة");
        }

        return new EventSubmissionResponse(
                false,
                "تم إرسال المناسبة للمراجعة بنجاح");
    }

    public List<AdminEventResponse> getPendingEvents() {

        List<Event> events =
                eventRepository.findByStatusOrderByEventDateAsc(EventStatus.PENDING);

        return events.stream()
                .map(this::toAdminEventResponse)
                .toList();
    }

    public List<AdminEventResponse> getAllEvents() {

        List<Event> events = eventRepository.findAll();

        return events.stream()
                .map(this::toAdminEventResponse)
                .toList();
    }

    public List<PublicEventResponse> getApprovedEventsByYear(int year) {

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Jerusalem"));
        LocalDate yearStart = LocalDate.of(year, 1, 1);

        // Hide past events publicly; events dated today remain visible all day.
        LocalDate startDate = yearStart.isBefore(today) ? today : yearStart;
        LocalDate endDate = LocalDate.of(year, 12, 31);

        List<Event> events =
                eventRepository.findByStatusAndEventDateBetweenOrderByEventDateAsc(
                        EventStatus.APPROVED,
                        startDate,
                        endDate);

        return events.stream()
                .map(event -> new PublicEventResponse(
                        event.getId(),
                        event.getPersonName(),
                        event.getEventType(),
                        event.getEventDate(),
                        event.getLocation()))
                .toList();
    }

    public Event approveEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        event.setStatus(EventStatus.APPROVED);
        event.setUpdatedAt(LocalDateTime.now());

        return eventRepository.save(event);
    }

    public Event rejectEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        event.setStatus(EventStatus.REJECTED);
        event.setUpdatedAt(LocalDateTime.now());

        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        eventRepository.delete(event);
    }

    public Event updateEvent(Long id, Event updatedEvent) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        if (updatedEvent.getEventDate().isBefore(LocalDate.now(ZoneId.of("Asia/Jerusalem")))) {
            throw new IllegalArgumentException("Event date cannot be in the past");
        }

        event.setPersonName(updatedEvent.getPersonName());
        event.setEventType(updatedEvent.getEventType());
        event.setEventDate(updatedEvent.getEventDate());
        event.setLocation(updatedEvent.getLocation());
        event.setContactPhone(updatedEvent.getContactPhone());
        event.setNotes(updatedEvent.getNotes());
        event.setUpdatedAt(LocalDateTime.now());

        return eventRepository.save(event);
    }

    private AdminEventResponse toAdminEventResponse(Event event) {

        long sameDateCount =
                eventRepository.countByEventDateAndIdNotAndStatusNot(
                        event.getEventDate(),
                        event.getId(),
                        EventStatus.REJECTED);

        boolean hasDateConflict = sameDateCount > 0;

        return new AdminEventResponse(
                event.getId(),
                event.getPersonName(),
                event.getEventType(),
                event.getEventDate(),
                event.getLocation(),
                event.getContactPhone(),
                event.getNotes(),
                event.getStatus(),
                event.getCreatedAt(),
                event.getUpdatedAt(),
                hasDateConflict,
                (int) sameDateCount);
    }
}