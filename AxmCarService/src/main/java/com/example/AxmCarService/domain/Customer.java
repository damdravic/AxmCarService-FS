package com.example.AxmCarService.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Customer {

    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String notes;
    private boolean active;
    private Date createdDate;
    private Date updateDate;
    private String createdBy;
    private String updatedBy;

}
