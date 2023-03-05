package com.technext.event_bright.svc_event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technext.event_bright.svc_auth.contracts.SignupPersistenceService;
import com.technext.event_bright.svc_event.entity.Login;
import com.technext.event_bright.svc_event.entity.User;
import com.technext.event_bright.svc_event.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@RequiredArgsConstructor
public class SignupPersistenceServiceImpl implements SignupPersistenceService {
    private final UserRepository userRepository;

    @Override
    public void handleSignup(Map<String, Object> signupData, Map<String, Object> loginData) throws Exception {
        User user = new ObjectMapper().convertValue(signupData, User.class);
        Login login = new ObjectMapper().convertValue(loginData, Login.class);

        user.setLogin(login);
        login.setUser(user);

        userRepository.save(user);
    }

}
