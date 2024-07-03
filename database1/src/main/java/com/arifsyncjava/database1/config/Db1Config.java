package com.arifsyncjava.database1.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories (
        basePackages = "com.arifsyncjava.database1.repository",
        entityManagerFactoryRef = "entityManagerFactory1",
        transactionManagerRef = "transactionManager1"

)
public class Db1Config {

    @Bean
    @ConfigurationProperties (prefix = "spring.datasource1")
    DataSourceProperties db1DataSourceProperties () {
        return new DataSourceProperties();
    }


    @Bean
    DataSource db1DataSource (
            @Qualifier("db1DataSourceProperties")
            DataSourceProperties db1DataSourceProperties ) {
        return db1DataSourceProperties
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    DataSourceScriptDatabaseInitializer database1Sql (
           @Qualifier ("db1DataSource") DataSource db1DataSource
    ) {
        var settings = new DatabaseInitializationSettings();
        settings.setMode(DatabaseInitializationMode.ALWAYS);
        settings.setContinueOnError(false);
        settings.setSchemaLocations(List.of(
                "classpath:/customer-schema.sql",
                "classpath:/customer-data.sql"
        ));
        return  new DataSourceScriptDatabaseInitializer(db1DataSource, settings);
    }

    @Bean
    EntityManagerFactoryBuilder entityManagerFactoryBuilder1 () {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                null,
                null
        );
    }



    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory1 (
            @Qualifier ("entityManagerFactoryBuilder1")
                   EntityManagerFactoryBuilder builder,
            @Qualifier ("db1DataSource") DataSource db1DataSource) {

        final HashMap<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto","update");


        return  builder
                .dataSource(db1DataSource)
                .packages("com.arifsyncjava.database1.model")
                .properties(jpaProperties)
                .build();
    }

    @Bean
    PlatformTransactionManager transactionManager1 (
           @Qualifier ( "entityManagerFactory1") EntityManagerFactory entityManagerFactory1 ) {
        return new JpaTransactionManager(entityManagerFactory1);
    }


}