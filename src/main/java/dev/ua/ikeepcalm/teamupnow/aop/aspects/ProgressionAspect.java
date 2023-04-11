package dev.ua.ikeepcalm.teamupnow.aop.aspects;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Aspect
public class ProgressionAspect {

    @Autowired
    private CredentialsService credentialsService;

    @Around("@annotation(progressable) && args(origin, ..)")
    public Object monitorProgress(ProceedingJoinPoint joinPoint, Progressable progressable, Message origin) throws Throwable {
        ProgressENUM expectedProgress = progressable.value();
        ProgressENUM currentProgress = credentialsService.findByAccountId(origin.getChatId()).getProgress().getProgressENUM();
        if (!expectedProgress.equals(currentProgress)) {
            return new Object();
        } else {
            return joinPoint.proceed();
        }
    }
}
