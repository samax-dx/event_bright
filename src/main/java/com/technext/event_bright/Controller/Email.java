package com.technext.event_bright.Controller;

import com.technext.event_bright.Models.email.EmailDetails;
import com.technext.event_bright.Services.Emil.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@RequestMapping("/Email")
@RequiredArgsConstructor
public class Email {
    private final EmailService emailService;

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/sendSimpleMail",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Object sendSimpleMail(@RequestBody EmailDetails emailDetails) {
        try {
            emailService.sendSimpleMail(emailDetails);
            return new HashMap<>();
        } catch (MailException e) {
            if (e.getCause() instanceof MailPreparationException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }
}
