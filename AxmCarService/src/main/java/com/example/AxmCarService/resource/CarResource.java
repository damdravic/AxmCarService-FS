package com.example.AxmCarService.resource;

import com.example.AxmCarService.domain.Car;
import com.example.AxmCarService.domain.HttpResponse;
import com.example.AxmCarService.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static java.util.Map.of;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarResource {

    private final CarService carService;

    @GetMapping("/all")
    public ResponseEntity<HttpResponse> getAllCars(){


        return  ResponseEntity.ok().body(
                HttpResponse.builder()
                        .statusCode(200)
                        .httpStatus(org.springframework.http.HttpStatus.OK)
                        .message("All cars")
                        .data(of("cars", carService.getAllCars()))
                        .build()
        );





    }







}
