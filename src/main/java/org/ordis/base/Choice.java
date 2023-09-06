package org.ordis.base;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Choice {

    @Nonnull String name();

    @Nonnull String value();
}
