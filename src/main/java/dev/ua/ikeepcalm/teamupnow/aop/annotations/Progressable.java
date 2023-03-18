package dev.ua.ikeepcalm.teamupnow.aop.annotations;

import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Progressable {
    ProgressENUM value();
}
