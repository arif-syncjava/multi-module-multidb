package com.arifsyncjava.database2.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@EnableTransactionManagement
@EnableJpaRepositories (
        basePackages = "com.arifsyncjava.database2.repository",
        entityManagerFactoryRef = "entityManagerFactory2",
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
    public DataSourceInitializer dataSourceInitializer(
            @Qualifier ("db2DataSource") DataSource db2DataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(db2DataSource);
        initializer.setDatabasePopulator(database2Populator());
        return initializer;
    }

    private DatabasePopulator database2Populator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("teacher-schema.sql"));
        populator.addScript(new ClassPathResource("teacher-data.sql"));
        return populator;
    }


    @Bean
    EntityManagerFactoryBuilder entityManagerFactoryBuilder2 () {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                new HashMap<>(),
                null
        );
    }



    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory2(
            @Qualifier ("entityManagerFactoryBuilder2")
             EntityManagerFactoryBuilder builder,
            @Qualifier ("db2DataSource") DataSource db2DataSource
    ) {


        final HashMap<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto","update");


        return builder
                .dataSource(db2DataSource)
                .packages("com.arifsyncjava.database2.model")
                .properties(jpaProperties)
                .build();
    }

    @Bean
    PlatformTransactionManager transactionManager2 (
           @Qualifier ("entityManagerFactory2") EntityManagerFactory entityManagerFactory2) {
        return new JpaTransactionManager(entityManagerFactory2);
    }





}
