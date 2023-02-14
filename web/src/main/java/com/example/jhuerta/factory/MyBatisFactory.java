package com.example.jhuerta.factory;

import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.setup.Environment;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.InputStream;
import java.util.Properties;

public class MyBatisFactory {

    public SqlSessionManager build(Environment environment, String myBatisEnvironmentName, String sqlMapConfigResource, PooledDataSourceFactory dbConfig, String name) {
        ManagedDataSource dataSource = dbConfig.build(environment.metrics(), name);
        return this.build(environment, myBatisEnvironmentName, sqlMapConfigResource, dataSource, name);
    }

    public SqlSessionManager build(Environment environment, String myBatisEnvironmentName, String sqlMapConfigResource, ManagedDataSource dataSource, String name) {
        environment.lifecycle().manage(dataSource);
        Configuration configuration = this.parse(myBatisEnvironmentName, sqlMapConfigResource);
        configuration.setEnvironment(new org.apache.ibatis.mapping.Environment(name, new JdbcTransactionFactory(), dataSource));
        configuration.setDatabaseId((new VendorDatabaseIdProvider()).getDatabaseId(dataSource));
        SqlSessionFactory sqlSessionFactory = (new SqlSessionFactoryBuilder()).build(configuration);
        sqlSessionFactory.getConfiguration().getMappedStatementNames();
        return SqlSessionManager.newInstance(sqlSessionFactory);
    }

    private Configuration parse(String myBatisEnvironmentName, String sqlMapConfigResource) {
        try {
            InputStream is = Resources.getResourceAsStream(sqlMapConfigResource);
            return (new XMLConfigBuilder(is, myBatisEnvironmentName, (Properties) null)).parse();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse myBatis configuration resource " + sqlMapConfigResource, e);
        }
    }
}
