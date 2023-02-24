package com.technext.event_bright.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.technext.event_bright.BrightAuth.JwtHelper;
import com.technext.event_bright.BrightAuth.PasswordHelper;
import com.technext.event_bright.Models.EmailDetails;
import com.technext.event_bright.Entity.User;
import com.technext.event_bright.Models.AuthData;
import com.technext.event_bright.Models.PasswordPair;
import com.technext.event_bright.Models.PasswordRecipient;
import com.technext.event_bright.Models.SignupData;
import com.technext.event_bright.Repository.UserRepository;
import com.technext.event_bright.Services.Emil.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailPreparationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/Auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtHelper jwtHelper;
    private final PasswordHelper passwordHelper;
    private final EmailService emailService;
    private final UserRepository userRepository;


    @RequestMapping(
            value = "/sendSignupOtpEmail",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Object sendSignupOtpEmail(@RequestBody PasswordRecipient otpRecipient) {
        try {
            PasswordPair otp = passwordHelper.createPasswordPairAuto(otpRecipient.address);

            EmailDetails emailDetails = new EmailDetails();
            emailDetails.recipient = otpRecipient.address;
            emailDetails.subject = "Email Verification";
            emailDetails.message = "Verification Code: " + otp.password;
            emailService.sendSimpleMail(emailDetails);

            return ImmutableMap.of("otpCiphered", otp.passwordHash);
        } catch (Exception e) {
            if (e.getCause() instanceof MailPreparationException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }

    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Object signup(@RequestBody SignupData signupData) {
        try {
            passwordHelper.validatePasswordPair(new PasswordPair(signupData.otp, signupData.otpCiphered), signupData.email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid_credentials");
        }

        User user = new User();
        user.name = signupData.name;
        user.email = signupData.email;
        userRepository.save(user);

        return ImmutableMap.of("token", jwtHelper.getDataToken(new ObjectMapper().convertValue(user, new TypeReference<Map<String, String>>() {})));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Object login(@RequestBody Map<String, String> payload) {
        AuthData rqData = new ObjectMapper().convertValue(payload, AuthData.class);
        AuthData dbData = new AuthData("admin", "admin");

        boolean isAuthentic = dbData.loginId.equals(rqData.loginId) && dbData.password.equals(rqData.password);

        if (isAuthentic) {
            User user = new User();
            user.email = "admin@technext.com";
            user.name = "admin";

            return ImmutableMap.of("token", jwtHelper.getDataToken(new ObjectMapper().convertValue(user, new TypeReference<Map<String, String>>() {})));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid_credentials");
        }
    }
}
