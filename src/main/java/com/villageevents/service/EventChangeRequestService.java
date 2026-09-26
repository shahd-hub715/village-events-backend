package com.villageevents.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.villageevents.entity.EventChangeRequest;
import com.villageevents.entity.EventChangeRequestStatus;
import com.villageevents.repository.EventChangeRequestRepository;

@Service
public class EventChangeRequestService {

    private final EventChangeRequestRepository repository;
    private final EmailService emailService;

    public EventChangeRequestService(
            EventChangeRequestRepository repository,
            EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    public EventChangeRequest createRequest(EventChangeRequest request) {
        request.setStatus(EventChangeRequestStatus.PENDING);

        LocalDateTime now = LocalDateTime.now();
        request.setCreatedAt(now);
        request.setUpdatedAt(now);

        EventChangeRequest savedRequest = repository.save(request);

        String requestTypeLabel =
                request.getRequestType().name().equals("EDIT")
                        ? "تعديل"
                        : "حذف";

        emailService.sendEmail(
                "طلب " + requestTypeLabel + " لمناسبة",
                """
                <h2>وصل طلب %s لمناسبة</h2>
                <p><strong>الاسم:</strong> %s</p>
                <p><strong>تاريخ المناسبة:</strong> %s</p>
                <p><strong>تفاصيل الطلب:</strong> %s</p>
                <p>ادخلي إلى لوحة الإدارة لمراجعة الطلب.</p>
                """.formatted(
                        requestTypeLabel,
                        request.getPersonName(),
                        request.getEventDate(),
                        request.getRequestedChanges())
        );

        return savedRequest;
    }

    public List<EventChangeRequest> getPendingRequests() {
        return repository.findByStatusOrderByCreatedAtAsc(
                EventChangeRequestStatus.PENDING);
    }

    public EventChangeRequest markResolved(Long id) {
        EventChangeRequest request = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Change request not found"));

        request.setStatus(EventChangeRequestStatus.RESOLVED);
        request.setUpdatedAt(LocalDateTime.now());

        return repository.save(request);
    }
}