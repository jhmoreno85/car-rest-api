package com.example.jhuerta.service;

import com.example.jhuerta.api.exception.InternalServerException;
import com.example.jhuerta.api.exception.NotFoundException;
import com.example.jhuerta.api.model.Brand;
import com.example.jhuerta.api.model.Color;
import com.example.jhuerta.api.model.response.Car;
import com.example.jhuerta.dao.CarDao;
import com.example.jhuerta.dto.CarDto;
import com.example.jhuerta.service.ks.StreamService;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CarServiceImpl implements CarService {

    private static final int MIN_YEAR = 1900;

    private final CarDao carDao;
    private final StreamService streamService;

    @Inject
    public CarServiceImpl(CarDao carDao, StreamService streamService) {
        this.carDao = carDao;
        this.streamService = streamService;
    }

    @Override
    public Car get(int id) {
        CarDto carDto = carDao.findById(id);
        if (null == carDto) {
            throw new NotFoundException("car not found");
        }
        return Car.builder()
                .id(carDto.getId())
                .brand(Brand.valueOf(carDto.getBrand()))
                .model(carDto.getModel())
                .plates(carDto.getPlates())
                .vin(carDto.getVin())
                .year(carDto.getYear())
                .color(Color.valueOf(carDto.getColor()))
                .build();
    }

    @Override
    public List<Car> getAll() {
        return carDao.findAll().stream()
                .map(carDto -> Car.builder()
                        .id(carDto.getId())
                        .brand(Brand.valueOf(carDto.getBrand()))
                        .model(carDto.getModel())
                        .plates(carDto.getPlates())
                        .vin(carDto.getVin())
                        .year(carDto.getYear())
                        .color(Color.valueOf(carDto.getColor()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Car add(Car car) {
        validate(car);
        try {
            CarDto carDto = carDao.save(CarDto.builder()
                    .brand(car.getBrand().name())
                    .plates(car.getPlates())
                    .vin(car.getVin())
                    .year(car.getYear())
                    .model(car.getModel())
                    .color(car.getColor().name())
                    .build());
            car.setId(carDto.getId());
            streamService.publish(car.toString());
        } catch (Exception e) {
            log.error("An error has occurred", e);
            throw new InternalServerException(e.getMessage());
        }
        return car;
    }

    @Override
    public void update(int id, Car car) {
        validate(car);
        try {
            carDao.update(CarDto.builder()
                    .id(id)
                    .brand(car.getBrand().name())
                    .plates(car.getPlates())
                    .vin(car.getVin())
                    .year(car.getYear())
                    .model(car.getModel())
                    .color(car.getColor().name())
                    .build());
            streamService.publish(car.toString());
        } catch (Exception e) {
            log.error("An error has occurred", e);
            throw new InternalServerException(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        carDao.delete(id);
    }

    private void validate(Car car) {
        int currYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
        if (null == car) {
            throw new IllegalArgumentException("Car object must be not null");
        } else if (null == car.getBrand()) {
            throw new IllegalArgumentException("Car brand must be not null");
        } else if (null == car.getModel() || car.getModel().isEmpty()) {
            throw new IllegalArgumentException("Car model must be not null/empty");
        } else if (null == car.getVin() || car.getVin().isEmpty()) {
            throw new IllegalArgumentException("Car VIN must be not null/empty");
        } else if (null == car.getColor()) {
            throw new IllegalArgumentException("Car color must be not null");
        } else if (null == car.getYear() || MIN_YEAR > car.getYear() || currYear < car.getYear()) {
            throw new IllegalArgumentException("Car year must be not null, or must be between " + MIN_YEAR + " and " + currYear);
        }
    }
}
