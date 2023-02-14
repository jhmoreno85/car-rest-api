package com.example.jhuerta.resources;

import com.codahale.metrics.annotation.Timed;

import com.example.jhuerta.api.model.response.Car;
import com.example.jhuerta.service.CarService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1/cars")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Inject))
public class CarV1Resource {

    private final CarService carService;

    @GET
    @Path("/{id}")
    @Timed
    public Response get(@PathParam("id") Integer id) {
        Car result = carService.get(id);
        return Response.ok()
                .entity(result)
                .build();
    }

    @GET
    @Path("")
    @Timed
    public Response getAll() {
        List<Car> result = carService.getAll();
        return Response.ok()
                .entity(result)
                .build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public Response add(Car car) {
        Car result = carService.add(car);
        return Response.ok()
                .entity(result)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public Response update(@PathParam("id") Integer id, Car car) {
        carService.update(id, car);
        return Response.noContent()
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public Response delete(@PathParam("id") Integer id) {
        carService.delete(id);
        return Response.noContent()
                .build();
    }
}
