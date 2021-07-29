package com.macro.cloud.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-23 14:35
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DbConfig {

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    @Bean
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
