package dev.ua.ikeepcalm.teamupnow.aop.aspects;

import dev.ua.ikeepcalm.teamupnow.aop.annotations.Progressable;
import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.SLF4JServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Aspect
public class ProgressionAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(SLF4JServiceProvider.class);

    private final CredentialsService credentialsService;

    public ProgressionAspect(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @Around("@annotation(progressable) && args(origin, ..)")
    public Object monitorProgress(ProceedingJoinPoint joinPoint, Progressable progressable, Message origin) throws Throwable {
        LOGGER.info("["  + joinPoint.getTarget().getClass().getSimpleName() + "] - @" + origin.getFrom().getUserName());
        ProgressENUM expectedProgress = progressable.value();
        ProgressENUM currentProgress = credentialsService.findByAccountId(origin.getChatId()).getProgress().getProgressENUM();
        if (!expectedProgress.equals(currentProgress)) {
            return new Object();
        } else {
            return joinPoint.proceed();
        }
    }
}
