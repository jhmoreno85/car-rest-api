package com.example.jhuerta.dao;

import com.example.jhuerta.dto.CarDto;
import com.example.jhuerta.mapper.CarMapper;

import org.apache.ibatis.session.SqlSessionManager;

import javax.inject.Inject;
import java.util.List;

public class CarDaoImpl implements CarDao {

    private final CarMapper mapper;

    @Inject
    public CarDaoImpl(SqlSessionManager sqlSessionManager) {
        this.mapper = sqlSessionManager.getMapper(CarMapper.class);
    }

    @Override
    public CarDto findById(int id) {
        return mapper.findById(id);
    }

    @Override
    public List<CarDto> findAll() {
        return mapper.findAll();
    }

    @Override
    public CarDto save(CarDto car) {
        mapper.add(car);
        return car;
    }

    @Override
    public void update(CarDto car) {
        mapper.update(car);
    }

    @Override
    public void delete(int id) {
        mapper.delete(id);
    }
}
