package com.example.AxmCarService.resource;

import com.example.AxmCarService.domain.HttpResponse;
import com.example.AxmCarService.domain.Technician;
import com.example.AxmCarService.service.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TechnicianResource {
     private final TechnicianService technicianService;

    @PostMapping("/newTechnician")
    public ResponseEntity<HttpResponse> addTechnician(@RequestBody Technician technician) {
        System.out.println("in add technician" + technician.getTechName() + " " + technician.getSpecialization());
        Technician newTech =  technicianService.addTechnician(technician);
        return ResponseEntity.ok().body(
          HttpResponse.builder()
                    .statusCode(200)
                    .message("Technician added successfully")
                    .data(Map.of("technician",newTech))
                    .build());

    }


    @GetMapping("/all")
    public ResponseEntity<HttpResponse> getAllTechnicians() {
        System.out.println("in get all technicians");
        return ResponseEntity.ok().body(
          HttpResponse.builder()
                    .statusCode(200)
                    .message("Technicians retrieved successfully")
                    .data(Map.of("technicians",technicianService.getAllTechnicians()))
                    .build());

    }


}
