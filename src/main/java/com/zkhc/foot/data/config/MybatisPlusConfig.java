package com.zkhc.foot.data.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.zkhc.foot.data.config.properties.DruidProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author 武海升
 * @date 2018/12/6 13:44
 */
@Configuration
@MapperScan(basePackages = {"com.zkhc.foot.data.modules.dao"})
public class MybatisPlusConfig {

    @Resource
    private DruidProperties druidProperties;

    /**
     * 核心数据源
     */
    private DruidDataSource coreDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }


    /**
     * 单数据源连接池配置
     */
    @Bean
    public DruidDataSource singleDatasource() {
        return coreDataSource();
    }


    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }

}
