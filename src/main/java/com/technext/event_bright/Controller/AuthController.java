package com.technext.event_bright.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.technext.event_bright.Annotations.Authorize;
import com.technext.event_bright.BrightAuth.JwtHelper;
import com.technext.event_bright.BrightAuth.PasswordHelper;
import com.technext.event_bright.Entity.UserLogin;
import com.technext.event_bright.Models.EmailDetails;
import com.technext.event_bright.Entity.User;
import com.technext.event_bright.Models.AuthData;
import com.technext.event_bright.Models.PasswordPair;
import com.technext.event_bright.Models.PasswordRecipient;
import com.technext.event_bright.Models.SignupData;
import com.technext.event_bright.Repository.UserLoginRepository;
import com.technext.event_bright.Repository.UserRepository;
import com.technext.event_bright.Services.Emil.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/Auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtHelper jwtHelper;
    private final PasswordHelper passwordHelper;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;


    @RequestMapping(
            value = "/sendSignupOtpEmail",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Object sendSignupOtpEmail(@RequestBody PasswordRecipient otpRecipient) {
        String emailAddress = Optional.ofNullable(otpRecipient.address).orElse("");

        try {
            PasswordPair otp = passwordHelper.createPasswordPairAuto(emailAddress);

            EmailDetails emailDetails = new EmailDetails();
            emailDetails.recipient = emailAddress;
            emailDetails.subject = "Email Verification";
            emailDetails.message = "Verification Code: " + otp.password;
            emailService.sendSimpleMail(emailDetails);

            return ImmutableMap.of("token", jwtHelper.getDataToken(ImmutableMap.of("loginId", emailAddress, "password", otp.passwordHash)));
        } catch (Exception e) {
            if (e instanceof MailParseException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }

    @Authorize
    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Object signup(@RequestBody SignupData signupData, @ModelAttribute AuthData authData) {
        try {
            passwordHelper.validatePasswordPair(new PasswordPair(signupData.otp, authData.password), signupData.email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid_credentials");
        }

        User user = new User();
        user.name = signupData.name;
        user.email = signupData.email;

        UserLogin userLogin = new UserLogin();
        userLogin.loginId = signupData.loginId;
        userLogin.salt = RandomStringUtils.random(8);
        PasswordPair passwordPair;
        try {
            passwordPair = passwordHelper.createPasswordPair(signupData.password, userLogin.salt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        userLogin.password = passwordPair.passwordHash;
        userLogin.user = userRepository.save(user);

        userLoginRepository.save(userLogin);

        return ImmutableMap.of("token", jwtHelper.getDataToken(new ObjectMapper().convertValue(userLogin.user, new TypeReference<Map<String, String>>() {})));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Object login(@RequestBody AuthData authData) {
        UserLogin userLogin = userLoginRepository.findByLoginId(authData.loginId);
        if (userLogin == null)  {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user_not_found");
        }

        try {
            passwordHelper.validatePasswordPair(new PasswordPair(authData.password, userLogin.password), userLogin.salt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("authorization_failed");
        }

        return ImmutableMap.of("token", jwtHelper.getDataToken(new ObjectMapper().convertValue(userLogin.user, new TypeReference<Map<String, String>>() {})));
    }
}
