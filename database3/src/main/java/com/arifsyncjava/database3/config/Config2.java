package com.arifsyncjava.database3.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories (
        basePackages = {"com.arifsyncjava.database3.repository1"},
        entityManagerFactoryRef = "entityManagerFactory3",
        transactionManagerRef = "transactionManager3"
)
public class Config2 {

    @Bean
    @ConfigurationProperties (prefix = "spring.datasource3")
    DataSourceProperties db3DataSourceProperties () {
        return new DataSourceProperties();
    }


    @Bean
    DataSource db3DataSource (
            @Qualifier("db3DataSourceProperties")
            DataSourceProperties db3DataSourceProperties ) {
        return db3DataSourceProperties
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    DataSourceScriptDatabaseInitializer database3Sql (@Qualifier ("db3DataSource") DataSource db3DataSource) {
        var settings = new DatabaseInitializationSettings();
        settings.setMode(DatabaseInitializationMode.ALWAYS);
        settings.setContinueOnError(false);
        settings.setSchemaLocations(List.of(
                "classpath:/random-schema.sql",
                "classpath:/random-data.sql"
        ));
        return  new DataSourceScriptDatabaseInitializer(db3DataSource, settings);
    }

    @Bean
    DataSourceScriptDatabaseInitializer database2Sql (@Qualifier ("db2DataSource")
                                                      DataSource db2DataSource) {
        var settings = new DatabaseInitializationSettings();
        settings.setMode(DatabaseInitializationMode.ALWAYS);
        settings.setContinueOnError(false);
        settings.setSchemaLocations(List.of(
                "classpath:/random-schema.sql",
                "classpath:/random-data.sql"
        ));
        return  new DataSourceScriptDatabaseInitializer(db2DataSource, settings);
    }


    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory3 (
            @Qualifier ("db3DataSource") DataSource db3DataSource) {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean
                = new LocalContainerEntityManagerFactoryBean();

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");

        entityManagerFactoryBean.setDataSource(db3DataSource);
        entityManagerFactoryBean
                .setPackagesToScan("com.arifsyncjava.database3.model1");
        entityManagerFactoryBean.setJpaVendorAdapter(
                new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(jpaProperties);


        return  entityManagerFactoryBean;
    }

    @Bean
    PlatformTransactionManager transactionManager3 (
            @Qualifier ( "entityManagerFactory3") EntityManagerFactory entityManagerFactory3 ) {
        return new JpaTransactionManager(entityManagerFactory3);
    }


}