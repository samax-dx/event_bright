package com.technext.event_bright;

import com.technext.event_bright.BrightAuth.JwtHelper;
import com.technext.event_bright.BrightAuth.PasswordHelper;
import com.technext.event_bright.Interceptors.AuthorizationInterceptor;
import com.technext.event_bright.Aspects.AuthorizationAspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
public class BrightAuthConfig implements WebMvcConfigurer {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void configurePathMatch(PathMatchConfigurer config) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        config.setPathMatcher(matcher);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(new JwtHelper(secret)));
    }

    @Bean
    public JwtHelper jwtHelper() {
        return new JwtHelper(secret);
    }

    @Bean
    public PasswordHelper passwordHelper() {
        return new PasswordHelper(secret);
    }

    @Bean
    public AuthorizationAspect authorizationAspect() {
        return new AuthorizationAspect();
    }
}
