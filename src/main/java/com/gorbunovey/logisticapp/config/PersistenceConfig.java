package com.gorbunovey.logisticapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:persistence.properties")
@EnableTransactionManagement
public class PersistenceConfig {
    // class for -> configuration connection to DB:
    // 1 -> configure data source
    // 2 -> factory for entityManager(data source, JPA implementation (Hibernate), repository placing,
    // dialect for hibernate, some other jpa-properties)
    // 1 -> set transaction manager

    @Bean
    public DataSource dataSource(@Value("${driverClassName}") String driver,
                                 @Value("${url}") String url,
                                 @Value("${username}") String user,
                                 @Value("${password}") String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/logisticdb?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false");
        dataSource.setUsername("root");
        dataSource.setPassword("48kk11HHWell");
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {

        //Create default configuration for Hibernate:
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        // Configure the entity manager factory bean:
        LocalContainerEntityManagerFactoryBean entityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        // Set base package of your entities:
        entityManagerFactory.setPackagesToScan("com.gorbunovey.logisticapp.entity");
        // Set JPA properties:
        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        props.put("hibernate.show_sql", "true");
        entityManagerFactory.setJpaProperties(props);
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
