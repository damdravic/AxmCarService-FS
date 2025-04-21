package com.example.AxmCarService.service;

import com.example.AxmCarService.domain.Car;

import java.util.List;

public interface CarService<T extends Car> {


  T createCar(T car);

    T getCarById(Long id);

    List<T> getAllCars();




}
