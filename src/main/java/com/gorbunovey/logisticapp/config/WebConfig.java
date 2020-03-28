package com.gorbunovey.logisticapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan("com.gorbunovey.logisticapp")
public class WebConfig implements WebMvcConfigurer {
    // класс -> концигурация диспетчер-сервлета

    @Bean
    public InternalResourceViewResolver viewResolver() {
        // настраиваем маппинг вьюшек:
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        //viewResolver.setContentType("text/html;charset=UTF-8");
        //viewResolver.setCache(false);
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        // маппим статические ресурсы и иконку:
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/resources/img/favicon.ico").setCachePeriod(31536000);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // маппим корневой запрос на index-страницку (не обязательно, у меня еть контроллер)
        // подобный маппинг - это что-то вроде контроллера на статический ресурс
        registry.addViewController("/").setViewName("index");
    }


}
