package com.gorbunovey.logisticapp.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(2)
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    // класс -> вспомогательный, нужен для работы SecurityConfig
}
