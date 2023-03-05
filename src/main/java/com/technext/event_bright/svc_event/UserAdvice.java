package com.technext.event_bright.svc_event;

import com.technext.event_bright.svc_event.dto.UserLogin;
import com.technext.event_bright.svc_event.entity.User;
import com.technext.event_bright.svc_event.repository.UserLoginRepository;
import com.technext.event_bright.svc_auth.dto.AuthData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.databind.ObjectMapper;


@ControllerAdvice
@RequiredArgsConstructor
public class UserAdvice {
    private final UserLoginRepository userLoginRepository;

    @ModelAttribute
    public User getUser(@ModelAttribute AuthData authData) {
        UserLogin userLogin = userLoginRepository.findUserLoginByLoginId(authData.loginId).orElse(null);
        if (userLogin == null) {
            return null;
        }
        return new ObjectMapper().convertValue(userLogin, User.class);
    }

}
