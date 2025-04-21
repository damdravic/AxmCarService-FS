package com.example.AxmCarService.rmapper;

import com.example.AxmCarService.domain.Workshop;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkshopMapper implements RowMapper<Workshop> {
    @Override
    public  Workshop mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Workshop.builder()
                .id(rs.getLong("workshop_id"))
                .name(rs.getString("workshop_name"))
                .description(rs.getString("description")).build();
    }
}
