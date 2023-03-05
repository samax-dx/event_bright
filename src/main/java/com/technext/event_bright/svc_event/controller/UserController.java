package com.technext.event_bright.svc_event.controller;

import com.technext.event_bright.svc_auth.annotations.Authorize;
import com.technext.event_bright.svc_event.entity.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/User")
@RequiredArgsConstructor
public class UserController {

    @Authorize
    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/profile",
            method = RequestMethod.GET,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Object profile(@ModelAttribute User user) {
        return user;
    }

}
