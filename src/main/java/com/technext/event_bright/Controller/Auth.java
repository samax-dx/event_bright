package com.technext.event_bright.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.technext.event_bright.BrightAuth.JwtHelper;
import com.technext.event_bright.Models.auth.AuthData;
import com.technext.event_bright.Models.auth.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/Auth")
@RequiredArgsConstructor
public class Auth {
    private final JwtHelper jwtHelper;

    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Object register(@RequestBody Map<String, String> payload) {
        AuthData rqData = new ObjectMapper().convertValue(payload, AuthData.class);
        AuthData dbData = new AuthData("admin", "admin");

        boolean isAuthentic = dbData.loginId.equals(rqData.loginId) && dbData.password.equals(rqData.password);

        if (isAuthentic) {
            AuthUser authUser = new AuthUser(dbData.loginId, "");
            return ImmutableMap.of("token", jwtHelper.getDataToken(new ObjectMapper().convertValue(authUser, new TypeReference<Map<String, String>>() {})));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid_credentials");
        }
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
            AuthUser authUser = new AuthUser(dbData.loginId, "");
            return ImmutableMap.of("token", jwtHelper.getDataToken(new ObjectMapper().convertValue(authUser, new TypeReference<Map<String, String>>() {})));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid_credentials");
        }
    }
}
