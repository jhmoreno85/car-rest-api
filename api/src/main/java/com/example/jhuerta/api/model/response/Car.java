package com.example.jhuerta.api.model.response;

import com.example.jhuerta.api.model.ColorType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private Integer id;
    private String brand;
    private String model;
    private String plates;
    private String vin;
    private ColorType color;
    private Integer year;
}
