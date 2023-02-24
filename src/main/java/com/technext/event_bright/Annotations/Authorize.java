package com.technext.event_bright.Annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {
    String[] roles() default {};
    String[] groups() default {};
}
