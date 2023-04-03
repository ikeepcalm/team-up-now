package dev.ua.ikeepcalm.teamupnow.aop.aspects;

import dev.ua.ikeepcalm.teamupnow.database.dao.service.impls.CredentialsService;
import dev.ua.ikeepcalm.teamupnow.database.entities.Credentials;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import dev.ua.ikeepcalm.teamupnow.telegram.servicing.implementations.LocaleTool;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

@Component
@Aspect
public class I18NAspect {

    @Autowired
    private CredentialsService credentialsService;

    @Around("@annotation(dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N) && args(origin)")
    public void replaceStringsWithLocalizedValue(ProceedingJoinPoint joinPoint, Message origin) throws Throwable {
        Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
        Object[] args = joinPoint.getArgs();
        Object targetObject = joinPoint.getTarget();
        Field myField = targetObject.getClass().getDeclaredField("locale");
        myField.setAccessible(true);
        if (credentials.getUiLanguage() == LanguageENUM.UKRAINIAN){
            myField.set(targetObject, new LocaleTool("i18n/messages_ua.properties"));
        } else if (credentials.getUiLanguage() == LanguageENUM.ENGLISH){
            myField.set(targetObject, new LocaleTool("i18n/messages_en.properties"));
        } joinPoint.proceed();
    }

    @Around("@annotation(dev.ua.ikeepcalm.teamupnow.aop.annotations.I18N) && args(receivedCallback, origin)")
    public void replaceStringsWithLocalizedValue(ProceedingJoinPoint joinPoint, String receivedCallback, Message origin) throws Throwable {
        Credentials credentials = credentialsService.findByAccountId(origin.getChatId());
        Object[] args = joinPoint.getArgs();
        Object targetObject = joinPoint.getTarget();
        Field myField = targetObject.getClass().getDeclaredField("locale");
        myField.setAccessible(true);
        if (credentials.getUiLanguage() == LanguageENUM.UKRAINIAN){
            myField.set(targetObject, new LocaleTool("i18n/messages_ua.properties"));
        } else if (credentials.getUiLanguage() == LanguageENUM.ENGLISH){
            myField.set(targetObject, new LocaleTool("i18n/messages_en.properties"));
        } joinPoint.proceed();
    }
}

