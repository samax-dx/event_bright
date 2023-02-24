package com.technext.event_bright.Advices;

import com.technext.event_bright.Entity.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.technext.event_bright.Models.AuthData;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class TokenDataAdvice {

    @ModelAttribute
    public AuthData getAuthData(HttpServletRequest request) {
        return new ObjectMapper().convertValue(request.getAttribute("tokenData"), AuthData.class);
    }

    @ModelAttribute
    public User getUser(HttpServletRequest request) {
        return new ObjectMapper().convertValue(request.getAttribute("tokenData"), User.class);
    }

}
