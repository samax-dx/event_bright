package com.technext.event_bright.svc_auth.contracts;

import java.util.Map;


public interface SignupPersistenceService {
    void handleSignup(Map<String, Object> signupData, Map<String, Object> loginData) throws Exception;
}
