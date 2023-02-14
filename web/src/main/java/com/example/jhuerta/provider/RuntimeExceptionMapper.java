package com.example.jhuerta.provider;

import com.example.jhuerta.api.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException ex) {
        log.error("Uncaught exception in application", ex);
        if (ex instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else if (ex instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
