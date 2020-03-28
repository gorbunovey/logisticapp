package com.gorbunovey.logisticapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.gorbunovey.logisticapp")
public class CoreConfig {
    // класс -> сканирует пакет и создает бины по аннотациям @Service, @Component
    // нужен т.к. в WebAppInitializer мы подключаем SecurityConfig который использует эти бины
    // сюда также можно прикрутить аспекты, добавив аннотацию @EnableAspectJAutoProxy
    // классы-аспекты должны быть в сканируемом пакете с аннотациями @Component и @Aspect
}
