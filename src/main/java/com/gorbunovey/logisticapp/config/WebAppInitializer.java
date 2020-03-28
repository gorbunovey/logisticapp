package com.gorbunovey.logisticapp.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Order(1)
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    // класс -> инициализация веб-контекста, последовательное подтягивание других контекстов
    // замена дескриптора развертывания web.xml и импортов в класе диспетчере

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // основные конфиги, которые шли импортом в диспетчер-сервлете
        return new Class<?>[] {
                PersistenceConfig.class, CoreConfig.class, SecurityConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // класс описывающий диспетчер-сервлет
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        // маппинг запросов на диспетчер-сервлет
        return new String[]{"/"};
    }

}
