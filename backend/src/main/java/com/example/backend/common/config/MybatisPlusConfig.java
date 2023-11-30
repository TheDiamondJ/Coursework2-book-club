package com.example.backend.common.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.example.backend.mapper")
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor paginationInterceptor(){
        return new MybatisPlusInterceptor();
    }
}
