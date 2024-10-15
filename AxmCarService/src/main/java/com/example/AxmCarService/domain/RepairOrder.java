package com.example.AxmCarService.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RepairOrder {

    private Long orderId;
    private Long workshopId;
    private Long vehicleId;
    private Long technicianId;
    private String description;
    private Date dateReceived;
    private String diagnosis;
    private String status;
    private List<Part> parts;
    private Date estimatedCompletionDate;
    private Double estimatedCost;
    private Long totalCost;


    public RepairOrder(long orderId, String description, Date dateReceived, Date estimatedCompletionDate, Double estimatedCost, String status) {
        this.orderId = orderId;
        this.description = description;
        this.dateReceived = dateReceived;
        this.estimatedCompletionDate = estimatedCompletionDate;
        this.estimatedCost = estimatedCost;
        this.status = status;
    }



}
