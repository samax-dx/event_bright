package com.technext.event_bright.Models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    public String recipient;
    public String message;
    public String subject;
}
