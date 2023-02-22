package com.technext.event_bright.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthData extends AuthDataCommon {
    public String password;

    public AuthData(String loginId, String password) {
        super(loginId);
        this.password = password;
    }
}
