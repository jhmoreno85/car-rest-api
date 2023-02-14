package com.example.jhuerta.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private Integer id;
    private String brand;
    private String model;
    private String plates;
    private String vin;
    private String color;
    private Integer year;
}
