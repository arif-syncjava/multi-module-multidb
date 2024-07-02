package com.arifsyncjava.database1.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement
@EnableJpaRepositories (
        basePackages = "com.arifsyncjava.database1.repository",
        entityManagerFactoryRef = "entityManager1",
        transactionManagerRef = "transactionManager1"
)
@Configuration
public class Db1Config {

    @Bean
    @ConfigurationProperties (prefix = "spring.datasource1")
    DataSourceProperties db1DataSourceProperties () {
        return new DataSourceProperties();
    }


    @Bean
    DataSource db1DataSource (
            @Qualifier("db1DataSourceProperties") DataSourceProperties db1DataSourceProperties ) {
        return db1DataSourceProperties
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManager1 (
            @Qualifier ("db1DataSource") DataSource db1DataSource, EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(db1DataSource)
                .packages("com.arifsyncjava.database1.model")
                .build();
    }

    @Bean
    PlatformTransactionManager transactionManager1 (
           @Qualifier ( "entityManager1") EntityManagerFactory entityManagerFactory1 ) {
        return new JpaTransactionManager(entityManagerFactory1);
    }


}
