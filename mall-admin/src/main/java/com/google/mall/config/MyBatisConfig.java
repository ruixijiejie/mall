package com.google.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis配置类
 * Created by sohyun on 2019/12/29 16:47.
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.google.mall.mapper", "com.google.mall.dao"})
public class MyBatisConfig {
}
