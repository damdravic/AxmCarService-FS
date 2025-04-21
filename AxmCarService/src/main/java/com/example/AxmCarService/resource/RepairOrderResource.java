package com.example.AxmCarService.resource;

import com.example.AxmCarService.domain.HttpResponse;
import com.example.AxmCarService.domain.RepairOrder;
import com.example.AxmCarService.service.RepairOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/repairOrder")
@RequiredArgsConstructor
public class RepairOrderResource {

     private final RepairOrderService repairOrderService;
     @RequestMapping("/all")
    public ResponseEntity<HttpResponse> getAll() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .message("All repair orders fetched successfully")
                        .data(Map.of("orders", repairOrderService.getAll()))
                        .build());
    }
    @RequestMapping("/create")
    public ResponseEntity<HttpResponse> create(@RequestBody RepairOrder order){
        System.out.println(order);

          return ResponseEntity.ok().body(
                  HttpResponse.builder()
                            .timeStamp(now().toString())
                            .httpStatus(OK)
                            .statusCode(OK.value())
                            .message("RepairOrder created  successfully")
                            .data(Map.of("order", repairOrderService.create(order)))
                            .build());
    }



}
