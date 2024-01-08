package com.facenet.mrp.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.facenet.mrp.repository.sap",
    entityManagerFactoryRef = "sapEntityManager",
    transactionManagerRef = "sapTransactionManager"
)
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class SapDatabaseConfiguration {
    @Bean(name = "sapDatasource")
    @ConfigurationProperties(prefix="spring.datasource.sap")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sapEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("sapDatasource") DataSource dataSource
    ) {
        return builder
            .dataSource(dataSource)
            .packages("com.facenet.mrp.domain.sap")
            .build();
    }

    @Bean
    public PlatformTransactionManager sapTransactionManager(
        final @Qualifier("sapEntityManager") LocalContainerEntityManagerFactoryBean memberEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(memberEntityManagerFactory.getObject()));
    }
}
