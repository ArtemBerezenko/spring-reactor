package com.example.springreactor;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
@EnableR2dbcRepositories
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {

    @Value("${spring.data.postgres.host}")
    private String host;
    @Value("${spring.data.postgres.port}")
    private String port;
    @Value("${spring.data.postgres.database}")
    private String database;
    @Value("${spring.data.postgres.username}")
    private String username;
    @Value("${spring.data.postgres.password}")
    private String password;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(host)
                        .port(Integer.parseInt(port))
                        .database(database)
                        .username(username)
                        .password(password).build()
        );
    }

    @Bean
    TransactionalOperator transactionalOperator(ReactiveTransactionManager rtm) {
        return TransactionalOperator.create(rtm);
    }

    @Bean
    ReactiveTransactionManager r2dbcTransactionManager (ConnectionFactory cf) {
        return new R2dbcTransactionManager(cf);
    }
}
