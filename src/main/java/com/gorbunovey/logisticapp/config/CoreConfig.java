package com.gorbunovey.logisticapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.gorbunovey.logisticapp")
public class CoreConfig {
    // class for -> scans a packet and creates beans by annotations @Service, @Component
    // need to be here, because WebAppInitializer upload SecurityConfig which use those beans
    // here also could be added aspects, by adding annotation @EnableAspectJAutoProxy
    // classes for aspects must be in scanning package with annotations @Component и @Aspect
}
