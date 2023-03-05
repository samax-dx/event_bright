package com.technext.event_bright.svc_event;

import com.technext.event_bright.svc_auth.contracts.AuthMessagingService;
import com.technext.event_bright.svc_email.EmailService;
import com.technext.event_bright.svc_email.payload.EmailDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AuthMessagingServiceImpl implements AuthMessagingService {
    private final EmailService emailService;

    @Override
    public void sendMessage(String message, String recipient, String subject) {
        emailService.sendSimpleMail(new EmailDetails(recipient, message, subject));
    }

}
