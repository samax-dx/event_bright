package com.technext.event_bright.Services.Emil;

import com.technext.event_bright.Models.email.EmailDetails;


public interface EmailService {
    void sendSimpleMail(EmailDetails details);
}
