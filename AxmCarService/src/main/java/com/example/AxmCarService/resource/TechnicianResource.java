package com.example.AxmCarService.resource;

import com.example.AxmCarService.domain.HttpResponse;
import com.example.AxmCarService.domain.Technician;
import com.example.AxmCarService.service.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TechnicianResource {
     private final TechnicianService technicianService;

    @RequestMapping("/newTechnician")
    public ResponseEntity<HttpResponse> addTechnician(@RequestBody Technician technician) {
        Technician newTech =  technicianService.addTechnician(technician);
        return ResponseEntity.ok().body(
          HttpResponse.builder()
                    .statusCode(200)
                    .message("Technician added successfully")
                    .data(Map.of("technician",newTech))
                    .build());

    }


}
