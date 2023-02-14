package com.example.jhuerta.mapper;

import com.example.jhuerta.dto.CarDto;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper {
    CarDto findById(@Param("id") int id);
    List<CarDto> findAll();
    void add(CarDto car);
    void update(CarDto car);
    void delete(@Param("id") int id);
}
