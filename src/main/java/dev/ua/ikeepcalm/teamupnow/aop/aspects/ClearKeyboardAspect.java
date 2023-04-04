package dev.ua.ikeepcalm.teamupnow.aop.aspects;

import dev.ua.ikeepcalm.teamupnow.telegram.servicing.TelegramService;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.MultiMessage;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.proxies.PurgeMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Component
@Aspect
public class ClearKeyboardAspect {

    @Autowired
    private TelegramService telegramService;

    @Around("@annotation(dev.ua.ikeepcalm.teamupnow.aop.annotations.ClearKeyboard) && args(origin)")
    public void clearReplyMarkupKeyboard(ProceedingJoinPoint joinPoint, Message origin) throws Throwable {
        try {
            MultiMessage multiMessage = new MultiMessage();
            multiMessage.setText("The system is carrying a heavy demand, please be patient!");
            multiMessage.setChatId(origin.getChatId());
            ReplyKeyboardRemove remove = new ReplyKeyboardRemove();
            remove.setRemoveKeyboard(true);
            multiMessage.setReplyKeyboard(remove);
            org.telegram.telegrambots.meta.api.objects.Message message =  telegramService.sendMultiMessage(multiMessage);
            PurgeMessage purgeMessage = new PurgeMessage(message.getMessageId(), message.getChatId());
            telegramService.sendPurgeMessage(purgeMessage);
        } finally {
            joinPoint.proceed();
        }
    }
}
