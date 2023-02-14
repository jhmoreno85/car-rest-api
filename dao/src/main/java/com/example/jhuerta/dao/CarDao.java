package com.example.jhuerta.dao;

import com.example.jhuerta.dto.CarDto;

import java.util.List;

public interface CarDao {
    CarDto findById(int id);
    List<CarDto> findAll();
    CarDto save(CarDto car);
    void update(CarDto car);
    void delete(int id);
}
