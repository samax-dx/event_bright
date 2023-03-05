package com.technext.event_bright.svc_email.payload;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    public String recipient;
    public String message;
    public String subject;
}
