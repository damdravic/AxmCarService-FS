package com.example.AxmCarService.repository.implementation;

import com.example.AxmCarService.domain.Car;
import com.example.AxmCarService.repository.CarRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CarRepositoryImpl implements CarRepository<Car> {

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
        System.out.println("Getting all cars from the database");

        //TODO: Implement this method - return a list of cars
        List cars = new ArrayList();
        cars.add(new Car(1L,1L,"Toyota","Corolla",2020,
                "ABC","19980","VN01PYC","01231231232","red", 150000, "patrol", "automatic"));
        cars.add(new Car(2L,1L,"Toyota","Corolla",2020,
                "ABC","19980","VN39PYC","01231231232","red", 150000, "patrol", "automatic"));
        cars.add(new Car(3L,1L,"Toyota","Corolla",2020,
                "ABC","19980","VN05AXM","01231231232","red", 150000, "patrol", "automatic"));


        return cars;

    }
}
