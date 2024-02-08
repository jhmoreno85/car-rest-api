package com.example.jhuerta;

import com.example.jhuerta.provider.RuntimeExceptionMapper;
import com.example.jhuerta.service.CarService;
import com.example.jhuerta.dao.CarDao;
import com.example.jhuerta.dao.CarDaoImpl;
import com.example.jhuerta.factory.MyBatisFactory;
import com.example.jhuerta.resources.CarResourceV1;
import com.example.jhuerta.service.CarServiceImpl;
import com.example.jhuerta.service.ks.KafkaStreamService;
import com.example.jhuerta.service.ks.StreamService;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.apache.ibatis.session.SqlSessionManager;

import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

public class CarApplication extends Application<CarConfiguration> {

    private static final String APP_NAME = "car";
    private static final String DB_SESSION = "db-session";
    private static final String MY_BATIS_ENVIRONMENT_NAME = "local";
    private static final String SQL_MAP_CONFIG_RESOURCE = "SqlMapConfig.xml";

    public static void main(final String[] args) throws Exception {
        new CarApplication().run(args);
    }

    @Override
    public String getName() {
        return APP_NAME;
    }

    @Override
    public void initialize(final Bootstrap<CarConfiguration> bootstrap) {
        bootstrap.getObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public void run(final CarConfiguration configuration, final Environment environment) {
        environment.jersey().register(RuntimeExceptionMapper.class);
        SqlSessionManager sqlSessionManager = new MyBatisFactory().build(
                environment,
                MY_BATIS_ENVIRONMENT_NAME,
                SQL_MAP_CONFIG_RESOURCE,
                configuration.getMySQLConfiguration().getDatabase(),
                DB_SESSION);
        environment.jersey().register(sqlSessionManager);
        environment.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(KafkaStreamService.class).to(StreamService.class).in(Singleton.class);
                bind(CarDaoImpl.class).to(CarDao.class).in(Singleton.class);
                bind(CarServiceImpl.class).to(CarService.class).in(Singleton.class);
            }
        });
        environment.jersey().register(CarResourceV1.class);
    }
}
