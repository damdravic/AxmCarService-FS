package com.example.AxmCarService.service.implementation;

import com.example.AxmCarService.domain.Car;
import com.example.AxmCarService.repository.CarRepository;
import com.example.AxmCarService.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService<Car> {

    private final CarRepository carRepository;
    @Override
    public Car createCar(Car car) {
        return null;
    }

    @Override
    public Car getCarById(Long id) {
        return null;
    }

    @Override
    public List<Car> getAllCars() {
        return  carRepository.getAllCars();
    }
}
