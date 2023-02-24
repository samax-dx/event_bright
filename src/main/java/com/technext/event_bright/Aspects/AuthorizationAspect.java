package com.technext.event_bright.Aspects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technext.event_bright.Annotations.Authorize;
import com.technext.event_bright.Utility.SpringWebUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;


@Aspect
public class AuthorizationAspect {
    @Around(value = "@annotation(authorizeAnnotation)")
    public Object authorize(ProceedingJoinPoint joinPoint, Authorize authorizeAnnotation) {
        try {
            Map<String, String> party = new ObjectMapper()
                    .convertValue(SpringWebUtil.currentRequestAttribute("tokenData"), new TypeReference<Map<String, String>>() {});

            boolean authChecks;

            authChecks = party.containsKey("userId") || party.containsKey("loginId");
            authChecks &= hasPartyRoles(authorizeAnnotation.roles(), party);
            authChecks &= belongsToPartyGroups(authorizeAnnotation.groups(), party);

            if (authChecks) {
                return joinPoint.proceed();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("authorization_failed");
            }
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private boolean hasPartyRoles(String[] partyRoles, Map<String, String> party) {
        return true;
    }

    private boolean belongsToPartyGroups(String[] partyGroups, Map<String, String> party) {
        return true;
    }
}
