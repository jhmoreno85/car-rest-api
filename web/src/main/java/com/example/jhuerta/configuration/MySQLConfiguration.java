package com.example.jhuerta.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.db.DataSourceFactory;

import lombok.Data;

import javax.validation.Valid;

@Data
public class MySQLConfiguration {
    @Valid
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();
}
