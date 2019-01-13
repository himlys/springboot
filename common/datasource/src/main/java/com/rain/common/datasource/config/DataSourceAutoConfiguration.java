package com.rain.common.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.rain.common.base.RainObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ConditionalOnClass(DataSource.class)
@EnableConfigurationProperties(Slf4jConfigurationProperties.class)
@ConditionalOnProperty(name = "spring.datasource.druid")
public class DataSourceAutoConfiguration extends RainObject {
    @Configuration
    @ConditionalOnClass(DruidDataSource.class)
    class DruidDataSourceConfiguration {
        @Primary
        @Bean
        @ConfigurationProperties("spring.datasource.druid")
        public DruidDataSource dataSourceOne(Slf4jConfigurationProperties slf4jConfigurationProperties) {
            DruidDataSource ds = DruidDataSourceBuilder.create().build();
            try {
                System.setProperty("druid.log.conn", slf4jConfigurationProperties.getConnectionLogEnabled());
                System.setProperty("druid.log.stmt", slf4jConfigurationProperties.getStatementLogEnabled());
                System.setProperty("druid.log.r", slf4jConfigurationProperties.getResultSetLogEnabled());
                System.setProperty("druid.log.stmt.executableSql", slf4jConfigurationProperties.getStatementExecutableSqlLogEnable());
                ds.addFilters("localDruidLogFilter");

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ds;
        }
    }
}

@ConditionalOnProperty(name = "spring.datasource.druid.filter.slf4j")
@ConfigurationProperties("spring.datasource.druid.filter.slf4j")
class Slf4jConfigurationProperties {
    @Value("${spring.datasource.druid.filter.slf4j.connection-log-enabled}")
    private String connectionLogEnabled;
    @Value("${spring.datasource.druid.filter.slf4j.statement-log-enabled}")
    private String statementLogEnabled;
    @Value("${spring.datasource.druid.filter.slf4j.result-set-log-enabled}")
    private String resultSetLogEnabled;
    @Value("${spring.datasource.druid.filter.slf4j.statement-executable-sql-log-enable}")
    private String statementExecutableSqlLogEnable;

    public String getConnectionLogEnabled() {
        return connectionLogEnabled;
    }

    public void setConnectionLogEnabled(String connectionLogEnabled) {
        this.connectionLogEnabled = connectionLogEnabled;
    }

    public String getStatementLogEnabled() {
        return statementLogEnabled;
    }

    public void setStatementLogEnabled(String statementLogEnabled) {
        this.statementLogEnabled = statementLogEnabled;
    }

    public String getResultSetLogEnabled() {
        return resultSetLogEnabled;
    }

    public void setResultSetLogEnabled(String resultSetLogEnabled) {
        this.resultSetLogEnabled = resultSetLogEnabled;
    }

    public String getStatementExecutableSqlLogEnable() {
        return statementExecutableSqlLogEnable;
    }

    public void setStatementExecutableSqlLogEnable(String statementExecutableSqlLogEnable) {
        this.statementExecutableSqlLogEnable = statementExecutableSqlLogEnable;
    }
}
