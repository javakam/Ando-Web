package com.ando.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

//批量扫描所有的 Mapper 接口
@MapperScan(value = "com.ando.web.mapper")
@SpringBootApplication
public class AndoSpringbootWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndoSpringbootWebApplication.class, args);
    }

    @Autowired
    DataSource dataSource;

    public void testDataSource() throws SQLException {
        System.out.println(dataSource);

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Bean
    public ViewResolver myViewReolver() {
        return new MyViewResolver();
    }

    public static class MyViewResolver implements ViewResolver {

        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
            return null;
        }
    }
}
