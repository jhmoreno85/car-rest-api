package com.example.jhuerta.dao;

import com.example.jhuerta.dto.CarDto;
import com.example.jhuerta.mapper.CarMapper;

import org.apache.ibatis.session.SqlSessionManager;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.List;

public class CarDaoImpl implements CarDao {

    private static final int MIN_YEAR = 1900;

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
        validate(car);
        mapper.add(car);
        return car;
    }

    @Override
    public void update(CarDto car) {
        validate(car);
        mapper.update(car);
    }

    @Override
    public void delete(int id) {
        mapper.delete(id);
    }

    private void validate(CarDto car) {
        int currYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
        if (null == car) {
            throw new IllegalArgumentException("Car object must be not null");
        } else if (null == car.getBrand() || car.getBrand().isEmpty()) {
            throw new IllegalArgumentException("Car brand must be not null/empty");
        } else if (null == car.getModel() || car.getModel().isEmpty()) {
            throw new IllegalArgumentException("Car model must be not null/empty");
        } else if (null == car.getVin() || car.getVin().isEmpty()) {
            throw new IllegalArgumentException("Car VIN must be not null/empty");
        } else if (null == car.getColor() || car.getColor().isEmpty()) {
            throw new IllegalArgumentException("Car color must be not null/empty");
        } else if (null == car.getYear() || MIN_YEAR > car.getYear() || currYear < car.getYear()) {
            throw new IllegalArgumentException("Car year must be not null, or must be between " + MIN_YEAR + " and " + currYear);
        }
    }
}
