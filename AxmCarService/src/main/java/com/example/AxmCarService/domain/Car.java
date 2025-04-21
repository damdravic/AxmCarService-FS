package com.example.AxmCarService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Car {
    private Long id;
    private Long customerId;
    private String brand;
    private String model;
    private int year;
    private String engineType;
    private String engineCapacity;
    private String licencePlate;
    private String vin;
    private String color;
    private int mileage;
    private String fuelType;
    private String transmission;


}
