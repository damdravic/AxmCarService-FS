package com.example.AxmCarService.query;

public class RepairOrderQuery {
    public static final String SELECT_ALL_REPAIR_ORDERS = "SELECT * FROM repair_order";
   public  static final String INSERT_ORDER_QUERY = "INSERT INTO repair_order (workshop_id,vehicle_id,technician_id,description,received_date,diagnosis,status,estimated_time,estimated_cost,total_cost)" +
           " VALUES (:workshopId,:vehicleId,:technicianId,:description,:receivedDate,:diagnosis,:status,:estimatedCompletionDate,:estimatedCost,:totalCost)";
}
