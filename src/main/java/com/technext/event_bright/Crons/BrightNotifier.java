package com.technext.event_bright.Crons;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class BrightNotifier {
    @Scheduled(cron = "0 * * * * *")
    public void notifyEvent() {
        System.out.println("-----IIIII-----");
    }
}
