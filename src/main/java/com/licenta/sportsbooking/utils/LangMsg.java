package com.licenta.sportsbooking.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LangMsg implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static String get(String key) {
        return applicationContext.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LangMsg.applicationContext = applicationContext;
    }
}
