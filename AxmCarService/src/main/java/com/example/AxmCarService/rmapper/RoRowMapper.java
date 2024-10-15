package com.example.AxmCarService.rmapper;

import com.example.AxmCarService.domain.RepairOrder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoRowMapper implements RowMapper<RepairOrder> {


    @Override
    public RepairOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RepairOrder.builder()
                .orderId(rs.getLong("order_id"))
                .workshopId(rs.getLong("workshop_id"))
                .vehicleId(rs.getLong("vehicle_id"))
                .technicianId(rs.getLong("technician_id"))
                .description(rs.getString("description"))
                .dateReceived(rs.getDate("received_date"))
                .diagnosis(rs.getString("diagnosis"))
                .status(rs.getString("status"))
                .estimatedCompletionDate(rs.getDate("estimated_time"))
                .estimatedCost(rs.getDouble("estimated_cost"))
                .totalCost(rs.getLong("total_cost"))
                .build();
    }

}
