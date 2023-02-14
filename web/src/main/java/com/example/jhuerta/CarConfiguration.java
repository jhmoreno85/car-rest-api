package com.example.jhuerta;

import com.example.jhuerta.configuration.MySQLConfiguration;

import io.dropwizard.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CarConfiguration extends Configuration {

    @NotNull
    private MySQLConfiguration mySQLConfiguration;
}
