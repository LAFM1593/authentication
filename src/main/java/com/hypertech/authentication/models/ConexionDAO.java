/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypertech.authentication.models;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author hypertech
 */
@PropertySource(value = { "classpath:application.properties" })
public class ConexionDAO {
    
    @Autowired
    private static Environment env;
    
    public static enum ACTION {
        INSERT, UPDATE 
    }
    
    @Bean
    public static JdbcTemplate jdbcTemplate() {
        
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName(env.getProperty("jdbc.driverClassName"))
                .url(env.getProperty("jdbc.url"))
                .username(env.getProperty("jdbc.username"))
                .password(env.getProperty("jdbc.password"))
                .build();

        return new JdbcTemplate(dataSource);
    
    }
    
}
