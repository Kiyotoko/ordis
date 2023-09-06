package org.ordis.base;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Option {
    @Nonnull OptionType type();

    @Nonnull String name();

    @Nonnull String description();

    @Nonnull ChannelType[] channelTypes() default {};

    @Nonnull Choice[] choices() default {};

    boolean isRequired() default false;

    boolean isAutoComplete() default false;
}
