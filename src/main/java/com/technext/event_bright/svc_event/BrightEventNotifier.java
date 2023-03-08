package com.technext.event_bright.svc_event;

import com.technext.event_bright.svc_email.EmailService;
import com.technext.event_bright.svc_email.payload.EmailDetails;
import com.technext.event_bright.svc_event.entity.Event;
import com.technext.event_bright.svc_event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Component
@RequiredArgsConstructor
public class BrightEventNotifier {
    private final EventRepository eventRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void notifyEvent() {
        List<Event> events = eventRepository.findEventsByDateBetween(
                LocalDateTime.now().plusMinutes(30).truncatedTo(ChronoUnit.MINUTES).minus(1, ChronoUnit.SECONDS),
                LocalDateTime.now().plusMinutes(31).truncatedTo(ChronoUnit.MINUTES)
        );

        events.forEach(event -> {
            EmailDetails details = new EmailDetails();
            details.subject = "Event " + event.getName() + " Reminder";
            details.message = "Event " + event.getName() + " is starting at " + event.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            event.getEventParticipants().forEach(participant -> {
                details.recipient = participant.getEmail();
                emailService.sendSimpleMail(details);
            });
        });
    }
}
