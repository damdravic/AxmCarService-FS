package com.example.AxmCarService.resource;

import com.example.AxmCarService.domain.HttpResponse;
import com.example.AxmCarService.domain.Workshop;
import com.example.AxmCarService.service.WorkshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workshop")
public class WorkshopResource {
    private final WorkshopService workshopService;

    @GetMapping("/all")
    public ResponseEntity<HttpResponse> getAllWarehouses(){
       return ResponseEntity.ok().body(
               HttpResponse.builder()
                               .statusCode(200)
                       .httpStatus(HttpStatus.OK).message("All workshops")
                       .data(Map.of("workshops",workshopService.getAllWorkshops())).build()
       );

    }



    @PostMapping("/newWorkshop")
    public ResponseEntity<HttpResponse> addNewWorkshop(@RequestBody Workshop workshop){

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .statusCode(200)
                        .httpStatus(HttpStatus.OK)
                        .message("Workshop created successfully")
                        .data(Map.of("workshop",workshopService.create(workshop)))
                        .build()
        );

    }

    @DeleteMapping("/deleteWorkshop/{id}")
    public ResponseEntity<HttpResponse> deleteWorkshop(@PathVariable("id") long id){
        System.out.println("in delete");
        workshopService.delete(id);
        return ResponseEntity.ok().body(
        HttpResponse.builder().httpStatus(HttpStatus.OK).statusCode(200).message("Workshop with id deleted successfully")
                .build());
    }

    @PostMapping("/editWorkshop")
    public ResponseEntity<HttpResponse> editWorkshop(@RequestBody Workshop workshop) {
        System.out.println("in edit");
        //workshopService.edit(workshop);

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .message("Workshop edited successfully")
                        .statusCode(200)
                        .httpStatus(HttpStatus.OK)
                        .data(Map.of("workshop",workshopService.edit(workshop)))
                        .build()

        );



    }


}
