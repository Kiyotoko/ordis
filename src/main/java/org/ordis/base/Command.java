package org.ordis.base;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    @Nonnull String name();

    @Nonnull String description();

    @Nonnull Option[] options() default {};
}
