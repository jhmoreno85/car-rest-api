package com.example.jhuerta;

import com.example.jhuerta.configuration.MySQLConfiguration;

import io.dropwizard.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.pac4j.dropwizard.Pac4jFactory;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CarConfiguration extends Configuration {

    @NotNull
    private MySQLConfiguration mySQLConfiguration;

    @NotNull
    Pac4jFactory pac4j = new Pac4jFactory();
}
