package com.technext.event_bright.svc_email;

import com.technext.event_bright.svc_email.payload.EmailDetails;


public interface EmailService {
    void sendSimpleMail(EmailDetails details);
}
