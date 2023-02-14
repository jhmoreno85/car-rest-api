package com.example.jhuerta.service;

import com.example.jhuerta.api.exception.BadRequestException;
import com.example.jhuerta.api.exception.InternalServerException;
import com.example.jhuerta.api.exception.NotFoundException;
import com.example.jhuerta.api.model.ColorType;
import com.example.jhuerta.api.model.response.Car;
import com.example.jhuerta.dao.CarDao;
import com.example.jhuerta.dto.CarDto;
import com.example.jhuerta.service.ks.StreamService;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CarServiceImpl implements CarService {

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
                .brand(carDto.getBrand())
                .model(carDto.getModel())
                .plates(carDto.getPlates())
                .vin(carDto.getVin())
                .year(carDto.getYear())
                .color(ColorType.valueOf(carDto.getColor()))
                .build();
    }

    @Override
    public List<Car> getAll() {
        return carDao.findAll().stream()
                .map(carDto -> Car.builder()
                        .id(carDto.getId())
                        .brand(carDto.getBrand())
                        .model(carDto.getModel())
                        .plates(carDto.getPlates())
                        .vin(carDto.getVin())
                        .year(carDto.getYear())
                        .color(ColorType.valueOf(carDto.getColor()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Car add(Car car) {
        CarDto carDto;
        try {
            carDto = carDao.save(CarDto.builder()
                    .brand(car.getBrand())
                    .plates(car.getPlates())
                    .vin(car.getVin())
                    .year(car.getYear())
                    .model(car.getModel())
                    .color(car.getColor().name())
                    .build());
            streamService.publish(carDto.toString());
        } catch (IllegalArgumentException e) {
            log.error("An error has occurred", e);
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            log.error("An error has occurred", e);
            throw new InternalServerException(e.getMessage());
        }
        car.setId(carDto.getId());
        return car;
    }

    @Override
    public void update(int id, Car car) {
        try {
            carDao.update(CarDto.builder()
                    .id(id)
                    .brand(car.getBrand())
                    .plates(car.getPlates())
                    .vin(car.getVin())
                    .year(car.getYear())
                    .model(car.getModel())
                    .color(car.getColor().name())
                    .build());
            streamService.publish(car.toString());
        } catch (IllegalArgumentException e) {
            log.error("An error has occurred", e);
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            log.error("An error has occurred", e);
            throw new InternalServerException(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        carDao.delete(id);
    }
}
