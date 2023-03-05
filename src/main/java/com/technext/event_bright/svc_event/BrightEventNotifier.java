package com.technext.event_bright.svc_event;

import com.technext.event_bright.svc_event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BrightEventNotifier {
    private final EventRepository eventRepository;

    @Scheduled(cron = "0 * * * * *")
    public void notifyEvent() {
        System.out.println("-----IIIII-----");
    }
}
