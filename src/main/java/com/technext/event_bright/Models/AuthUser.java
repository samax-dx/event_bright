package com.technext.event_bright.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthUser extends AuthDataCommon {
    public String partyId;

    public AuthUser(String loginId, String partyId) {
        super(loginId);
        this.partyId = partyId;
    }
}
