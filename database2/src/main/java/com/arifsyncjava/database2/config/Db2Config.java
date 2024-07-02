package com.arifsyncjava.database2.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@EnableTransactionManagement
@EnableJpaRepositories (
        basePackages = "com.arifsyncjava.database2.repository",
        entityManagerFactoryRef = "entityManager2",
        transactionManagerRef = "transactionManager2"
)
@Configuration
public class Db2Config {

    @Bean
    @ConfigurationProperties (prefix = "spring.datasource2")
    DataSourceProperties db2DataSourceProperties () {
        return new DataSourceProperties();
    }

    @Bean
    DataSource db2DataSource (
            @Qualifier ("db2DataSourceProperties")
            DataSourceProperties db2DataSourceProperties ) {
        return db2DataSourceProperties
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    EntityManagerFactoryBuilder managerFactoryBuilder2 () {

        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        final HashMap<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("jpa.hibernate.ddl-auto","update");


        return new EntityManagerFactoryBuilder(
                jpaVendorAdapter,
                jpaProperties,
                null
        );
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManager2 (
            @Qualifier ("managerFactoryBuilder2") EntityManagerFactoryBuilder builder,
            @Qualifier ("db2DataSource") DataSource db2DataSource
    ) {
        return builder
                .dataSource(db2DataSource)
                .packages("com.arifsyncjava.database2.model")
                .build();
    }

    @Bean
    PlatformTransactionManager transactionManager2 (
           @Qualifier ("entityManager2") EntityManagerFactory entityManagerFactory2) {
        return new JpaTransactionManager(entityManagerFactory2);
    }





}