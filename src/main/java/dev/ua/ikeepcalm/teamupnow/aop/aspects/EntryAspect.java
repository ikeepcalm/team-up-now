package dev.ua.ikeepcalm.teamupnow.aop.aspects;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.exceptions.DAOException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mariadb.jdbc.util.log.Slf4JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Aspect
public class EntryAspect {
    private final Logger logger = LoggerFactory.getLogger(Slf4JLogger.class);

    @Autowired
    private CredentialsService credentialsService;

    @Around("@annotation(dev.ua.ikeepcalm.teamupnow.aop.annotations.EntryPoint) && args(origin)")
    public void checkEntityCreation(ProceedingJoinPoint joinPoint, Message origin) throws Throwable {
        logger.debug("["  + joinPoint.getTarget().getClass().getName() + "] - @" + origin.getFrom().getUserName());
        try {
            credentialsService.findByAccountId(origin.getFrom().getId());
        } catch (DAOException e){
            //There's no entity, allow user to use the Start command
            joinPoint.proceed();
        }
    }
}
