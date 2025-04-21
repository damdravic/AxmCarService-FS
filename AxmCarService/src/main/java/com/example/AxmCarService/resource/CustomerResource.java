package com.example.AxmCarService.resource;

import com.example.AxmCarService.domain.Customer;
import com.example.AxmCarService.domain.HttpResponse;
import com.example.AxmCarService.service.CustomerService;
import com.example.AxmCarService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.util.Map.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerResource {

    private final CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<HttpResponse> createCustomer(@RequestBody Customer customer) {

        customerService.addCustomer(customer);


        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .statusCode(200)
                        .httpStatus(org.springframework.http.HttpStatus.OK)
                        .message("Customer created successfully")
                        .data(of("customer", customer))
                        .build()
        );
    }

    @GetMapping("/all")
    public ResponseEntity<HttpResponse> getAllCustomers(){
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .statusCode(200)
                        .httpStatus(org.springframework.http.HttpStatus.OK)
                        .message("All customers")
                        .data(of("customers", customerService.getAllCustomers()))
                        .build()
        );
    }

   @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<HttpResponse> deleteCustomer(@PathVariable Long customerId){
       System.out.println("customerId = " + customerId);
        customerService.deleteCustomer(customerId);

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .statusCode(200)
                        .httpStatus(org.springframework.http.HttpStatus.OK)
                        .message("Customer deleted successfully")
                        .build());
    }

}
