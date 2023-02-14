package com.example.jhuerta.service;

import com.example.jhuerta.api.model.response.Car;

import java.util.List;

public interface CarService {
    Car get(int id);
    List<Car> getAll();
    Car add(Car car);
    void update(int id, Car car);
    void delete(int id);
}
