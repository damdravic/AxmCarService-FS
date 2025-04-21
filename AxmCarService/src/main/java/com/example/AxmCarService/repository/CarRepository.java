package com.example.AxmCarService.repository;

import com.example.AxmCarService.domain.Car;

import java.util.List;

public interface CarRepository<T extends Car> {

    T createCar(T car);

    T getCarById(Long id);

    List<T> getAllCars();







}
