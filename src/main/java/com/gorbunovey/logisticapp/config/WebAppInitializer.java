package com.gorbunovey.logisticapp.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Order(1)
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    // class for -> web-context initialization, uploading other configs
    // replacement for deployment descriptor (web.xml) and imports in dispatcher servlet

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // core config classes
        return new Class<?>[] {
                PersistenceConfig.class, CoreConfig.class, SecurityConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // dispatcher servlet description class
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        // dispatcher servlet requests mapping
        return new String[]{"/"};
    }

}
