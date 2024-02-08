package com.example.jhuerta.api.model.response;

import com.example.jhuerta.api.model.Brand;
import com.example.jhuerta.api.model.Color;

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
    private Brand brand;
    private String model;
    private String plates;
    private String vin;
    private Color color;
    private Integer year;
}
