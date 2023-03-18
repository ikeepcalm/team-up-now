package dev.ua.ikeepcalm.teamupnow.aop.aspects;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ProgressionAspect {

    @Before("@annotation(progressable)")
    public void beforeMethodExecution(JoinPoint joinPoint, Progressable progressable) throws Throwable{
        ProgressENUM progressENUM = progressable.value();
        System.out.println("AOP says: Current step progression is - " + progressENUM);
    }
}
