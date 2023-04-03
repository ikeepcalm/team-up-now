package dev.ua.ikeepcalm.teamupnow.aop.aspects;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.exceptions.DAOException;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Aspect
public class EntryAspect {

    @Autowired
    private CredentialsService credentialsService;
    @Around("@annotation(dev.ua.ikeepcalm.teamupnow.aop.annotations.EntryPoint) && args(origin)")
    public void checkEntityCreation(ProceedingJoinPoint joinPoint, Message origin) throws Throwable {
        try {
            credentialsService.findByAccountId(origin.getFrom().getId());
        } catch (DAOException e){
            //There's no entity, allow user to use the Start command
            joinPoint.proceed();
        }
    }
}
