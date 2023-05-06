package dev.ua.ikeepcalm.teamupnow.aop.aspects;

import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Aspect
public class SequencedAspect {

    @Autowired
    private TelegramService telegramService;

    @Around("@annotation(dev.ua.ikeepcalm.teamupnow.aop.annotations.Sequenced) && args(callback, origin)")
    public void checkEntityCreation(ProceedingJoinPoint joinPoint, String callback, Message origin) throws Throwable {
        try {
            PurgeMessage purgeMessage = new PurgeMessage(origin.getMessageId(), origin.getChatId());
            telegramService.sendPurgeMessage(purgeMessage);
        } finally {
            joinPoint.proceed();
        }
    }
}
